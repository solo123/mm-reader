package com.caimeng.uilibray.info;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.BaseControl;


public class Tip {

	private String tip="";
	
	private BaseControl baseControl=null;
	
	public Tip(BaseControl baseControl){
		this.baseControl=baseControl;
	}
	
	public void setTip(String tip){
		this.tip=tip;
	}
	
	//具体怎么处理再议
	public void drawTip(Graphics g) {
		System.out.println("draw tip -->" +tip);
	}

}
