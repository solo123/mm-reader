package com.caimeng.uilibray.component;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.utils.StringEx;

public class TextArea extends List {
	
	
	public TextArea(int x, int y, int width, int height) {
		super(x, y, width, height);	
		//onShowSelectedItemNum=100;
		//bgTricolorR=Attribute.text_backR;
		//bgTricolorG=Attribute.text_backG;
		//bgTricolorB=Attribute.text_backB;
	}

	public String text = "";
	
	private String title=null;

	private boolean showScrollBar=false;

	public void append(String text) {
		this.text = this.text + text;
		line = StringEx.tokenCast(this.text, width-4, smallFont);
		if(line.size()>height/smallFont.getHeight()){
			line = StringEx.tokenCast(this.text, width-10, smallFont);
			showScrollBar=true;
		}
		itemSum=line.size();
	}

	public Vector line = new Vector();
	//public int onShowItemNum=0;
	public void paint(Graphics g) {
		int[] clip=new int[4];
		clip[0] = g.getClipX();
		clip[1] = g.getClipY();
		clip[2] = g.getClipWidth();
		clip[3] = g.getClipHeight();
		 int posX = 0;
		 int posY = 0;
		  posX = x + ui.offsetX;
		  posY = y + ui.offsetY;
		  if(title!=null)
			  posY=y + ui.offsetY+smallFont.getHeight()+5;
		g.setClip(posX, y + ui.offsetY, width ,height);
		// 画背景
		if(title!=null)
			g.drawString(title+":", x + ui.offsetX, y + ui.offsetY, 0);
//		g.setColor(bgTricolorR,bgTricolorG,bgTricolorB);
		g.fillRect(posX, posY, width, height);
		// 画前景
//		g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
		g.setFont(smallFont);
		// 画边框

		int length = itemSum;
		for (int i = startItem; i < length; i++) {
			g.drawString(line.elementAt(i).toString(), posX+2, posY +2+ smallFont.getHeight() * (i-startItem),Graphics.LEFT | Graphics.TOP);
		}
		// 画scrollBar
		if(showScrollBar){
//			g.drawImage(UIManager.scroll_btn_right, posX+width-1, 
//					posY+(((height-UIManager.scroll_btn_right.getHeight())/(itemSum-height/smallFont.getHeight()))*startItem),Graphics.RIGHT|Graphics.TOP);
		}
		g.setClip(clip[0],clip[1],clip[2],clip[3]);
		//if focus
		if(focus){
//			ImageDiv.drawJiuGong(g, UIManager.light, x + ui.offsetX - 8, y + ui.offsetY - 8, width+16,height+16);
		}
	}
	public int startItem = 0;

	//private int fireCount=0;

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (!ui.selector.inComboItem) {
			ui.selector.inComboItem = true;
		} else {
			if (keyCode == Key.LEFTKEY(keyCode) || keyCode == Key.UPKEY(keyCode)) {
				if (startItem > 0) {
					startItem--;
				}
			} else if (keyCode == Key.RIGHTKEY(keyCode) || keyCode == Key.DOWNKEY(keyCode)) {
				if (startItem < itemSum - height / smallFont.getHeight()) {
					startItem++;
				}
			} else if (keyCode == Key.FIREKEY(keyCode)) {
				/*fireCount = (fireCount + 4) % 3;
				if (fireCount == 0) {

				} else if (fireCount == 1) {
					ui.selector.inComboItem = false;
				} else if (fireCount == 2) {
					ui.selector.inComboItem = false;
				}*/
				ui.selector.inComboItem = false;
			}
		}
	}

	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode); 
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		height+=smallFont.getHeight()+5;
	}
}
