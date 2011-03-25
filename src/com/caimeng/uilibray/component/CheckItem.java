
package com.caimeng.uilibray.component;



import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;


public class CheckItem extends AbstructButton {

	
	public CheckItem(){
		super();
	}
	
	public CheckItem(int x, int y) {
		super(x, y, 14, 14);
		choosed=false;
	}

	public boolean choosed; 
	
	private String ID;
	
	private String groupID;
	
	public boolean lock;
	
	public void setLabel(String label) {
		this.text = label;
		int charsWidth = smallFont.stringWidth(label);
		if (charsWidth > (width + 14)) {
			width = charsWidth + 14;
		}
		height = smallFont.getHeight();
	}

	public void paint(Graphics g) {
		// 复选框
		if(focus){			
				
			if(choosed) {
				ImageBuilder.drawSmallImage(g,UIManager.select_block,x + ui.offsetX, y + ui.offsetY,UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(),0, 0);				
			} else {
				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX, y + ui.offsetY, UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4, 0);
			}
			g.setColor(62, 155, 255);
		}else{			
			if(choosed) {
				ImageBuilder.drawSmallImage(g,UIManager.select_block,x + ui.offsetX, y + ui.offsetY,UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(),0, 0);				
			} else {
				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX, y + ui.offsetY, UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4, 0);
			}
			g.setColor(52, 52, 52);
		}
		
		// label
		g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
		g.setFont(smallFont);
		g.drawString(text, x + ui.offsetX + (UIManager.select_block.getWidth()>>2), y + ui.offsetY + 7 + smallFont.getHeight() / 2, Graphics.LEFT | Graphics.BOTTOM);
	}

	public void keyPressed(int keyCode){
		super.keyPressed(keyCode);
		if(keyCode==Key.FIREKEY(keyCode))
		{
			ui.selector.checkItem=!ui.selector.checkItem;
			if(ui.selector.checkItem)
			{
				choosed=!choosed;
			}
			ui.selector.checkItem=false;
		}/*else if(keyCode==Key.UP|keyCode==Key.DOWN)
		{
			
		}*/
	}
	
	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

}
