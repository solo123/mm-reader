package com.caimeng.uilibray.table;

/**
 * 列属性对象，用来设置列标题和列显示宽度
 * @author Administrator
 *
 */
public class ColumnAttribute {

	/**
	 * 最大显示字符数
	 */
	private int maxDisplayCharNumber;
	
	
	
	/**
	 * 列标题
	 */
	private String title;

	
	/**
	 * 指定排序模式
	 */
	private boolean isASC = false;
	
	
	/**
	 * 创建一个列属性对象
	 * @param maxDisplayChar 列显示最大字符数，如果为0则说明是自适应
	 * @param title 列标题
	 */
	public ColumnAttribute(int maxDisplayChar, String title) {
		super();
		this.maxDisplayCharNumber = maxDisplayChar;
		this.title = title;
	}

	/**
	 * 当前列最大显示字符宽度
	 * @return
	 */
	public int getMaxDisplayCharNumber() {
		return maxDisplayCharNumber;
	}

	/**
	 * 设置当前列最大显示字符宽度
	 * @param maxDisplayChar
	 */
	public void setMaxDisplayCharNumber(int maxDisplayChar) {
		this.maxDisplayCharNumber = maxDisplayChar;
	}

	/**
	 * 获取当前列标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置当前列标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 判断当前列排序方向
	 * @return
	 */
	public boolean isASC() {
		return isASC;
	}

	/**
	 * 设置当前列排序标志
	 */
	public void setSortFlag() {
		this.isASC = !isASC;
	}
	
}
