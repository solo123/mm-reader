package com.caimeng.uilibray.component;


import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.event.ComboBoxListener;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;

public class ComboBox extends List {
	
	private int extraHeight = 40;

	private boolean listOpen = false;

	public int comboHeight = 0;// 关闭高度 与super.height相同

	private int maxOnshowItem =5;

	public int startItem = 0;
	
	private String title=null;
	
	private String ID;
	
	public int screenWidth;
	
	private int offsetY;
	public boolean hasScrollh=false;
	
	public int listNum=5;//定义下拉显示几条记录的高度,  默认5条高

	public ComboBox(){
		super();
	}
	
	public void setMaxItem(int i)
	{
		maxOnshowItem=i;
	}
	
	public ComboBox(int x, int y, int width) {
		super(x, y, width, 18);
		comboHeight = super.height;
		this.style=this.isComboBox;		
//		bgTricolorR=Attribute.text_backR;
//		bgTricolorG=Attribute.text_backG;
//		bgTricolorB=Attribute.text_backB;
	}
	public int getENG(String str){
		int num=0;
		char c;
		 for (int i = 0; i < str.length(); i++) {
             c = str.charAt(i);
             if ((c > '0' && c< '9') || (c > 'a' && c < 'z') ||
            		 (c >'A' && c < 'Z')) {
            	 
            	 num++;
             }
		 }
		return num;
	}
	
public void paint(Graphics g) {		
	int clipx = g.getClipX();
	int clipy = g.getClipY();
	int clipwidth = g.getClipWidth();
	int clipheight = g.getClipHeight();
	g.setClip(clipx, clipy, clipwidth, clipheight);
		int posX =0 ;
		int posY=0;
		posX=x + ui.offsetX;
		posY=y + ui.offsetY;
		
		int actualHeight=0;
		int num=0;
		if(itemSum>listNum){
			//实际条数>固定条数
			actualHeight=listNum*itemHeight+10;
		}else{
			actualHeight=itemSum*itemHeight+10;
		}
	/*	if(focus){
			if(posY>0)
			if(posY+itemHeight>ui.xWForm.frm_Height-30){
				ui.offsetY=ui.offsetY-(itemHeight+30);
				ui.xWForm.repaint();
			}else if(posY<20)
			{
				ui.offsetY=0;
			}
		}*/
		
		if(title!=null){
			g.setColor(ColorAttribute.label_title_text);
			int length=title.length();
			num=getENG(title);//得到英文字符个数			
			if(num>0){
				length=length-num/2;
			}
			g.setFont(smallHeavryFont);
			if(length<5)
				posX = x + ui.offsetX+smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth("用")*4;
			else
				posX = x + ui.offsetX+smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth(title);
			g.drawString(title + ":",x+ui.offsetX, y + ui.offsetY, g.TOP|g.LEFT);
		}
		if(posX+width>ui.xmlForm.frm_Width){			
			posX=x+ui.offsetX+5;
			posY=y+ui.offsetY+smallHeavryFont.getHeight()+5;
			height=18+smallHeavryFont.getHeight()+5;
		}
		//System.out.println("height"+ui.xWForm.frm_Height);
		
		g.setFont(smallFont);
		if (listOpen) {
			if(posY+actualHeight+itemHeight>ui.xmlForm.frm_Height-30){
				ui.offsetY=ui.offsetY-(actualHeight);
				ui.xmlForm.repaint();
			}
			// paint comboBox
			// paint extraHeight
			g.setColor(ColorAttribute.label_context);
			g.fillRect(posX, posY, width /*+UIManager.triangle.getWidth()*/,actualHeight+itemHeight-10+UIManager.triangle.getHeight());
			g.setColor(102,102,102);
			g.drawRect(posX, posY, width /*+UIManager.triangle.getWidth()*/, actualHeight+itemHeight-10+UIManager.triangle.getHeight());
			g.drawImage(UIManager.triangle, posX + width-UIManager.triangle.getWidth() , posY, Graphics.LEFT | Graphics.TOP);
			g.drawRect(posX, posY , width , UIManager.triangle.getHeight()-1);
			
			// paint selector
//			g.setClip(posX, posY+UIManager.triangle.getHeight()-1 , width - comboHeight+UIManager.triangle.getWidth()-1 , actualHeight);
			if(onShowSelectedItemNum>=0)
			ImageDiv.drawJiuGong(g, UIManager.select_bright,posX+3, posY+ UIManager.triangle.getHeight() + onShowSelectedItemNum * itemHeight+10-offsetY,width/*+UIManager.triangle.getWidth()*/-3, itemHeight);
			// paint other items
			
			g.setClip(posX, posY+UIManager.triangle.getHeight()-1, width ,actualHeight+itemHeight-10);
			g.setColor(ColorAttribute.label_context);
			if(itemSum>2){
				
				if ((onShowSelectedItemNum + 2) * itemHeight > actualHeight) {
					offsetY = (onShowSelectedItemNum + 2) * itemHeight - actualHeight;
				} else {
					offsetY = 0;
				}
			}
			int length = itemSum < maxOnshowItem ? itemSum : maxOnshowItem;
			g.setClip(posX, posY+UIManager.triangle.getHeight() , width /*+UIManager.triangle.getWidth()*/ , actualHeight+itemHeight-10);
			/*g.setColor(255,255,255);
			g.fillRect(posX-1, posY+UIManager.triangle.getHeight(), width - comboHeight+UIManager.triangle.getWidth()-2, actualHeight+itemHeight-11);
			g.setColor(0);*/
			
			for (int i = 0; i < length; i++) {
				g.drawString(items.elementAt(i + startItem).toString(), posX+5, posY + UIManager.triangle.getHeight() + i * itemHeight+10-offsetY, Graphics.LEFT| Graphics.TOP);
			}
		
			g.setClip(clipx, clipy, clipwidth, clipheight);
			ui.xmlForm.repaint();
			//if focus
//			if(focus){
//				g.setColor(0,138,185);
//				g.drawRect(posX, posY , width - comboHeight, UIManager.triangle.getHeight()-1);
//			}
		} else {
			g.setClip(clipx, clipy, clipwidth, clipheight);
			g.setColor(255,255,255);
			g.fillRect(posX, posY, width-UIManager.triangle.getWidth(),  UIManager.triangle.getHeight());
			g.setColor(102,102,102);
			g.drawRect(posX, posY , width-UIManager.triangle.getWidth() , UIManager.triangle.getHeight()-1);
			g.drawImage(UIManager.triangle, posX + width-UIManager.triangle.getWidth() , posY, Graphics.LEFT | Graphics.TOP);
			if(focus){
				g.setColor(0,220,247);
				g.drawRect(posX, posY , width , UIManager.triangle.getHeight()-1);
				g.drawRect(posX-1, posY-1 , width+2 , UIManager.triangle.getHeight()+1);
				//g.drawRect(posX-2, posY -2, width+4, UIManager.triangle.getHeight()+3);
				
			}		
			//System.out.println("time"+System.currentTimeMillis());
		}
		// last paint selected item
		if ((items.size() != 0) && (onShowSelectedItemNum >= 0)) {			
			g.setClip(posX, posY, width-UIManager.triangle.getWidth() , UIManager.triangle.getHeight());
			if(listOpen){
				g.setColor(255,255,255);
//				g.setColor(0);
				g.fillRect(posX, posY, width-UIManager.triangle.getWidth(),  UIManager.triangle.getHeight());
				g.setColor(102,102,102);
				g.drawRect(posX, posY , width-UIManager.triangle.getWidth(), UIManager.triangle.getHeight()-1);
				
				g.setColor(0, 0, 0);
				g.drawString(items.elementAt(startItem + onShowSelectedItemNum).toString(), posX+5, posY+2, g.TOP|g.LEFT);
//				g.drawImage(UIManager.triangle, posX + width , posY, Graphics.LEFT | Graphics.TOP);
			}else{
				g.setColor(0,0,0);
				g.drawString(items.elementAt(startItem + onShowSelectedItemNum).toString(),	posX+5, posY+2 + comboHeight / 2 - itemHeight / 2,Graphics.LEFT | Graphics.TOP);
			} 
			g.setClip(clipx, clipy, clipwidth, clipheight);
			
		}
		//画滚动条
		/*if(hasScrollh && listOpen){
			
			
			
			
			int scrollH=itemHeight;
			int dh=((actualHeight-scrollH)/(itemSum>0?itemSum:itemSum+1));
			if(((actualHeight-scrollH)%(itemSum>0?itemSum:itemSum+1))>itemSum/2){
				dh++;
			}
			int dy=(dh)*(onShowSelectedItemNum);
			if((onShowSelectedItemNum)==itemSum-1){
				int mistake=actualHeight-dy-dh-scrollH;
				if(mistake!=0)
					dy=dy+mistake;
				
			}
			g.setClip(0, posY+UIManager.triangle.getHeight()-1,ui.xWForm.frm_Width, itemHeight*maxOnshowItem+10);		
			g.setColor(0);
			g.drawRect(posX + width-5,posY+UIManager.triangle.getHeight(),5,actualHeight);
			g.setColor(211,211, 211);
			g.fillRect( posX + width-5+1,posY+UIManager.triangle.getHeight()+1,4,actualHeight-1);
			g.setColor(0);	
			g.drawRect(posX + width-5,posY+UIManager.triangle.getHeight()+dy-1, 5, scrollH+1+dh);
			g.setColor(0, 220, 247);		
			g.fillRect(posX + width-5+1,posY +UIManager.triangle.getHeight()+ dy,4, scrollH+dh);
			g.setClip(clipx, clipy, clipwidth, clipheight);
		}*/
		
		
	}

