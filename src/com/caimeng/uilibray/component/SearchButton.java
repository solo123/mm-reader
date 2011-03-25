package com.caimeng.uilibray.component;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.common.Input;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.control.UIControl;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;



public class SearchButton extends Input {
	private String ID;
//	private String title;
	private int focustate=0;
	private int codetype=0;//0-ALLCODE,1-code128,2-code39,3-EAN13
	private static String buttoname="搜索";
	private int btn_width=0;
	public SearchButton(int x,int y,int width,int height){
		super(x, y, width, height);
		this.setStyle(BaseControl.isBarCode);
	
		/*if(width>ui.xWForm.frm_Width-30){
			width=ui.xWForm.frm_Width-30;
		}*/
		btn_width=smallFont.stringWidth(buttoname)+5;
		super.width=super.width+btn_width;
	}
	
	public SearchButton() {
		// TODO 自动生成构造函数存根
	}

	public void paint(Graphics g){
		int posX=x+ui.offsetX;
		int posY=y+ui.offsetY;
		g.setFont(smallFont);
		
	
		
		if (focus) {
			if(focustate==0){
				g.setColor(ColorAttribute.btn_selected);
				g.fillRect(posX,  posY, width, UIManager.btn_bg.getHeight());
				g.setColor(ColorAttribute.textbox_rect);
				g.drawRect(posX, posY, width+1, UIManager.btn_bg.getHeight()+1);
				g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
				ImageDiv.fillScreen(g, UIManager.btn_bg, posX+width-btn_width,posY+1,posX+width, UIManager.btn_bg.getHeight());
				g.setColor(ColorAttribute.screen_text);
				g.drawString(buttoname, posX+width-btn_width+5, posY+3, Graphics.TOP|Graphics.LEFT);
				// 画label 缩减5像素
				g.setClip(posX+1,  posY+1, width-8, smallHeavryFont.getHeight()+5-1);
				g.setColor(ColorAttribute.textbox_rect);
				g.drawString(text, posX + 6, posY + 2, Graphics.LEFT|Graphics.TOP);

			}else{
				g.setColor(ColorAttribute.textbox_rect_bg);
				g.fillRect(posX,  posY, width, UIManager.btn_bg.getHeight());
				g.setColor(ColorAttribute.textbox_rect);
				g.drawRect(posX, posY, width+1, UIManager.btn_bg.getHeight()+1);
				g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
				ImageDiv.fillScreen(g, UIManager.btn_bg, posX+width-btn_width,posY+1,posX+width, UIManager.btn_bg.getHeight());
				g.setColor(ColorAttribute.btn_selected);
				g.drawString(buttoname, posX+width-btn_width+5, posY+3, Graphics.TOP|Graphics.LEFT);
				// 画label 缩减5像素
				g.setClip(posX+1,  posY+1, width-8, smallHeavryFont.getHeight()+5-1);
				g.setColor(ColorAttribute.textbox_rect);
				g.drawString(text, posX + 6, posY + 2, Graphics.LEFT|Graphics.TOP);

			}
		}else{
			g.setColor(ColorAttribute.textbox_rect_bg);
			g.fillRect(posX,  posY, width, UIManager.btn_bg.getHeight());
			g.setColor(ColorAttribute.textbox_rect);
			g.drawRect(posX, posY, width+1, UIManager.btn_bg.getHeight()+1);
			g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
			ImageDiv.fillScreen(g, UIManager.btn_bg, posX+width-btn_width,posY+1,posX+width, UIManager.btn_bg.getHeight());
			g.setColor(ColorAttribute.screen_text);
			g.drawString(buttoname, posX+width-btn_width+5, posY+3, Graphics.TOP|Graphics.LEFT);
			// 画label 缩减5像素
			g.setClip(posX+1,  posY+1, width-8, smallHeavryFont.getHeight()+5-1);
			g.setColor(ColorAttribute.textbox_rect);
			g.drawString(text, posX + 6, posY + 2, Graphics.LEFT|Graphics.TOP);
		}
		
		super.setCoordnate(posX+6, posY);
		g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
	}
	private int getPointState(int x,int y){
		int posX=this.x+ui.offsetX;
		int posY=this.y+ui.offsetY;
		if(x>=posX && x<=posX+width-btn_width
				&& y>=posY && y<=posY+UIManager.btn_bg.getHeight()){
			return 0;
		}else if(x>=posX+width-btn_width && x<=posX+width
					&& y>=posY && y<=posY+UIManager.btn_bg.getHeight()){
				return 1;
		}
		
		return 0;
	}
	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		focustate=getPointState(x, y);
		focus=true;
		ui.xmlForm.repaint();
		if(focustate==0){
			
			super.pointerPressed(x, y);
		}else if(focustate==1){
			UIControl.getInstance().searchForText(text);
		}
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}

	public void keyPressed(int keyCode){
		if(this.isPass()){
			return;
		}
		if(!ui.selector.barCode){
			ui.selector.barCode=true;
		}else{
//			System.out.println("...............");
			if(keyCode==Key.FIRE){
				if(focustate==0){
					
				}else if(focustate==1){
					UIControl.getInstance().searchForText(text);
				}
			}else if(keyCode==Key.DOWN| keyCode==Key.RIGHT){
				if(focustate<1)
					focustate++;
				else
					ui.selector.barCode=false;
				
			}else if(keyCode==Key.UP|keyCode==Key.LEFT){
				if(focustate>0)
					focustate--;
				else
					ui.selector.barCode=false;
				
			}
			if(focustate==0)
			super.keyPressed(keyCode);
		}
			
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
	public void setCodeType(int type){
		codetype=type;
	}
	public int getCodeType(){
		return codetype;
	}
}
