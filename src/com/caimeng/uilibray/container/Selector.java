package com.caimeng.uilibray.container;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;

//selector只对确定键抬起做事件反应(按下不作反应)


public class Selector {

	public int br = 189;

	public int bg = 156;

	public int bb = 42;

	public int selected = 0;

	private UIManager ui = null;

	public boolean inComboItem = false;

	public boolean inInputItem = false;

	public boolean inEmComboItem = false;

	public boolean inEmInputItem = false;

	public boolean enableoffset = false;

	public boolean nineGrid = false;

	public boolean imageLabel = false;

	public boolean checkItem = false;
	
	public boolean checkBox=false;
	
	public boolean textBox=false;

	public boolean Tab = false;

	public boolean button = false;

	public boolean structTree = false;

	public boolean messageBox = false;
	
	public boolean radioBox=false;
	
	public boolean barCode=false;
	
	
	public boolean locate=false;
	
	public boolean isKTable=false;

	public Selector(UIManager ui, boolean enableoffset) {
		this.ui = ui;
		this.enableoffset = enableoffset;
	}

	public void selectorBack(int pre, int cur) {// 类似undo
		//if(ui.getItem(pre).isPass())
		ui.getItem(pre).focus = false;
		if(!ui.getItem(cur).isPass())
		ui.getItem(cur).focus = true;
		if (enableoffset) {
			setoffset(ui.getItem(cur));
		}
	}

	public boolean isLast = false;

	public boolean setSelected(int index) {
		selected = index;
		if (selected < (ui.getItemSum() - 1)) {
			isLast = false;
		} else {
			selected = ui.getItemSum() - 1;
			isLast = true;
		}
		setFocus(selected);
		BaseControl baseControl = ui.getItem(selected);
		if (enableoffset) {
			setoffset(baseControl);
		}
		return baseControl.isVisible();
	}

	public boolean nextElement() {
	
		if (selected < (ui.getItemSum() - 1)) {
			selected++;
			isLast = false;
		} else {
			selected = ui.getItemSum() - 1;
			isLast = true;
			// selected = 0;
		}
		//if(!isLast)
		
		setFocus(selected);
		BaseControl baseControl = ui.getItem(selected);
		if (enableoffset) {
			setoffset(baseControl);
		}
		//if(baseControl.isPass())
		//	baseControl.focus=false;
		return (baseControl.isVisible() && !baseControl.isPass());
		
	}

	public boolean preElement() {
		if (selected > 0) {
			selected--;
			isLast = false;
		} else {
			// selected = ui.getItemSum() - 1;
			selected = 0;
			isLast = true;
		}
		//if(!isLast)
		setFocus(selected);
		BaseControl baseControl = ui.getItem(selected);
		if (enableoffset) {
			setoffset(baseControl);
		}
		//if(baseControl.isPass())
		//	baseControl.focus=false;
		return (baseControl.isVisible() && !baseControl.isPass() );		
	}

	public void setFocus(int index) {
		int sum = ui.getItemSum();
		for (int i = 0; i < sum; i++) {
			ui.getItem(i).focus = false;
		}
		ui.getItem(index).focus = true;
	}

	private int lastOffx = 0;

	private int lastOffy = 0;

	private int curOffx = 0;

	private int curOffy = 0;

	//private Thread movingThread = null;

	public void setoffset(BaseControl item) {
		
		
		int width = ui.xmlForm.frm_Width;
		int height = ui.xmlForm.frm_Height;
		int x = item.x + item.width;
		int y = item.y+ item.height;
		// 移动的时候右下各让出20像素(下方再让出20像素用于Menu)
		lastOffx = ui.offsetX;
		lastOffy = ui.offsetY;
		curOffx = 0;
		curOffy = 0;
		if (x > width) {
			curOffx = width - x - 20;
		} else {
			curOffx = 0;
		}
		if (y > (height-UIManager.bottom_bg.getHeight()) ){
			curOffy = height - y - item.height;
		
		} else {
			curOffy = 0;
		}
	    if ((lastOffx != curOffx) || (lastOffy != curOffy)) {
				   if(item.style==item.isLabel&&item.height>=40) //特殊lable框架使用绝对值来移动屏幕
        	        {
					   
        	        	   if(ui.xmlForm.keyCode==Key.UPKEY(ui.xmlForm.keyCode))
						   { 
        	        		      if(lastOffy<0){
								      
        	        		    	  lastOffy = lastOffy +22;//22为一行label高度
							       }else
							       {
							    	   lastOffy = 0;
							       }
						   }else if(ui.xmlForm.keyCode==Key.DOWNKEY(ui.xmlForm.keyCode))
						   { 
							       if(lastOffy >curOffy ) {
	      							   curOffy=curOffy-22;
	 								   lastOffy = lastOffy - 22;
							       }
      					   }
        	        	   
        	         }else if (lastOffy > curOffy) {
						        if(item.height<=0) //不是控件item.height小于0特殊使用绝对值来移动屏幕
						        {
						        	 lastOffy = lastOffy -40;
						        }else
						        {
							         lastOffy = lastOffy - item.height;
						        }
								if (lastOffy - curOffy < 40) {
									  lastOffy = curOffy;
//									  System.out.println("11111  lastOffy:"+lastOffy+"    curOffy:"+curOffy);
							    }
								
								
					} else if (lastOffy < curOffy) {
						
						if(item.height<=0)  //不是控件item.height小于0特殊使用绝对值来移动屏幕
						{   
							    lastOffy = lastOffy + 40;
						}else 
						{
						        lastOffy = lastOffy + item.height;
						}
//						System.out.println("22222  lastOffy:"+lastOffy+"    curOffy:"+curOffy);
						if (y+ui.offsetY<40) {
								curOffy=curOffy+item.height;
								lastOffy = lastOffy + item.height;
					 	}
						
					}
        	        ui.offsetX = lastOffx;
        			ui.offsetY = lastOffy;
        			ui.xmlForm.repaint();
					
		}
	}
}
