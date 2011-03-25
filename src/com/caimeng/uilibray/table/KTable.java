package com.caimeng.uilibray.table;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextField;

import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.utils.DisplaySetting;
import com.caimeng.uilibray.utils.StringUtil;

/**
 * 电子表格核心类。 用于在手机屏幕上显示一个二维电子表格
 * 
 * 目前主要支持的功能有 1. 颜色，字体风格的定义 2. 列宽的设定 3. 行标题，列表题的设定 4. 选择模式设定 5. 多行选择 6.
 * 允许用户设置单元格只读熟悉
 * 
 * 详细调用可以参考demo目录下的示例类
 * 
 * @author tooy
 * 
 */
public class KTable extends Canvas implements CommandListener{

	/**
	 * 单元选择模式
	 */
	public final static byte SELECT_BY_CELL = 0;

	/**
	 * 行选择模式
	 */
	public final static byte SELECT_BY_ROW = 1;

	/**
	 * 缺省系统边界宽度
	 */
	public final static short BORDER = 2;

	/**
	 * 系统信息条高度
	 */
	public final static short BAR_HEIGHT = 18;

	private static final int TOP = Graphics.TOP | Graphics.LEFT;
	/**
	 * 设置允许表格多行选择的情况
	 */
	private boolean allowMutltiRowSelected = false;

	/**
	 * 多行选择开始列
	 */
	private int startSelectRow = 0;

	/**
	 * 多行选择结束列
	 */
	private int endSelectRow = 0;

	/**
	 * 系统边界宽度
	 */
	protected static int borderWidth = 6;

	/**
	 * 表格数据模型
	 */
	private TableModel model;

	/**
	 * 列宽信息，根据预先设定或者列中的数据大小计算而来
	 */
	private int[] columnWidth;

	/**
	 * 样式区域
	 */

	/**
	 * 表格字体
	 */
	private Font fontStyle = DisplaySetting.TEXT_FONT;

	/**
	 * 背景色
	 */
	private int bgColor = DisplaySetting.WHITE_COLOR;

	/**
	 * 字体颜色
	 */
	private int fontColor = DisplaySetting.RED_COLOR;

	/**
	 * 表格线颜色
	 */
	private int lineColor = DisplaySetting.DATA_COLOR;

	/**
	 * 列标题字体
	 */
	private Font columnHeaderFont = DisplaySetting.TEXT_FONT;

	/**
	 * 列标题背景色
	 */
	private int columnHeaderBgColor = DisplaySetting.LIGHT_GRAY_COLOR;

	/**
	 * 列标题字体颜色
	 */
	private int columnHeaderFontColor = DisplaySetting.RED_COLOR;

	/**
	 * 行标题字体
	 */
	private Font rowHeaderFont = DisplaySetting.TEXT_FONT;

	/**
	 * 行标题背景色
	 */
	private int rowHeaderBgColor = DisplaySetting.LIGHT_GRAY_COLOR;

	/**
	 * 行标题字体颜色
	 */
	private int rowHeaderFontColor = DisplaySetting.RED_COLOR;

	/**
	 * 选择模式，缺省为单元选择
	 */
	private byte selectStyle = SELECT_BY_ROW;

	/**
	 * 被选中单元格字体颜色
	 */
	private int selectFontColor = DisplaySetting.SELECT_TEXT_COLOR;

	/**
	 * 被选择单元格背景颜色
	 */
	private int selectBgColor = DisplaySetting.SELECT_BG_COLOR;

	/**
	 * 脚注区背景色
	 */
	private int footerBgColor = DisplaySetting.LIGHT_YELLOW;

	/**
	 * 脚注区字体颜色
	 */
	private int footerFontColor = DisplaySetting.DATA_COLOR;

	/**
	 * 脚注区字体
	 */
	private Font footerFont = DisplaySetting.SMALL_FONT;

	/**
	 * 标题区字体颜色
	 */
	private int titleFontColor = DisplaySetting.WHITE_COLOR;

	/**
	 * 标题区背景颜色
	 */
	private int titleBgColor = DisplaySetting.HEADER_COLOR;

	/**
	 * 标题区字体
	 */
	private Font titleFont = DisplaySetting.SMALL_FONT;

	/**
	 * 显示控制信息
	 */

	/**
	 * 表格当前可显示区域开始x坐标
	 */
	private int startX;

	/**
	 * 表格当前可显示区域开始y坐标
	 */
	private int startY;

	/**
	 * 表格当前可显示区域结束y坐标
	 */
	private int endY;

	/**
	 * 表格当前可显示区域开始行
	 */
	private int startRow;

	/**
	 * 表格当前可显示区域开始列
	 */
	private int startCol;

	/**
	 * 表格当前可显示区域结束行
	 */
	private int endRow;

	/**
	 * 表格当前可显示区域结束列
	 */
	private int endCol;

	/**
	 * 表格当前被选择行
	 */
	private int currentRow;

	/**
	 * 表格当前被选择列
	 */
	private int currentCol;

	/**
	 * 脚注区高度
	 */
	private int footerBarHeight = 18;

	/**
	 * 用来判断tips是否要显示， 数据可以以tip的方式在当前表格正中绘制一个显示消息框 按任何键关闭。
	 */
	private boolean isShowTip = false;

	/**
	 * 用来显示的tip信息 ， tip只在表格启动的时候显示一次，如果需要再显示 应该手工设置isShowTip 为true
	 */
	private String tipMsg = "";

	/**
	 * 当前列坐标信息
	 */
	private int currentColumsnPosix[];

	/**
	 * 获取当前表格的数据模型
	 * 
	 * @return
	 */
    //private static Display display;
    
	private CMForm xWForm;
	
	private String name;

	Command insertCmd = new Command("当前位置插入新行", Command.OK, 1);

	Command appendCmd = new Command("新增一行", Command.OK, 1);

	Command deleteCurrentRow = new Command("删除当前行", Command.OK, 1);

	Command deleteAll = new Command("删除所有行", Command.OK, 1);
	
	Command back = new Command ("返回",Command.BACK,1);
	
