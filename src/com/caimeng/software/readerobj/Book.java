package com.caimeng.software.readerobj;

/**
 * 
 * 2.新书 和 3.阅读最多的书 包括书id ,名称，该书类型id,类型名称
 */
public class Book {
	private String bookTypeId;

	private String bookType;

	private String bookId;

	private String bookName;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

}
