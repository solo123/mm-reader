package com.caimeng.software.readerobj;
/**
 * 6,获取每个书的章节
 * 
 */
public class BookChapterAllBean {
	private String chapterId;

	private String chapterName;

	private String length;
	
	private String bookName;
	
	private String page;
	
	public String getPage(){
		return this.page;
	}
	public void setPage(String page){
		this.page=page;
	}
	
	public String getBookName(){
		return bookName;
		
	}
	public void setBookName(String bookname){
		this.bookName=bookname;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
