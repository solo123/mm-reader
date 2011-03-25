package com.caimeng.software.readerobj;
/**
 * 5,获取每本书的资料
 *
 */
public class BookInfoAllBean {
	private String bookName;
	private String author;
	private String bookIntro;
	private String status;
	private String hot;
	private String picurl;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPicUrl(){
		return this.picurl;
	}
	public void setPicUrl(String url){
		this.picurl=url;
	}
	public String getBookIntro() {
		return bookIntro;
	}
	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
