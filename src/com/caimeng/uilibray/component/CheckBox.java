package com.caimeng.uilibray.component;


import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.DisplaySetting;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.ImageDiv;



public class CheckBox extends List {
	// private int width;
	// private int height;
	private boolean inBox;// 是否与选择按钮一起使用
  
	public boolean hasNonItem=false;
	public CheckBox() {
		super();
	}

	public CheckBox(int x, int y, int width, int height, boolean inBox) {
		super(x, y, width, height);
		onShowSelectedItemNum = -1;
		itemHeight = smallFont.getHeight();
		this.inBox = inBox;
		/*bgTricolorR = Attribute.text_backR;
		bgTricolorG = Attribute.text_backG;
		bgTricolorB = Attribute.text_backB;
		fgTricolorR = Attribute.text_foreR;
		fgTricolorG = Attribute.text_foreG;
		fgTricolorB = Attribute.text_foreB;*/
	}

	private int offsetY;
	
	private boolean richmode=false;

	private String title = null;

	private Vector items = new Vector();

	public Vector selector = new Vector();

	public Vector itemID = new Vector();

	private String ID;

	public void setSelectedItem(int itemNum) {
		onShowSelectedItemNum = itemNum - 1;
	}
	
	public int getIndexOf(String itemname){
		return items.indexOf(itemname);
	}

	public void addItem(String item) {
		richmode=false;
		items.addElement(item);
		itemSum = items.size();
		
		if(itemSum>1)
		{
			if ((smallFont.stringWidth(item)+24) > width) {
				width = smallFont.stringWidth(item)	+ 14+10;
				
			}			
		}else
		{
			width = smallFont.stringWidth(item)	+ 14+10;
		}
		selector.addElement(new Integer(0));
		height += smallFont.getHeight();
	}

	public void addItem(String item, int num) {
		richmode=true;
		items.addElement(item);
		itemSum = items.size();
		if(itemSum>1)
		{
			if ((smallFont.stringWidth(item)+24) > width) {
				width = smallFont.stringWidth(item)	+ 14+10;
			}
		}else
		{
			width = smallFont.stringWidth(item)	+ 14+10;
		}
		selector.addElement(new Integer(num));
		this.height+=smallFont.getHeight();
	}

	public void setListData(Vector data) {
		items = data;
		itemSum = items.size();
		for (int i = 0; i < itemSum; i++) {
			selector.addElement(new Integer(0));
			if (smallFont.stringWidth(data.elementAt(i).toString()) > width) {
				width = smallFont.stringWidth(data.elementAt(i).toString())
						+ 10 + 14;
			}
			this.height += smallFont.getHeight();
		}

	}

	public void keyPressed(int keyCode) {
		// super.keyPressed(keyCode);
		if(this.isPass()){
			return;
		}
		if (!ui.selector.checkBox) {
			ui.selector.checkBox = true;
		} else {
			if (keyCode == Key.UPKEY(keyCode)) {
				if (onShowSelectedItemNum == -1) {
					 ui.selector.checkBox = false;
				} else if (onShowSelectedItemNum > -1) {
					onShowSelectedItemNum--;
					/*if(y+ui.offsetY<60){
						ui.offsetY+=20;
					}*/
				}
				
			} else if (keyCode == Key.DOWNKEY(keyCode)) {
				if (onShowSelectedItemNum == (itemSum - 2)) {
					 ui.selector.checkBox = false;
				} else if (onShowSelectedItemNum < (itemSum - 2)) {
					onShowSelectedItemNum++;
					/*if(height+y+ui.offsetY>ui.xWForm.frm_Height-40){
						ui.offsetY-=20;
						ui.xWForm.repaint();
					}*/
				}
				
			} else if ((keyCode == Key.RIGHTKEY(keyCode)) || (keyCode == Key.LEFTKEY(keyCode))) {
				if (!inBox)
					ui.selector.checkBox = false;
				else
					ui.selector.button = false;
			} else if (keyCode == Key.FIREKEY(keyCode)) {
				if (!selector.elementAt(onShowSelectedItemNum + 1).equals(new Integer(1))) {
					selector.setElementAt(new Integer(1),onShowSelectedItemNum + 1);
				} else {
					selector.setElementAt(new Integer(0),onShowSelectedItemNum + 1);
				}
			} else if (keyCode == Key.LEFT_SOFT_KEY(keyCode)) {
				if (inBox) {
					actionPerformed(keyCode);
					ui.selector.checkBox = false;
				}

			}
		}
	}

	public void keyReleased(int keyCode) {
		// super.keyReleased(keyCode);
		itemStateChanged(keyCode);
	}

