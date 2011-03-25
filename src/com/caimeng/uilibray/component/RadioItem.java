package com.caimeng.uilibray.component;



import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;



public class RadioItem extends AbstructButton{

	public boolean choosed;

	public boolean lock;
	
	private int groupID;
	
	private String ID;
	
	public RadioItem(){
		super();
	}
	
	public RadioItem(int x, int y) {
		super(x, y, 14, 14);
	}
	
	public void setLabel(String label){
		this.text=label;
		int charsWidth=smallFont.stringWidth(label);
		if(charsWidth>(width+14)){
			width=charsWidth+14;
		}
		height=smallFont.getHeight();
	}
	
	public void paint(Graphics g){
		int clipx = g.getClipX();
		int clipy = g.getClipY();
		int clipwidth = g.getClipWidth();
		int clipheight = g.getClipHeight();
		//单选框
		if(focus){
			if (choosed) {

				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX,y + ui.offsetY + 2,  UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*2, 0);
			} else {

				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX,y + ui.offsetY + 2,  UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*3, 0);
			}
		}else{
			if(choosed){

				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX,y + ui.offsetY + 2,  UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*2,  0);
			}else{

				ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX,y + ui.offsetY + 2,  UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*3, 0);
			}
		}
		//label
//		g.setColor(fgTricolorR,fgTricolorG,fgTricolorB);
		g.setClip(clipx, clipy, clipwidth, clipheight);
		g.setColor(0, 0, 0);
		g.setFont(smallFont);
		g.drawString(text, x + ui.offsetX +UIManager.select_block.getWidth()/4, y+ui.offsetY + 2, Graphics.LEFT|Graphics.TOP);
//		System.out.println(x+ui.offsetX+14);
//		System.out.println(y+ui.offsetY + 7+smallFont.getHeight()/2);
//		System.out.println("text==="+text);
		
	}
	
	public int getLabelWidth(){
		int w=smallFont.stringWidth(text);
		return w;
	}

	public void keyPressed(int keyCode){
		if(this.isPass()){
			return;
		}
		super.keyPressed(keyCode);
		if(keyCode==Key.FIREKEY(keyCode))
		{
			ui.selector.checkItem=!ui.selector.checkItem;
			if(ui.selector.checkItem)
			{
				choosed=!choosed;
			}
			ui.selector.checkItem=false;
		}
	}
	
	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

}
