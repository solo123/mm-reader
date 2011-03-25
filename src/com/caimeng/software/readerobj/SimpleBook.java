package com.caimeng.software.readerobj;
/**
 * 4.获取每个类型的书 和 7.搜索书 只包含 书id 及书名称
 *
 */
public class SimpleBook {
	private String bookId;

	private String bookName;
/*	
	private String bookTypeID;
	
	private String bookTypeName;
*/
	/*public String getBookTypeID(){
		return this.bookTypeID;
	}
	public String getBookTypeName(){
		return this.bookTypeName;
	}
	public void setBookTypeID(String id){
		this.bookTypeID=id;
	}
	public void setBookTypeName(String name){
		this.bookTypeName=name;
	}*/
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	
}