	public void addComboBoxListener(ComboBoxListener e){
		listenerList.add(e);
	}
	public void comboBoxSelected(int keyCode){
		ComboBoxListener item = this.listenerList.getComboBoxListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			item.comboBoxSelected(e);
		}	
	}

	public void setSelectedItem(int itemNum) {
		this.onShowSelectedItemNum = itemNum;
	}

	public void addItem(String item) {
		items.addElement(item);
		itemSum = items.size();
		if(smallFont.stringWidth(item)+30>width) {
			width=smallFont.stringWidth(item)+30;	
			if(width>CMForm.frm_Width){
				width=CMForm.frm_Width-15;
			}
		}
		//width=100;
		setExtraHeight();
	}

	public void setItem(String[] str){
		items=new Vector();
		int length=str.length;
		for(int i=0;i<length;i++){
			items.addElement(str[i]);
		}
		itemSum=items.size();
		setExtraHeight();
	}
	public boolean removeItem(int index) {
		try {
			items.removeElementAt(index);
			itemSum = items.size();
			setExtraHeight();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean removeAllItem() {
		try {
			items.removeAllElements();
			itemSum = 0;
			maxOnshowItem=0;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setExtraHeight() {
		if (itemSum <= maxOnshowItem) {
			extraHeight = itemSum * itemHeight;
		} else {
			extraHeight = maxOnshowItem * itemHeight;
		}
		// 每当Extraheight改变 height随之改变
//		height = comboHeight + extraHeight;
	}

	// 按键按下的时候做selector响应
	public void keyPressed(int keyCode) {
		if(this.isPass()){
			return;
		}
		super.keyPressed(keyCode);
		if (!ui.selector.inComboItem) {
			ui.selector.inComboItem = true;
			listOpen = true;
		} else {
			if (keyCode == Key.UPKEY(keyCode)) {
				if (onShowSelectedItemNum > 0) {
					onShowSelectedItemNum--;
				} else if (startItem > 0) {
					startItem--;
				}
				
			} else if (keyCode == Key.DOWNKEY(keyCode)) {
				if (onShowSelectedItemNum < maxOnshowItem - 1) {
					onShowSelectedItemNum++;
				} else if (startItem < itemSum - onShowSelectedItemNum - 1) {
					startItem++;
				}
			} else if (keyCode == Key.FIREKEY(keyCode)) {
				ui.selector.inComboItem = false;
				listOpen = false;
//				itemStateChanged(keyCode);
				comboBoxSelected(keyCode);
			}
		}
	}

	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
		//itemStateChanged(keyCode);
	}
	
	public int getIndexof(String itemname){
		return items.indexOf(itemname);
	}
	
	public String getSelectedItem() {
		if(startItem+onShowSelectedItemNum<0){
			return "无";
		}
		return items.elementAt(startItem + onShowSelectedItemNum).toString();
	}
	public int getSelectedItemIndex() {
		return startItem + onShowSelectedItemNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		int posX = x +smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth(title);
		
		if(posX+width>CMForm.frm_Width){
			height=18+smallHeavryFont.getHeight()+5;
		}
		//this.screenWidth=ui.width;
		//width+=smallFont.stringWidth(title+":")+5;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
}