	public KTable(CMForm xWForm,TableModel tm,String title)
	{
		//this.display=dis;
		this.xWForm=xWForm;
		this.name=title;
		addCommand(insertCmd);
		addCommand(appendCmd);
		addCommand(deleteCurrentRow);
		addCommand(deleteAll);
		addCommand(back);
        setCommandListener(this);       
    	setModel(tm);
		refreshModel();
	}

	
	

    
	public TableModel getModel() {
		return model;
	}

	/**
	 * 设定当前表格对应的数据模型， 模型设定或者修改以后，需要调用对应的refreshModel方法 才会更新数据。
	 * 
	 * @param model
	 */
	public void setModel(TableModel model) {
		this.model = model;
	}

	/**
	 * 获取当前标题区字体
	 * 
	 * @return
	 */
	public Font getTitleFont() {
		return titleFont;
	}

	/**
	 * 设置当前标题区字体
	 * 
	 * @param titleFont
	 */
	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	/**
	 * 刷新当前model，但是并不立即重画屏幕
	 */
	protected void refreshModel() {
		createGridWorkarea();
		caculateColumnWidth();
		// endRow = model.getRowCount() - 1;
		// endCol = model.getColCount() - 1;
		startRow = 0;
		startCol = 0;
		currentRow = 0;
		currentCol = 0;
		// 重新计算结束行和结束列
		getGridDisplayHeight();
		getGridDisplayWidth();
	}

	/**
	 * 更新当前model TODO rename late
	 */
	protected void updateModel() {
		refreshModel();
		isShowTip = false;
		tipMsg = null;
	}

	/**
	 * 刷新model，重画当前屏幕
	 */
	protected void refresh() {
		refreshModel();
		repaint();
	}

	/**
	 * 
	 */
	private void caculateColumnWidth() {
		columnWidth = new int[model.getColCount()];
		ColumnAttribute[] colAtts = model.getColumnAttributes();
		for (int i = 0; i < colAtts.length; i++) {
			columnWidth[i] = StringUtil.caculateStringWidth(colAtts[i]
					.getMaxDisplayCharNumber());
		}

	}

	/**
	 * 建立电子表格区域
	 */
	private void createGridWorkarea() {
		this.startX = BORDER;
		if (getModel().hasRowHeader()) {
			startX += caculateRowTitleColumnWidth();
		}
		this.startY = BAR_HEIGHT + getColumnHeaderBarHeight();
		setEndInfo();
	}

	/**
	 * 计算当前列标题高度
	 * 
	 * @return
	 */
	private int getColumnHeaderBarHeight() {
		return getDefaultRowHeight();//
	}

	private void setEndInfo() {
		this.endY = startY + getGridDisplayHeight();
	}

	/**
	 * 表格可视结束行
	 * 
	 * @return
	 */
	protected int getEndRow() {
		return endRow;
	}

	/**
	 * 表格可视结束行
	 * 
	 * @param endRow
	 */
	protected void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * 表格当前可显示区域开始行
	 */
	protected int getStartRow() {
		return startRow;
	}

	
	protected void paint(Graphics g) {
		g.setColor(getBgColor());
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(titleBgColor);
		g.fillRect(0, 0, getWidth(), BAR_HEIGHT);
		g.setColor(lineColor);
		g.drawRect(0, 0, getWidth(), BAR_HEIGHT);
		if (StringUtil.isNotBlank(name)) {
			g.setColor(titleFontColor);
			g.setFont(titleFont);			
			int start = (getWidth() - fontStyle.stringWidth(name)) / 2;
			g.drawString(name, start, 0, Graphics.LEFT | Graphics.TOP);
			
		}
		g.setColor(lineColor);

		int gridHeight = getGridDisplayHeight();
		int gridWidth = getGridDisplayWidth();

		int totalHeight = startY;

		g.setColor(lineColor);

		// DRAW ROW LINE
		g.drawLine(startX, startY, startX + gridWidth, startY);
		int row = startRow;
		// TODO
		/*Log.out("draw startRow is " + startRow + "  endRow is " + endRow
				+ "  currentRow " + currentRow);*/
		for (; row <= endRow; row++) {
			totalHeight += getRowHeight(row);
			g.drawLine(startX, totalHeight, startX + gridWidth, totalHeight);
		}

		// draw column line
		int totalWidth = startX;
		g.drawLine(startX, startY, startX, startY + gridHeight);
		int column = startCol;
		Vector temps = new Vector();
		for (; column <= endCol; column++) {
			totalWidth += columnWidth[column];
			// if (totalWidth > gridWidth) {
			// totalWidth = gridWidth;
			// g.drawLine(totalWidth, startY, totalWidth,
			// startY + gridHeight);
			// break;
			// }
			g.drawLine(totalWidth, startY, totalWidth, startY + gridHeight);
			temps.addElement(new Integer(totalWidth));
		}
		g.drawLine(startX + gridWidth, startY, startX + gridWidth, startY
				+ gridHeight);
		temps.addElement(new Integer(startX + gridWidth));

		currentColumsnPosix = convertInteger(temps);
		temps=null;
		for (int row1 = startRow; row1 <= endRow; row1++) {
			for (int col = startCol; col <= endCol; col++) {
				drawCellContent(row1, col, g);
			}
		}
		if (currentCol > endCol) {
			currentCol = endCol;
		}
		drawColumnHeaderBar(g);
		drawRowHeaderColumn(g);
		drawFooterBar(g);
	}

	/**
	 * 绘制行标题
	 * 
	 * @param g
	 */
	private void drawRowHeaderColumn(Graphics g) {
		if (!getModel().hasRowHeader()) {
			return;
		}

		int drawStartX = startX - caculateRowTitleColumnWidth();
		g.setColor(rowHeaderBgColor);
		g.fillRect(drawStartX, startY, caculateRowTitleColumnWidth(),
				getGridDisplayHeight());
		g.setColor(lineColor);
		g.drawLine(drawStartX, startY, drawStartX, startY
				+ getGridDisplayHeight());

		int totalHeight = startY;
		g.setColor(lineColor);
		g.drawLine(drawStartX, totalHeight, drawStartX
				+ caculateRowTitleColumnWidth(), totalHeight);
		for (int row = startRow; row <= endRow; row++) {
			g.setColor(lineColor);
			totalHeight += getRowHeight(row);
			g.drawLine(drawStartX, totalHeight, drawStartX
					+ caculateRowTitleColumnWidth(), totalHeight);

			drawText(g, drawStartX + 1, totalHeight - getRowHeight(row) + 2,
					caculateRowTitleColumnWidth() - 2, getRowHeight(row) - 4,
					getRowTitle(row), rowHeaderFont, rowHeaderFontColor);

		}
	}

