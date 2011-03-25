package com.caimeng.uilibray.table;

import java.util.Vector;

import com.caimeng.uilibray.utils.StringUtil;


/**
 * 表格行对象，用于存储表格中的一行数据
 * @author joycode workshop
 *
 */
public class Row {
	
	/**
	 * 单元格对象集合
	 */
	private Vector cells = new Vector();
	
	/**
	 * 行标题，行头
	 */
	private String title;
	
	
	/**
	 * 表格行缺省高度, 此属性未完全实现，不属于当前需求，为以后扩展保留
	 * 0表示取系统缺省高度
	 */
	private int rowHeight = 0;
	
	
	
	
	
	/**
	 * 创建一行
	 * @param colCount  必须指定列数 ，防止各行存在列数不匹配情况
	 * @param cellValues 以数组方式表现的行数据
	 * @return
	 */
	public static Row createRow(int colCount, String[] cellValues) {
		//Assert.notNull(cellValues);
		//Assert.notEquals(colCount, cellValues.length, "列数和指定的创建的列数不匹配");
		Row row = new Row();
		for (int i = 0; i < cellValues.length; i++) {
			row.addCell(new Cell(cellValues[i]));
		}
		return row;
	}
	
	/**
	 * 增加一个单元格
	 * @param cell
	 */
	private void addCell(Cell cell) {
		cells.addElement(cell);
	}
	
	/**
	 * 清空当前数据
	 */
	public void clean() {
		cells.removeAllElements();
	}
	
	/**
	 * 获取当前列数
	 * @return
	 */
	public int getColCount() {
		return cells.size();
	}

	/**
	 * 获取指定列的单元格对象
	 * @param col
	 * @return
	 */
	public Cell getColumnCell(int col) {
		//Assert.notBetween(0, getColCount()-1, col, "column index超出范围");
		return ((Cell) cells.elementAt(col));
	}

	/**
	 * 获取指定列的单元格值
	 * @param col
	 * @return
	 */
	public String getColumnValue(int col) {
		return getColumnCell(col).getValue();
	}

	/**
	 * 得到列标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置列标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置行高,此属性只做预留字段，当前不能使用
	 * @param rowHeight
	 */
	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}
	
	/**
	 * 获取行高, 此属性只做预留字段，当前不能使用
	 * @return
	 */
	public int getRowHeight() {
		return rowHeight;
	}
	
	/**
	 * 返回用于调试使用的字符串，将所有单元值拼接在一起
	 * @return
	 */
	public String getDebugString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getColCount(); i++) {
			sb.append(" | " + getColumnValue(i));
		}
		return sb.toString();
	}

	/**
	 * 判断当前表格是否有标题要显示
	 * @return
	 */
	public boolean hasHeader() {
		return StringUtil.isNotBlank(title);
	}

}
