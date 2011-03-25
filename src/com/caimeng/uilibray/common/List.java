package com.caimeng.uilibray.common;

import java.util.Vector;

import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.event.ItemStateListener;


public class List extends BaseControl {

	public int onShowSelectedItemNum = 0;

	public Vector items = new Vector();

	public int itemSum = 0;

	public int itemHeight = smallFont.getHeight();
	
	public List(){
		super();
	}
	
	public List(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void addItemStateListener(ItemStateListener e) {
		listenerList.add(e);
	}
	
	public void itemStateChanged(int keyCode){
		ItemStateListener action = this.listenerList.getItemStateListener();
		if (action != null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			action.itemStateChanged(e);
		}
	}
	public void itemStateChanged(int x,int y){
		ItemStateListener action = this.listenerList.getItemStateListener();
		if (action != null) {
			ItemEvent e = new ItemEvent(this, x,y);
			action.itemStateChanged(e);
		}
	}

}
