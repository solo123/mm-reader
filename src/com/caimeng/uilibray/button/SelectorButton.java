package com.caimeng.uilibray.button;


import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.DisplaySetting;
import com.caimeng.uilibray.utils.ImageDiv;

public class SelectorButton extends AbstructButton
{

	public SelectorButton()
	{
		super();
	}
	public SelectorButton(int x,int y,int width,int height,String label)
	{
		super(x,y,width,height);
		this.text=label;
	}

	public void setLabel(String label)
	{
		this.text=label;
		int charsWidth =  DisplaySetting.TEXT_FONT.stringWidth(label);
		if (charsWidth > width) {
			width = charsWidth;
		}
		height = DisplaySetting.TEXT_FONT.getHeight();
	}

	public void paint(Graphics g)
	{
		//if(DisplaySetting.TEXT_FONT.stringWidth(label)>30)
		g.setClip(0, 0, ui.xmlForm.getWidth(), ui.xmlForm.getHeight());
		if(width<80) {
			width =  DisplaySetting.TEXT_FONT.stringWidth(text);
		} else {
			width=80;
		}
		/*if (charsWidth > width) {
			width = charsWidth;
		}*/		
		if (focus) {
			if (keyPressed) {
				ImageDiv.drawJiuGong(g, UIManager.btn_1, x
						+ ui.offsetX, y + ui.offsetY, width, height);
			} else {
				ImageDiv.drawJiuGong(g, UIManager.btn_1, x + ui.offsetX, y
						+ ui.offsetY, width, height);
			}
		} else {
			ImageDiv.drawJiuGong(g, UIManager.btn_2, x + ui.offsetX, y + ui.offsetY, width, height);
		}
		if (text != null) {
			g.setClip(x + ui.offsetX, y+ ui.offsetY, width,height);
			g.setFont(smallFont);
			g.drawString(text, x+ ui.offsetX+5,y + (height - smallFontHeight) / 2 + ui.offsetY, 0);
		}
		g.setClip(0, 0, ui.xmlForm.getWidth(), ui.xmlForm.getHeight());
	}

	public void keyPressed(int keyCode) {
		if(!ui.selector.button){
			ui.selector.button=true;
		}
		if (keyCode ==Key.FIREKEY(keyCode)) {
			super.keyPressed(keyCode);
			if(ui.selector.button)
			{
				actionPerformed(keyCode);
			}
		}
		//super.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		if (keyCode ==Key.FIREKEY(keyCode)) {
			super.keyReleased(keyCode);	
			ui.selector.button=false;
		}
	}

}
