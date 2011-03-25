package com.caimeng.uilibray.table;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Font;

import com.caimeng.uilibray.utils.DisplaySetting;
import com.caimeng.uilibray.utils.StringUtil;

/**
 * 表格对象模型
 * 用于存储表格所展现的数据，此模型是基于行的方式进行存储
 * 要求所有的列信息必须一次创建，但是行则可以动态做增删改操作
 * 
 * @author Administrator
 * 
 */
public class TableModel {

	private Font fontStyle = DisplaySetting.TEXT_FONT;
	/**
	 * 列属性设定对象
	 */
	private ColumnAttribute[] columnAttributes;

	/**
	 * 行显示高度
	 */
	private int rowHeight = fontStyle.getHeight()+2;

	/**
	 * row对象集合
	 */
	private Vector rows = new Vector();
		
	/**
	 * 创建表格model， 表格的列数必须预先指定，行则可以动态增加或删除
	 * 
	 * @param cols
	 * @return
	 */
	public TableModel(ColumnAttribute[] cols) {
		super();
		//Assert.notNull(cols);
		this.columnAttributes = cols;
	}

	/**
	 * 检测当前model是否有row header要显示
	 * 
	 * @return
	 */
	public boolean hasRowHeader() {
		for (int i = 0; i < rows.size(); i++) {
			if (getRow(i).hasHeader()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前列属性设定
	 * @return
	 */
	public ColumnAttribute[] getColumnAttributes() {
		return columnAttributes;
	}

	public ColumnAttribute getColumnAttributes(int i)
	{
		return columnAttributes[i];
	}
	
	
	/**
	 * 在模型末位增加一行
	 * 
	 * @param cellValues
	 */
	public void addRow(String[] cellValues) {
		Row row = Row.createRow(getColCount(), cellValues);
		rows.addElement(row);
	}

	/**
	 * 删除指定行
	 * 
	 * @param row
	 */
	public void removeRow(int row) {
		Row rowObj = getRow(row);
		rows.removeElement(rowObj);
	}

	/**
	 * 在指定位置插入一行数据
	 * 
	 * @param row
	 * @param cellValues
	 */
	public void insertRow(int row, String[] cellValues) {
		//checkRowIndex(row);
		Row rowObj = Row.createRow(getColCount(), cellValues);
		rows.insertElementAt(rowObj, row);
	}
	
	/**
	 * 在模型末尾加人一行数据
	 * @param row
	 * @param cellValues
	 */
	public void appendRow(int row, String[] cellValues) {
		//checkRowIndex(row);
		Row rowObj = Row.createRow(getColCount(), cellValues);
		rows.addElement(rowObj);
	}
	/**
	 * 获取特定行的数据
	 * 
	 * @param row
	 * @return
	 */
	public Row getRow(int row) {
		//checkRowIndex(row);
		return (Row) rows.elementAt(row);
	}

	/**
	 * 行列都是从0开始计算的
	 * 
	 * @param row
	 * @param col
	 */
	public void setValue(int row, int col, String value) {
		getRow(row).getColumnCell(col).setValue(value);
	}

	/**
	 * 行列都是从0开始计算的
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public String getValue(int row, int col) {
		Row rowObj = getRow(row);
		return rowObj.getColumnValue(col);
	}

	/**
	 * 需要拿到特定的cell对象对cell的熟悉进行设置
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Cell getCell(int row, int col) {
		if (getRowCount() == 0) {
			return null;
		}
		Row rowObj = getRow(row);
		return rowObj.getColumnCell(col);
	}

	/**
	 * 设置特定单元格的只读熟悉
	 * @param row
	 * @param col
	 * @param readOnly
	 */
	public void setCell(int row, int col, boolean readOnly) {
		getCell(row, col).setReadOnly(readOnly);
	}

	/**
	 * 求当前行总数
	 * 
	 * @return
	 */
	public int getRowCount() {
		return rows.size();
	}

	/**
	 * 求当前列总数
	 * 
	 * @return
	 */
	public int getColCount() {
		return columnAttributes != null ? columnAttributes.length : 0;
	}

	/**
	 * 返回当前行的指定高度，为0表示使用表格的缺省高度
	 * 
	 * @return
	 */
	public int getRowHeight() {
		return rowHeight;
	}

	/**
	 * 设置当前行高 此方法暂时不支持
	 * 
	 * @param rowHeight
	 */
	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	/**
	 * 检测某个单元格是否为只读
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isReadonly(int row, int col) {
		Cell cell = getCell(row, col);
		if (cell != null) {
			return cell.isReadOnly();
		}
		return false;
	}

	/**
	 * 检查row的index是否合法
	 * 
	 * @param row
	 */
	/*private void checkRowIndex(int row) {
		Assert.notBetween(0, getRowCount() - 1, row, "row index 超出范围");
	}*/

	/**
	 * 指定对某一列进行排序
	 * 排序以开关方式实现升降序操作
	 * @param col
	 * @param isDesc
	 */
	public void sortColumn(int col) {
		getColumnAttributes()[col].setSortFlag();
		Hashtable keys = new Hashtable();
		String[] columnValues = new String[getRowCount()];
		String value = null;
		for (int i = 0; i < getRowCount(); i++) {
			value = getValue(i, col);
			// 先将row对象放置到一个集合中，排序结束以后再根据key值取出row对象重新排列
			keys.put(value + "+" + i, getRow(i));
			columnValues[i] = value + "+" + i;
		}
		String[] sortedColumn = StringUtil.sort(columnValues);
		rows.removeAllElements();
		if (columnAttributes[col].isASC()) {
			for (int i = 0; i < sortedColumn.length; i++) {
				rows.addElement(keys.get(sortedColumn[i]));
			}
		} else {
			for (int i = sortedColumn.length - 1; i >= 0; i--) {
				rows.addElement(keys.get(sortedColumn[i]));
			}
		}
	}

	/**
	 * 设置特定row的标题
	 * @param row
	 * @param title
	 */
	public void setRowTitle(int row, String title) {
		getRow(row).setTitle(title);
	}

	/**
	 * 删除所有行
	 */
	public void removeAllRows() {
		rows.removeAllElements();
	}
	
	
	
}