	public boolean removeItem(int index) {
		try {
			items.removeElementAt(index);
			itemSum = items.size();
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
			onShowSelectedItemNum=-1;
			//selector.removeAllElements();
			height=smallFont.getHeight();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getSelectedItem() {
		return items.elementAt(onShowSelectedItemNum + 1).toString();
	}
	
	public String getIndex(int num){
		return (String)items.elementAt(num);
	}

	public Vector getMultiSelected() {
		Vector c = new Vector();
		for (int i = 0; i < itemSum; i++) {
			if (selector.elementAt(i).equals(new Integer(1))) {
				c.addElement(items.elementAt(i));
			}
		}
		return c;
	}

	public void selectAll()
	{
		for(int i=0;i<itemSum;i++)
		{
			selector.setElementAt(new Integer(1),i);
		}
	}
	
	public void drawFocus(Graphics g) {

	}

	int posX = 0;
	int posY = 0;
	public void paint(Graphics g) {
		//g.setClip(0, 0, this.ui.xWForm.getWidth(), this.ui.xWForm.getWidth());
		if (inBox) {
			posX = (ui.xmlForm.frm_Width - width) / 2;
			posY = (ui.xmlForm.frm_Height - height) / 2;
		} else {
			posX = x + ui.offsetX;
			posY = y + ui.offsetY;
			/*
			if(focus){
				if(height<=ui.xWForm.frm_Height-60)
					if(posY+height+itemHeight-10>ui.xWForm.frm_Height-30){
						ui.offsetY=ui.offsetY-(ui.xWForm.frm_Height/2);
						ui.xWForm.repaint();
					}
			}*/
			if (title != null){
//				System.out.println("title==="+title);
				if(posX+smallHeavryFont.stringWidth(title)>ui.xmlForm.frm_Width){
					x=0;
					y=posY+smallHeavryFont.getHeight()+5;
				}
				posY = y + ui.offsetY+smallHeavryFont.getHeight()+5;
				posX=x+ui.offsetX+10;
				
			}
		}
		if (title != null){
			
			g.setColor(0);
			g.setFont(smallHeavryFont);
			g.drawString(title + ":", x + ui.offsetX, y + ui.offsetY, 0);
			
		}
//		if (focus) {
		
		/*if(items.size()==0){
			height=0;
		}
		if(height!=0)*/
		/*int h=0;
		if(richmode){
			h=title!=null?height-smallFont.getHeight()+5:0;
		}else{
			h=height+smallFont.getHeight()-10;
		}*/
		ImageDiv.drawJiuGong(g, UIManager.select_bright, posX - 3, posY - 3, width + 10,title!=null?height-smallFont.getHeight()+5:height);
		
//			
//		}
//		g.setColor(bgTricolorR, bgTricolorG, bgTricolorB);
//			g.setColor(125);
//		g.fillRect(posX, posY, width, height-(title!=null?smallFont.getHeight()-5:0));
		g.setFont(DisplaySetting.TEXT_FONT);
		if ((onShowSelectedItemNum + 2) * itemHeight > height) {
			offsetY = (onShowSelectedItemNum + 2) * itemHeight - height;
		} else {
			offsetY = 0;
		}
		/*if(focus)
		if (onShowSelectedItemNum + 1 >= 0) {
			ImageDiv.drawJiuGong(g, UIManager.connect_bg, posX, posY
					+ (onShowSelectedItemNum + 1) * itemHeight - offsetY,
					width, itemHeight);
		}*/
		int clipx = g.getClipX();
		int clipy = g.getClipY();
		int clipwidth = g.getClipWidth();
		int clipheight = g.getClipHeight();
		g.setClip(posX, posY, width, height);
//		g.setColor(Attribute.);
		// g.setFont(font);
		g.setFont(smallFont);
		g.setColor(ColorAttribute.label_context);
		int length = items.size();
		for (int i = 0; i < length; i++) {
			g.setClip(posX, posY, width, height);
			if(i==onShowSelectedItemNum + 1 && focus){
				g.setColor(0, 220, 247);
				if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY>ui.xmlForm.frm_Height-40){
					ui.offsetY-=20;
					ui.xmlForm.repaint();
					System.out.println("draw it");
				}else if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY<20){
					ui.offsetY+=20;
					ui.xmlForm.repaint();
					System.out.println("draw it");
				}
//				System.out.println("draw it");
			}else{
				g.setColor(0);
			}
			if (items.elementAt(i) != null) {
				g.drawString(items.elementAt(i).toString(), posX+5+ (UIManager.select_block.getWidth()>>2)+ 2, posY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
			}
			if(!hasNonItem)
			ImageBuilder.drawSmallImage(g, UIManager.select_block, posX+5,posY + i
					* itemHeight - offsetY + 2, (UIManager.select_block.getWidth()>>2), UIManager.select_block.getHeight(), selector.elementAt(i).equals(new Integer(1)) ?(UIManager.select_block.getWidth()>>2)*0:(UIManager.select_block.getWidth()>>2)*1, 0);
		}
		if (inBox) {
			g.drawImage(UIManager.select_bright, posX + width - 1, posY
					+ (height * (onShowSelectedItemNum + 1) / length),
					Graphics.RIGHT | Graphics.TOP);
		}
		g.setClip(clipx, clipy, clipwidth, clipheight);
		/*if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY>ui.xWForm.frm_Height-40){
			ui.offsetY-=20;
			ui.xWForm.repaint();
		}else if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY<20){
			ui.offsetY+=20;
		}*/

	}

	public boolean isInBox() {
		return inBox;
	}

	public void setInBox(boolean inBox) {
		this.inBox = inBox;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		height += smallFont.getHeight() + 5;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

}