	/**
	 * 获取指定行的标题
	 * 
	 * @param row
	 * @return
	 */
	private String getRowTitle(int row) {
		String value = model.getRow(row).getTitle();
		return StringUtil.isNotBlank(value) ? value : "";
	}

	/**
	 * 绘制表格的列标题
	 * 
	 * @param g
	 */
	private void drawColumnHeaderBar(Graphics g) {
		int drawStartY = startY - getColumnHeaderBarHeight() + 2;
		g.setColor(columnHeaderBgColor);

		g.fillRect(startX, drawStartY + 1, getGridDisplayWidth(),
				getColumnHeaderBarHeight() - 3);
		g.setColor(lineColor);
		g.drawLine(startX, drawStartY, startX + getGridDisplayWidth(),
				drawStartY);

		int cellHeight = getColumnHeaderBarHeight() - 2;
		// for (int i = startCol; i <= endCol; i++) {
		// g.setColor(lineColor);
		// g.drawLine(getCellStartX(i), drawStartY-1, getCellStartX(i),
		// startY);
		// drawText(g, getCellStartX(i)+ 3, drawStartY+1, columnWidth[i]-4,
		// cellHeight, getColumnTitle(i),columnHeaderFont,
		// columnHeaderFontColor);
		// }
		for (int i = startCol; i <= endCol; i++) {
			drawText(g, getCellStartX(i) + 3, drawStartY + 1,
					columnWidth[i] - 4, cellHeight, getColumnTitle(i),
					columnHeaderFont, columnHeaderFontColor);
		}
		for (int i = 0; i < currentColumsnPosix.length; i++) {
			g.setColor(lineColor);
			g.drawLine(currentColumsnPosix[i], drawStartY,
					currentColumsnPosix[i], startY);
		}
		// g.setColor(lineColor);
		// g.drawLine(endX, drawStartY, endX,
		// startY);
		g.setColor(lineColor);
		g.drawLine(startX, drawStartY, startX, startY);
	}

	/**
	 * 在指定范围绘制一段文本信息
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param value
	 * @param font
	 * @param color
	 */
	private void drawText(Graphics g, int x, int y, int width, int height,
			String value, Font font, int color) {
		int oldClipX = g.getClipX();
		int oldClipY = g.getClipY();
		int oldClipWidth = g.getClipWidth();
		int oldClipHeight = g.getClipHeight();
		g.setColor(color);
		g.setFont(font);
		g.setClip(x, y, width, height);
		g.drawString(value, g.getClipX(), g.getClipY(), TOP);
		g.setClip(oldClipX, oldClipY, oldClipWidth, oldClipHeight);
	}

	/**
	 * 获取特定列的列标题
	 * 
	 * @param i
	 * @return
	 */
	private String getColumnTitle(int i) {
		String value = model.getColumnAttributes()[i].getTitle();
		return StringUtil.isBlank(value) ? "" : value;
	}

	/**
	 * 得到当前可显示区域的结束y坐标
	 * 
	 * @return
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * 设置当前可显示区域的结束y坐标
	 * 
	 * @param endY
	 */
	public void setEndY(int endY) {
		this.endY = endY;
	}

	/**
	 * 根据行、列号填充cell
	 * 
	 * @param row
	 * @param col
	 * @param g
	 */
	protected void drawCellContent(int row, int col, Graphics g) {
		int oldClipX = g.getClipX();
		int oldClipY = g.getClipY();
		int oldClipWidth = g.getClipWidth();
		int oldClipHeight = g.getClipHeight();

		int[] cellCoodinateInfo = getCellCoordinateInfo(col, row);

		Cell cell = model.getCell(row, col);
		if (cell == null) {
			return;
		}
		String value = cell.getValue();

		/*if (isAllowMutltiRowSelected()
				&& col == startCol
				&& (row == getStartSelectRow() || (row == getEndSelectRow() && getEndSelectRow() >= 0))) {
			g.drawImage(ImageBuilder.INDICATOR_IMG, cellCoodinateInfo[0],
					cellCoodinateInfo[1], TOP);
		}*/

		if (!StringUtil.isBlank(value)) {
			// if (fontStyle.stringWidth(value) + BORDER_WIDTH >
			// columnWidth[col]) {
			// value = StringUtil.wrappedTextByScreenWidth(value, fontStyle,
			// columnWidth[col] - BORDER_WIDTH);
			// }

			g.clipRect(cellCoodinateInfo[0] + 2, cellCoodinateInfo[1] + 1,
					cellCoodinateInfo[2] - cellCoodinateInfo[0],
					cellCoodinateInfo[3] - cellCoodinateInfo[1]);

			if ((row == currentRow) && ((col == currentCol) || isSelectByRow())) {
				g.setColor(selectBgColor);
				g.fillRect(g.getClipX(), g.getClipY(), g.getClipWidth(), g
						.getClipHeight());
				g.setColor(selectFontColor);

			} else {
				g.setColor(cell.getBgColor(bgColor));
				g.fillRect(g.getClipX(), g.getClipY(), g.getClipWidth(), g
						.getClipHeight());
				g.setColor(cell.getFontColor(fontColor));
			}

			// g.drawString(value, cellCoodinateInfo[0] + 3,
			// cellCoodinateInfo[1],
			// Graphics.TOP | Graphics.LEFT);
			g.setFont(fontStyle);
			g.drawString(value, g.getClipX(), g.getClipY(), TOP);
		}

		g.setClip(oldClipX, oldClipY, oldClipWidth, oldClipHeight);

	}

