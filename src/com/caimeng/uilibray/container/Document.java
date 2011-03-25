package com.caimeng.uilibray.container;

import java.util.Vector;

public class Document {

	// private int ID;
	private String title;

	private String titleID;

	private Vector content;

	public Document() {
	}

	public Vector getContent() {
		return content;
	}

	public void setContent(Vector content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleID() {
		return titleID;
	}

	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}
}
