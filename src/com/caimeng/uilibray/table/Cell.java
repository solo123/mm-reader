package com.caimeng.uilibray.table;

/**
 * 单元格对象，代表表格中的一个单元格
 * @author JoyCode workshop
 * 
 */
public class Cell {

	/**
	 * 字体颜色
	 */
	private int fontColor =-1;

	/**
	 * 单元格背景颜色
	 */
	private int bgColor = -1;

	/**
	 * 是否只读
	 * 
	 * @tbd 以后使用
	 */
	private boolean readOnly = false;

	/**
	 * 值
	 */
	private String value;

	// 数据类型常量
	// Field descriptor #133 I
	public static final int ANY = 0;

	// Field descriptor #133 I
	public static final int EMAILADDR = 1;

	// Field descriptor #133 I
	public static final int NUMERIC = 2;

	// Field descriptor #133 I
	public static final int PHONENUMBER = 3;

	// Field descriptor #133 I
	public static final int URL = 4;

	// Field descriptor #133 I
	public static final int DECIMAL = 5;

	// Field descriptor #133 I
	public static final int PASSWORD = 65536;

	// Field descriptor #133 I
	public static final int UNEDITABLE = 131072;

	// Field descriptor #133 I
	public static final int SENSITIVE = 262144;

	// Field descriptor #133 I
	public static final int NON_PREDICTIVE = 524288;

	// Field descriptor #133 I
	public static final int INITIAL_CAPS_WORD = 1048576;

	// Field descriptor #133 I
	public static final int INITIAL_CAPS_SENTENCE = 2097152;

	// Field descriptor #133 I
	public static final int CONSTRAINT_MASK = 65535;
	
	
	
	/**
	 * 数据类型，注意，此属性只是为用户自行扩展保留当前并无特殊关联意义
	 * 比如用户希望限定某个单元的输入类型，则可以自行覆盖fire事件
	 * 根据数据类型创建不同的数据text限制输入
	 */
	private int dataType = ANY;
	
	//cell的坐标属性
	
	/**
	 * cell 左x坐标
	 */
	private int leftTopX;
	/**
	 * cell 左y坐标
	 */
	private int leftTopY;
	
	/**
	 * cell右下x坐标
	 */
	private int rightBottomX;
	
	/**
	 * cell 右下y坐标
	 */
	private int rightBottomY;

	/**
	 * 获取当前cell左上x坐标
	 * @return
	 */
	public int getLeftTopX() {
		return leftTopX;
	}

	/**
	 * 设置当前cell左上x坐标
	 * @param leftTopX
	 */
	public void setLeftTopX(int leftTopX) {
		this.leftTopX = leftTopX;
	}

	/**
	 * 获取当前cell左上y坐标
	 * @return
	 */
	public int getLeftTopY() {
		return leftTopY;
	}

	/**
	 * 设置当前cell左上y坐标
	 * @param leftTopY
	 */
	public void setLeftTopY(int leftTopY) {
		this.leftTopY = leftTopY;
	}

	/**
	 * 获取当前cell右下x坐标
	 * @return
	 */
	public int getRightBottomX() {
		return rightBottomX;
	}

	/**
	 * 设置当前cell右下x坐标
	 * @param rightBottomX
	 */
	public void setRightBottomX(int rightBottomX) {
		this.rightBottomX = rightBottomX;
	}

	/**
	 * 获取当前cell右下x坐标
	 * @return
	 */
	public int getRightBottomY() {
		return rightBottomY;
	}

	/**
	 * 设置当前cell右下y坐标
	 * @param rightBottomY
	 */
	public void setRightBottomY(int rightBottomY) {
		this.rightBottomY = rightBottomY;
	}

	
	/**
	 * 使用指定值创建一个cell对象
	 * @param cellValue
	 */
	public Cell(String cellValue) {
		this.value = cellValue;
	}

	
	/**
	 * 判断当前对象是否为只读
	 * @return
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * 设置当前对象的只读属性
	 * @param readOnly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 获取当前值
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置当前值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取当前数据类型
	 * @return
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * 设置当前数据类型
	 * @param dataType
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

//	public int getFontStyle() {
//		return fontStyle;
//	}
//
//	public void setFontStyle(int fontStyle) {
//		this.fontStyle = fontStyle;
//	}

	/**
	 * 获取当前字体颜色
	 */
	public int getFontColor(int defaultColor) {
		return fontColor == -1 ? defaultColor : fontColor;
	}

	/**
	 * 设置当前字体颜色
	 * @param fontColor
	 */
	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * 获取背景色
	 * @param defaultColor
	 * @return
	 */
	public int getBgColor(int defaultColor) {
		return bgColor == -1 ? defaultColor : bgColor;;
	}

	/**
	 * 设置背景色
	 * @param bgColor
	 */
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	
}