	/**
	 * 用数据填充整个表格
	 * 
	 * @param g
	 */
	protected void fillGrid(Graphics g) {
		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col <= endCol; col++) {
				drawCellContent(row, col, g);
			}
		}
		if (currentCol > endCol) {
			currentCol = endCol;
		}
	}

	/**
	 * 类型转换
	 * 
	 * @param temps
	 * @return
	 */
	private int[] convertInteger(Vector temps) {
		int[] results = new int[temps.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = ((Integer) temps.elementAt(i)).intValue();
		}
		return results;
	}

	/**
	 * 显示当前选中Row
	 * 
	 * @param g
	 */
	// private void drawSelectRow(Graphics g) {
	// // g.setColor(10, 36, 108);
	// int currentCellStartY = getCurrentCellStartY() + 2;
	// g.setColor(selectBgColor);
	// int currentCellStartX = startX + 2;
	// int width = getGridDisplayWidth() - 4;
	// if (selectStyle == SELECT_BY_CELL) {
	// currentCellStartX = getCurrentCellStartX() + 2;
	// width = columnWidth[currentCol] - BORDER*2;
	// }
	//
	// if (currentCellStartX + width > startX + getGridDisplayWidth()) {
	// width = getGridDisplayWidth() + startX - 4;
	// }
	//		
	// debugPosition(currentCellStartX, currentCellStartY, width,
	// getRowHeight(currentRow) - 4);
	// g.fillRect(currentCellStartX, currentCellStartY, width,
	// getRowHeight(currentRow) - 4);
	// }
	/**
	 * Debug当前cell的位置信息
	 */
	/*protected void debugPosition(int startX, int startY, int width, int height) {
		Log.out("-------------------------");
		Log.out(" startX " + startX);
		Log.out(" startY  " + startY);
		Log.out(" width  " + width);
		Log.out(" height  " + height);
		Log.out("-------------------------");
	}
*/
	/**
	 * 获取当前cell的开始y坐标
	 * 
	 * @return
	 */
	protected int getCurrentCellStartY() {
		int height = 0;
		for (int i = startRow; i < currentRow; i++) {
			height += getRowHeight(i);
		}
		return startY + height;
	}

	protected int getCurrentCellStartX() {
		int width = 0;
		for (int i = startCol; i < currentCol; i++) {
			width += columnWidth[i];
		}
		return startX + width;
	}

	/**
	 * 绘制脚注信息，缺省在页脚显示当前选中记录
	 * 
	 * @param g
	 */
	protected void drawFooterBar(Graphics g) {
		if (isShowTip) {
			showTips(g);
		}
		g.setColor(footerBgColor);
		int footerY = getHeight() - footerBarHeight;
		g.fillRect(0, footerY, getWidth(), footerBarHeight);
		g.setColor(lineColor);
		g.drawRect(0, footerY, getWidth(), footerBarHeight);

		String value = getCurrentCellValue();
		if (!StringUtil.isBlank(value)) {
			g.clipRect(0, footerY, getWidth(), footerBarHeight);
			g.setColor(footerFontColor);
			g.setFont(footerFont);
			String position = (currentRow + 1) + "/" + (currentCol + 1);
			g.drawString(position, BORDER + 2, footerY, Graphics.LEFT
					| Graphics.TOP);
			g.drawString(value, fontStyle.stringWidth(value)>getWidth()/2?getWidth()/3: getWidth()- fontStyle.stringWidth(value)
					- BORDER, footerY, Graphics.LEFT | Graphics.TOP);

		}
	}

	/**
	 * 设置缺省显示的tips 信息，子类可以重载此方法
	 */
	protected void setDefaultTip() {
		tipMsg = "当前表格共" + model.getRowCount() + "行/" + model.getColCount()
				+ "列 可用 3，9前后翻页";
		isShowTip = true;
	}

	private void showTips(Graphics g) {
		if (StringUtil.isNotBlank(tipMsg)) {
			int tipWidth = getWidth() - getFontStyle().getHeight() * 2;
			String lines[] = StringUtil.separateTextArrayByWidth(tipMsg,
					getFontStyle(), tipWidth - 20, false);
			int height = lines.length * getFontStyle().getHeight() + 10;
			if (height > getHeight() - 20) {
				height = getHeight() - 20;
			}
			int x = (getWidth() - tipWidth) / 2;
			int y = (getHeight() - height) / 2;

			g.setColor(DisplaySetting.LIGHT_YELLOW);
			g.fillRect(x, y, tipWidth, height);
			g.setColor(lineColor);
			g.drawRect(x, y, tipWidth, height);
			int cY = y + 2;

			g.setColor(DisplaySetting.DATA_COLOR);

			for (int i = 0; i < lines.length; i++) {
				if (cY + getFontStyle().getHeight() > y + height) {
					break;
				}
				if (lines.length == 1) {
					x = (getWidth() - getFontStyle().stringWidth(lines[0])) / 2;
				}

				g.drawString(lines[i], x + 5, cY, Graphics.LEFT | Graphics.TOP);
				cY += getFontStyle().getHeight();
			}
		}

	}


	private int getRowHeight(int row) {
		Row rowObj = model.getRow(row);
		int currentRowHeight = rowObj.getRowHeight() > 0 ? rowObj
				.getRowHeight() : getDefaultRowHeight();
		return currentRowHeight;
	}

	protected int getCurrentRow() {
		return currentRow;
	}

	protected int getCurrentCol() {
		return currentCol;
	}

	/**
	 * 获取当前cell的值，如果模型为空则返回null
	 * 
	 * @return
	 */
	protected String getCurrentCellValue() {
		return model.getRowCount() == 0 ? null : model.getCell(currentRow,
				currentCol).getValue();
	}

	/**
	 * 得到当前表格的字体设置
	 * 
	 * @return
	 */
	public Font getFontStyle() {
		return fontStyle == null ? DisplaySetting.TEXT_FONT : fontStyle;
	}

	/**
	 * 设置当前表格字体信息
	 * 
	 * @param fontStyle
	 */
	public void setFontStyle(Font fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public byte getSelectStyle() {
		return selectStyle;
	}

	public void setSelectStyle(byte selectStyle) {
		this.selectStyle = selectStyle;
	}

	public int getSelectFontColor() {
		return selectFontColor;
	}

	public void setSelectFontColor(int selectFontColor) {
		this.selectFontColor = selectFontColor;
	}

	public int getSelectBgColor() {
		return selectBgColor;
	}

	public void setSelectBgColor(int selectBgColor) {
		this.selectBgColor = selectBgColor;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getFooterBgColor() {
		return footerBgColor;
	}

	public void setFooterBgColor(int footerBgColor) {
		this.footerBgColor = footerBgColor;
	}

	public int getFooterFontColor() {
		return footerFontColor;
	}

	public void setFooterFontColor(int footerFontColor) {
		this.footerFontColor = footerFontColor;
	}

	public int getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(int titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public int getTitleBgColor() {
		return titleBgColor;
	}

	public void setTitleBgColor(int titleBgColor) {
		this.titleBgColor = titleBgColor;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX() {
		this.startX = BORDER;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY() {
		this.startY = BAR_HEIGHT;
	}

	public void setStartRow(int startRow) {
		if (startRow < getModel().getRowCount() - 1) {
			this.startRow = startRow;
		}
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int col) {
		if ((col < getModel().getColCount()) && (col >= 0)) {
			this.startCol = col;
		}
	}

	public void setCurrentRow(int newCurrentRow) {
		if ((newCurrentRow < getModel().getRowCount()) && (newCurrentRow >= 0)) {
			this.currentRow = newCurrentRow;
		}
	}

	public void setCurrentCol(int col) {
		if ((col < getModel().getColCount()) && (col >= 0)) {
			this.currentCol = col;
		}
	}

	public int getMaxDisplayWidth() {
		return getWidth() - startX;
	}

	public int getMaxDisplayHeight() {
		return getHeight() - BAR_HEIGHT - getColumnHeaderBarHeight()
				- footerBarHeight;
	}

	public String getTipMsg() {
		return tipMsg;
	}

	/**
	 * 设置当前表格启动时显示的msg
	 * 
	 * @param tipMsg
	 */
	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
		if (StringUtil.isNotBlank(tipMsg)) {
			isShowTip = true;
		}
	}

	/**
	 * 取得显示表格的宽度
	 * 
	 * @return
	 */
	public int getGridDisplayWidth() {
		int totalWidth = 0;
		int column = startCol;
		for (; column < columnWidth.length; column++) {
			totalWidth += columnWidth[column];
			if (totalWidth > getMaxDisplayWidth()) {
				totalWidth = getMaxDisplayWidth();
				break;
			}

		}
		endCol = column >= model.getColCount() ? model.getColCount() - 1
				: column;
		return totalWidth;
	}

	/**
	 * 取得显示表格的高度
	 * 
	 * @return
	 */
	public int getGridDisplayHeight() {
		int totalHeight = 0;
		int row = startRow;
		endRow = row;
		for (; row < model.getRowCount(); row++) {
			totalHeight += getRowHeight(row);
			if (totalHeight >= getMaxDisplayHeight()) {
				totalHeight = totalHeight - getRowHeight(row);
				endRow--;
				break;
			}
			endRow++;
		}
		if (endRow >= model.getRowCount()) {
			endRow = model.getRowCount() - 1;
		}
		//Log.out("caculate endRow " + endRow);
		return totalHeight;
	}

	/**
	 * 上下左右滚动
	 */
	public void keyPressed(int keyCode) {
		processKeyEvent(keyCode);
	}

	/**
	 * 上下左右滚动
	 */
	public void keyRepeated(int keyCode) {
		//processKeyEvent(keyCode);
		int gameCode = getGameAction(keyCode);
		if (gameCode == UP) {
			if (currentRow > startRow) {
				currentRow--;
				repaint();

			} else if ((currentRow == startRow) && (startRow > 0)) {
				startRow--;
				currentRow--;
				repaint();
			}
		} else if (gameCode == DOWN) {
			boolean isNotLastRow = endRow != model.getRowCount() - 1;
			if ((currentRow == endRow) && isNotLastRow) {
				startRow++;
				currentRow++;
				repaint();
			} else if (currentRow < endRow) {
				currentRow++;
				repaint();
			}		
			// Log.out("cr " + currentRow + " endRow " + endRow
			// + " startRow " + startRow);
		}
	}

	private void processKeyEvent(int keyCode) {
		if (isShowTip) {
			isShowTip = false;
			repaint();
			return;
		}

		int gameCode = getGameAction(keyCode);

		// 不同机器的6,9键值可能有差异，要实际测试
		// 6 是方向键， 不适合使用， 修改为3
		if ((keyCode == KEY_NUM3)) {
			int visibleRowCount = getVisibleRowCountForCurrentPage(false);
			if (startRow - visibleRowCount >= 0) {
				startRow -= visibleRowCount;
				// currentRow -= visibleRowCount;
				currentRow = startRow;
				repaint();
			} else if (startRow > 0) {
				startRow = 0;
				currentRow = 0;
				repaint();
			}

		} else if (keyCode == KEY_NUM9) {
			if (endRow + 1 < getModel().getRowCount()) {
				startRow = endRow + 1;
				currentRow = startRow;
				repaint();
			}
		} else if (gameCode == UP) {
			if (currentRow > startRow) {
				currentRow--;
				repaint();

			} else if ((currentRow == startRow) && (startRow > 0)) {
				startRow--;
				currentRow--;
				repaint();
			}
		} else if (gameCode == DOWN) {
			boolean isNotLastRow = endRow != model.getRowCount() - 1;
			if ((currentRow == endRow) && isNotLastRow) {
				startRow++;
				currentRow++;
				repaint();
			} else if (currentRow < endRow) {
				currentRow++;
				repaint();
			}
			// Log.out("cr " + currentRow + " endRow " + endRow
			// + " startRow " + startRow);
		} else if (gameCode == LEFT) {
			//Log.out("---------- cl  " + currentCol + " startCol = " + startCol);
			if (currentCol > startCol) {
				currentCol--;
				repaint();
			} else if ((currentCol == startCol) && (startCol > 0)) {
				startCol--;
				currentCol--;
				repaint();
			}
		} else if (gameCode == RIGHT) {
			boolean isNotLastCol = endCol != (model.getColCount() - 1);
			if ((currentCol == endCol) && isNotLastCol) {
				// 此处需要增加判断，如果当前列显示不完整，则将当前列设置
				// 为显示第一列，而不是滚动到下一列

				int currentColWidth = columnWidth[currentCol];
				int currentColDisplayWidth = getCellEndX(currentCol)
						- getCellStartX(currentCol);
				if (currentColDisplayWidth < currentColWidth - 1) {
					startCol = currentCol;
				}

				startCol++;
				currentCol++;
				boolean checkWidth = checkWidth(startCol, currentCol);
				if (checkWidth) {
					startCol++;
				}
				repaint();
			} else if (currentCol < endCol) {
				currentCol++;

				// 此处需要增加判断，如果当前列显示不完整，则将当前列设置
				// 为显示第一列，而不是滚动到下一列
				// Log.out(" currentCol " + currentCol);

				int currentColWidth = columnWidth[currentCol];
				//Log.out(" columnWidth is " + currentColWidth);
				int currentColDisplayWidth = getCellEndX(currentCol)
						- getCellStartX(currentCol);
				//Log.out(" display columnWidth is " + currentColDisplayWidth);

				if (currentColDisplayWidth < currentColWidth - 3) {
					startCol = currentCol;
				}

				repaint();
			}
		} else if (gameCode == FIRE) {
			keyFireEvent();
		}
	}

	/**
	 * 取得屏幕可显示的行数
	 * 
	 * @return
	 */
	private int getVisibleRowCountForCurrentPage(boolean down) {
		if (startRow == 0 && !down
				|| (down && endRow == getModel().getRowCount() - 1)) {
			return 0;
		}
		int totalHeight = startY;
		int end = getHeight() - footerBarHeight;
		int count = 0;
		if (down) {
			for (int i = endRow + 1; i < getModel().getRowCount(); i++) {
				if (totalHeight + getRowHeight(i) >= end) {
					break;
				}
				totalHeight += getRowHeight(i);
				count++;
			}
		} else {
			for (int i = startRow - 1; i >= 0; i--) {
				if (totalHeight + getRowHeight(i) >= end) {
					break;
				}
				totalHeight += getRowHeight(i);
				count++;
			}
		}

		return count;
	}


	/**
	 * 检查表格左右滚动过程中是否需要调整起始列
	 * 
	 * @param startCol
	 * @param currentCol
	 * @return
	 */
	private boolean checkWidth(int startCol, int currentCol) {
		boolean flag = false;
		int widthCount = 0;
		for (int i = startCol; i < currentCol; i++) {
			widthCount += columnWidth[i];
		}
		if (widthCount >= getGridDisplayWidth()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 捕获当前单元格被触发事件，子类可以覆盖，实现自己的控制处理
	 */
	protected void keyFireEvent() {
		if (model.getRowCount() == 0) {
			return;
		}
		if (model.isReadonly(currentRow, currentCol)) {
			showTxtInfo(getCurrentCellValue());
		} else {
			//TextInput textInput = new TextInput(getCurrentCellValue(), this);
			//frame.display.setCurrent(textInput);
			showTxtInfo(getCurrentCellValue());
		}
	}

	/**
	 * 打开一个文本form，用来显示一些信息
	 */
	protected void showTxtInfo(String msg) {
		TextDisplay textDisplay = new TextDisplay(msg, this);
		//DisplayUtil.setCurrentDisplayable(textDisplay);
		CMForm.display.setCurrent(textDisplay);
	}

	/**
	 * 触摸屏指针事件
	 */
	protected void pointerPressed(int x, int y) {
		super.pointerPressed(x, y);

		/**
		 * 处理排序事件
		 */
		int columnHeaderBarStartY = startY - getColumnHeaderBarHeight();
		if ((y > columnHeaderBarStartY) && (y < startY) && (x > startX)) {

			for (int i = startCol; i <= endCol; i++) {
				if ((x >= getCellStartX(i)) && (x <= getCellEndX(i))) {
					model.sortColumn(i);
					repaint();
					return;
				}
			}
		}

		int currentCellStartY = startY;
		int currentCellEndY = startY;

		boolean pointerActiveX = false;
		boolean pointerActiveY = false;

		// 根据坐标取得行号
		for (int row = 0; row < model.getRowCount() - 1; row++) {

			currentCellStartY = getCellCoordinateYByRow(row);
			currentCellEndY = getCellCoordinateYByRow(row + 1);
			if ((y > currentCellStartY) && (y < currentCellEndY)) {
				currentRow = row;
				pointerActiveX = true;
				break;
			}
		}

		int currentCellStartX = startX;
		int currentCellEndX = startX;
		// 根据坐标取得列号
		for (int col = 0; col < model.getColCount() - 1; col++) {
			currentCellStartX = getCellCoordinateXByCol(col);
			currentCellEndX = getCellCoordinateXByCol(col + 1);
			if ((x > currentCellStartX) && (x < currentCellEndX)) {
				currentCol = col;
				pointerActiveY = true;
				break;
			}
		}

		if (pointerActiveX && pointerActiveY) {
			keyFireEvent();
		}
	}

	/**
	 * 计算行标题显示的宽度
	 * 
	 * @return
	 */
	public int caculateRowTitleColumnWidth() {
		String maxTitle = "";
		for (int i = 0; i < model.getRowCount(); i++) {
			Row rowObj = model.getRow(i);
			if (StringUtil.isNotBlank(rowObj.getTitle())
					&& (rowObj.getTitle().length() > maxTitle.length())) {
				maxTitle = rowObj.getTitle();
			}
		}

		return rowHeaderFont.stringWidth(maxTitle + "Z");
	}

	/**
	 * 根据cell的列号取得该cell左上角的横坐标
	 * 
	 * @param row
	 * @return
	 */
	public int getCellCoordinateXByCol(int col) {
		// Log.out("The screen width is " + getWidth());
		int totalWidth = startX;
		if (col - startCol > 0) {
			for (int i = startCol; i < col; i++) {
				totalWidth += columnWidth[i];
				if (totalWidth > getMaxDisplayWidth()) {
					totalWidth = getMaxDisplayWidth();
					break;
				}
			}

		}

		return totalWidth;
	}

	/**
	 * 根据cell的行号取得该cell左上角的纵坐标
	 * 
	 * @param row
	 * @return
	 */
	public int getCellCoordinateYByRow(int row) {
		int totalHeight = startY;
		if (row - startRow > 0) {
			for (int i = startRow; i < row; i++) {
				Row rowObj = model.getRow(i);
				totalHeight += rowObj.getRowHeight() > 0 ? rowObj
						.getRowHeight() : getDefaultRowHeight();
				if (totalHeight > getMaxDisplayHeight()) {
					totalHeight = getMaxDisplayHeight();
					//Log.out("total Height" + getMaxDisplayHeight());
					break;
				}
			}
		}
		return totalHeight;
	}

	/**
	 * 根据行列值查出该cell的坐标信息，数组内容依次为左上角X坐标、左上角Y坐标、右下角X坐标、右下角Y坐标
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public int[] getCellCoordinateInfo(int col, int row) {
		int[] cellCoodinateInfo = new int[4];
		cellCoodinateInfo[0] = getCellStartX(col);
		cellCoodinateInfo[1] = getCellStartY(row) + 1;
		cellCoodinateInfo[2] = getCellEndX(col) - 2;
		cellCoodinateInfo[3] = getCellStartY(row + 1) - 2;
		return cellCoodinateInfo;
	}

	/**
	 * 获取指定cell的开始y坐标
	 * 
	 * @param row
	 * @return
	 */
	public int getCellStartY(int row) {
		int totalHeight = startY;
		for (int i = startRow; i < row; i++) {
			totalHeight += getRowHeight(i);
		}
		return totalHeight;
	}

	/**
	 * 获取指定cell的开始x坐标
	 * 
	 * @param col
	 * @return
	 */
	public int getCellStartX(int col) {
		int totalWidth = startX;
		for (int i = startCol; i < col; i++) {
			totalWidth += columnWidth[i];
		}
		return (totalWidth + 1);
	}

	/**
	 * 获取指定cell的结束x坐标
	 * 
	 * @param col
	 * @return
	 */
	public int getCellEndX(int col) {
		int totalWidth = startX;
		for (int i = startCol; i < col; i++) {
			totalWidth += columnWidth[i];
		}
		totalWidth += columnWidth[col];
		if (totalWidth > startX + getGridDisplayWidth()) {
			totalWidth = startX + getGridDisplayWidth();
		}
		return (totalWidth - 2);
	}

	/**
	 * 设置当前选择模式是否为行选择
	 * 
	 * @param b
	 */
	protected void setSelectStyleByRow(boolean b) {
		selectStyle = b ? SELECT_BY_ROW : SELECT_BY_CELL;
	}

	/**
	 * 测试当前单元格选择模式
	 * 
	 * @return
	 */
	protected boolean isSelectByRow() {
		return selectStyle == SELECT_BY_ROW;
	}

	/**
	 * 取得页面所能显示的列数
	 * 
	 * @return
	 */
	public int getVisibleColCount() {
		int totalWidth = 0;
		int column = startCol;
		for (; column < columnWidth.length; column++) {
			totalWidth += columnWidth[column];
			if (totalWidth > getMaxDisplayWidth()) {
				totalWidth = getMaxDisplayWidth();
				break;
			}

		}
		return column - startCol + 1;
	}

	/**
	 * 设置当前cell的值
	 * 
	 * @param value
	 */
	public void setCurrentCellValue(String value) {
		model.setValue(currentRow, currentCol, value);
		// refreshModel();
	}

	/**
	 * 根据字体设置默认行高
	 */
	private int getDefaultRowHeight() {
		return fontStyle.getHeight() + 4;
	}

	/**
	 * 判断是否允许多行选择
	 * 
	 * @return
	 */
	public boolean isAllowMutltiRowSelected() {
		return allowMutltiRowSelected;
	}

	/**
	 * 设置是否允许多行选择
	 * 
	 * @param allowMutltiRowSelected
	 */
	public void setAllowMutltiRowSelected(boolean allowMutltiRowSelected) {
		this.allowMutltiRowSelected = allowMutltiRowSelected;
	}

	/**
	 * 获取多行选择开始行
	 * 
	 * @return
	 */
	public int getStartSelectRow() {
		return startSelectRow;
	}

	/**
	 * 设置多行选择开始行
	 * 
	 * @param startSelectRow
	 */
	public void setStartSelectRow(int startSelectRow) {
		this.startSelectRow = startSelectRow;
	}

	/**
	 * 设置当前行为多行选择开始行
	 */
	public void setStartSelectRow() {
		setStartSelectRow(getCurrentRow());
	}

	/**
	 * 获取当前选择结束行
	 * 
	 * @return
	 */
	public int getEndSelectRow() {
		return endSelectRow;
	}

	/**
	 * 设置多行选择结束行
	 * 
	 * @param endSelectRow
	 */
	public void setEndSelectRow(int endSelectRow) {
		this.endSelectRow = endSelectRow;
	}

	/**
	 * 设置当前行为选择结束行
	 */
	public void setEndSelectRow() {
		setEndSelectRow(getCurrentRow());
	}

	/**
	 * 获取列标题字体
	 * 
	 * @return
	 */
	public Font getColumnHeaderFont() {
		return columnHeaderFont;
	}

	/**
	 * 设置列标题字体
	 * 
	 * @param columnHeaderFont
	 */
	public void setColumnHeaderFont(Font columnHeaderFont) {
		this.columnHeaderFont = columnHeaderFont;
	}

	/**
	 * 获取列标题背景色
	 * 
	 * @return
	 */
	public int getColumnHeaderBgColor() {
		return columnHeaderBgColor;
	}

	/**
	 * 设置列标题背景色
	 * 
	 * @param columnHeaderBgColor
	 */
	public void setColumnHeaderBgColor(int columnHeaderBgColor) {
		this.columnHeaderBgColor = columnHeaderBgColor;
	}

	/**
	 * 获取行标题字体
	 * 
	 * @return
	 */
	public Font getRowHeaderFont() {
		return rowHeaderFont;
	}

	/**
	 * 设置行标题字体
	 * 
	 * @param rowHeaderFont
	 */
	public void setRowHeaderFont(Font rowHeaderFont) {
		this.rowHeaderFont = rowHeaderFont;
	}

	/**
	 * 获取行标题背景色
	 * 
	 * @return
	 */
	public int getRowHeaderBgColor() {
		return rowHeaderBgColor;
	}

	/**
	 * 设置行标题背景色
	 * 
	 * @param rowHeaderBgColor
	 */
	public void setRowHeaderBgColor(int rowHeaderBgColor) {
		this.rowHeaderBgColor = rowHeaderBgColor;
	}

	/**
	 * 获取行标题字体颜色
	 * 
	 * @return
	 */
	public int getRowHeaderFontColor() {
		return rowHeaderFontColor;
	}

	/**
	 * 设置行标题字体颜色
	 * 
	 * @param rowHeaderFontColor
	 */
	public void setRowHeaderFontColor(int rowHeaderFontColor) {
		this.rowHeaderFontColor = rowHeaderFontColor;
	}

	/**
	 * 返回当前被选择的row
	 * 
	 * @return
	 */
	protected Row getSelectRow() {
		return model.getRow(getCurrentRow());
	}

	/**
	 * 如果选择模式为允许多行选择，则返回当前选中的行
	 * 
	 * @return
	 */
	protected Row[] getSelectRows() {
		if (!isAllowMutltiRowSelected()) {
			return null;
		}
		int rowNumber = Math.abs(getEndSelectRow() - getStartSelectRow()) + 1;
		if (rowNumber == 0) {
			return null;
		}
		int start = getEndSelectRow() > getStartSelectRow() ? getStartSelectRow()
				: getEndSelectRow();
		Row[] rows = new Row[rowNumber];
		for (int i = 0; i < rowNumber; i++) {
			rows[i] = model.getRow(i + start);
		}
		return rows;
	}

	/**
	 * 插入一行数据
	 * 
	 * @param rowCellValues
	 */
	public void insertRow(String rowCellValues[]) {
		PositionInfo oldPosition = new PositionInfo();
		int endRow = getEndRow();
		getModel().insertRow(oldPosition.currentRow, rowCellValues);
		updateModel();
		if ((oldPosition.startRow == 0) && (oldPosition.currentRow < endRow)) {
			oldPosition.currentRow++;
		} else {
			oldPosition.startRow++;
			oldPosition.currentRow++;
		}
		oldPosition.setPosition();
		isShowTip = false;
	}

	/**
	 * 增加一行数据
	 * 
	 * @param rowCellValues
	 */
	public void appendRow(String rowCellValues[]) {
		PositionInfo oldPosition = new PositionInfo();
		getModel().addRow(rowCellValues);
		updateModel();
		oldPosition.setPosition();
	}

	/**
	 * 删除当前行数据
	 */
	public void removeCurrentRow() {
		if (getModel().getRowCount() > 0) {
			PositionInfo oldPosition = new PositionInfo();
			getModel().removeRow(getCurrentRow());
			updateModel();
			if (oldPosition.currentRow > 0) {
				if (oldPosition.startRow > 0) {
					oldPosition.startRow--;
				}
				oldPosition.currentRow--;
			}
			oldPosition.setPosition();
		}
	}

	/**
	 * remove all of rows for current table
	 */
	protected void removeAllRows() {
		getModel().removeAllRows();
		updateModel();
	}

	/**
	 * 用来拷贝当前表格的坐标信息
	 * 
	 * @author ehanrli
	 * 
	 */
	class PositionInfo {
		int currentRow = getCurrentRow();
		int currentCol = getCurrentCol();
		int startRow = getStartRow();
		int startCol = getStartCol();

		public void setPosition() {
			// 注意,设定必须保证先后顺序
			setStartRow(this.startRow);
			setCurrentRow(this.currentRow);
			setStartCol(this.startCol);
			setCurrentCol(this.currentCol);
		}
	}

	/**
	 * 用于显示只读的文本信息
	 * 
	 * @author tooy
	 * 
	 */
	class TextDisplay extends Form implements CommandListener {
		private final Command OK = new Command("完成", Command.OK, 1);
		private KTable parent;
		private Vector txtInput;

		public TextDisplay(String text, KTable parent) {
			super("输入内容");
			txtInput=new Vector();
			for(int i=0;i<model.getColCount();i++)
			{
				txtInput.addElement(new TextField(getColumnTitle(i),model.getCell(getCurrentRow(), i).getValue(),100,0));
				append((TextField)txtInput.elementAt(i));
			}		
			//append(text);
			this.parent = parent;
			addCommand(OK);
			setCommandListener(this);
		}

		public void commandAction(Command c, Displayable d) {
			for(int i=0;i<txtInput.size();i++)
			{
				model.setValue(getCurrentRow(), i, ((TextField)txtInput.elementAt(i)).getString());
			}
			System.gc();
			CMForm.display.setCurrent(parent);
		}
	}

	/**
	 * 用于显示可编辑的文本信息
	 * 
	 * @author tooy
	 * 
	 */
	/*class TextInput extends TextBox implements CommandListener {

		private final Command CMD_OK = new Command("确定", Command.OK, 1);
		private final Command CMD_CANCEL = new Command("取消", Command.CANCEL, 1);

		KTable parent;
		private String originalText;

		public TextInput(String text, KTable parent) {
			super("输入内容", text,1600, TextField.ANY);
			this.parent = parent;
			addCommand(CMD_OK);
			addCommand(CMD_CANCEL);
			setCommandListener(this);
			this.originalText = text;
		}

		public void commandAction(Command c, Displayable d) {
			if (c == CMD_OK) {
				if (dataChanged()) {
					parent.setCurrentCellValue(getString());
				}
			}
			frame.display.setCurrent(parent);
		}

		private boolean dataChanged() {
			return (getString() != null && !getString().equals(originalText))
					&& (originalText != null && !originalText
							.equals(getString()));
		}

	}*/

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO 自动生成方法存根
		//super.commandAction(cmd, arg1);
		if (cmd == insertCmd) {
			insertRow(createCellValues(getModel().getRowCount(), model.getColCount()));
			repaint();
		} else if (cmd == appendCmd) {
			appendRow(createCellValues(getModel().getRowCount(), model.getColCount()));
			repaint();
			setDefaultTip();
		} else if (cmd == deleteCurrentRow) {
			removeCurrentRow();
			repaint();
//			setDefaultTip();
		} else if (cmd == deleteAll) {
			removeAllRows();
			repaint();
			setDefaultTip(); //不设置则不显示
		}else if(cmd==back)
		{
			CMForm.display.setCurrent(xWForm);
		}
		
	}
	public static String[] createCellValues(int row, int colNumber) {
		String[] cellValues = new String[colNumber];
		for (int i = 0; i < colNumber; i++) {
			cellValues[i] = "新增";	
		}
		return cellValues;
	}

}
