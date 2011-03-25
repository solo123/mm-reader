package com.caimeng.uilibray.event;

import com.caimeng.uilibray.common.BaseControl;



public class ItemEvent {

	private BaseControl baseControl = null;

	private int keyCode = 0;
	
	private int x,y=0;

	public ItemEvent(BaseControl baseControl, int keyCode) {
		this.baseControl = baseControl;
		this.keyCode = keyCode;
	}
	public ItemEvent(BaseControl baseControl, int x,int y) {
		this.baseControl = baseControl;
		this.x=x;
		this.y=y;
	}

	public void addEvent(BaseControl baseControl, int keyCode) {
		this.baseControl = baseControl;
		this.keyCode = keyCode;
	}

	public void disPoseEvent() {
		this.baseControl = null;
		this.keyCode = 0;
	}

	public BaseControl getItem() {
		return baseControl;
	}

	public int getKeyCode() {
		return keyCode;
	}
}
