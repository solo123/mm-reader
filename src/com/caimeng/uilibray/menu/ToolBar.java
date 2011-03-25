package com.caimeng.uilibray.menu;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;

public class ToolBar extends BaseControl {

	public  CMForm cmForm = null;

	public boolean leftMenuOpen = false;

	public boolean rightMenuOpen = false;

	public boolean flash = false;
	
	public boolean hasTitle;
	
	public boolean alarm = false;
	
	public String title ="";
//	private int w=smallFont.stringWidth("标")*5;
	//public int menuBarHeight = 21;

	private int flashTime=2;
	
	public boolean showOnline=true;


	/*private int[] flashIconStyle = { 0, 0, 0};// 一共3个快捷键
													// ，分别代表不同的消息，值为1表明相应的图标闪烁
*/
	private int messageNum;
	public ToolBar(CMForm xWForm) {
		super(0, xWForm.frm_Height,114, 21);
		title=xWForm.title;
		this.cmForm = xWForm;
		this.ui=cmForm.getUIManager();
		// TODO 自动生成构造函数存根
		
	}
	


	public void setLeftMenuLabel(String label){
		leftMenu.setLabel(label);
	}
	
	public String getLeftMenuLabel(){
		return leftMenu.getLabel();
	}
	public void setrightMenuLabel(String label){
		rightMenu.setLabel(label);
	}
	public String getRightMenuLabel(){
		return rightMenu.getLabel();
	}
	public void paint(Graphics g) {
		title=cmForm.title;
		g.setFont(smallFont);		
				
		g.setClip(0, 0, cmForm.frm_Width, cmForm.frm_Height);
		
		ImageDiv.fillScreen(g, UIManager.bottom_bg, 0, cmForm.frm_Height-UIManager.bottom_bg.getHeight(), cmForm.frm_Width, cmForm.frm_Height);
//		g.setColor(255,204,0);
//		g.fillRect(0, cmForm.frm_Height-20, cmForm.frm_Width, cmForm.frm_Height);
		
		if(hasTitle)
		{
			ImageDiv.fillScreen(g, UIManager.top_bg, 0, 0, cmForm.frm_Width, cmForm.frm_Height);
//			g.setColor(255,204,0);
//			g.fillRect(0, 0, cmForm.frm_Width, 20);
			 g.setClip(0, 0, cmForm.frm_Width, cmForm.frm_Height);	
			 g.setColor(255,255,255);
			 g.drawString(title, cmForm.frm_Width>>1, 0, Graphics.TOP|Graphics.HCENTER);
		}
		 g.setColor(255,255,255);
		 g.setClip(0, 0, cmForm.frm_Width, cmForm.frm_Height);									
		if (leftMenu != null) {
			g.drawString(leftMenu.getLabel(),8, cmForm.frm_Height-3,	Graphics.LEFT | Graphics.BOTTOM);
//			g.drawImage(UIManager.bottom_p, smallFont.stringWidth(leftMenu.getLabel())+16,  cmForm.frm_Height-UIManager.bottom_p.getHeight(), Graphics.LEFT | Graphics.TOP);
		}
		if (rightMenu != null) {
			g.drawString(rightMenu.getLabel(), cmForm.frm_Width - 8,cmForm.frm_Height-3, Graphics.RIGHT | Graphics.BOTTOM);
//			g.drawImage(UIManager.bottom_p,cmForm.frm_Width - 16- smallFont.stringWidth(rightMenu.getLabel()), cmForm.frm_Height-UIManager.bottom_p.getHeight(), Graphics.RIGHT | Graphics.TOP);
		}
		if ((leftMenu != null) && leftMenuOpen) {
			leftMenu.paint(g);
		} else if ((rightMenu != null) && rightMenuOpen) {
			rightMenu.paint(g);
		}

		/*g.setColor(0);
		if (flash) {
			if (flashTime % 3 == 0) {
				g.drawRect(xWForm.frm_Width / 2 - 20 * 4 / 2,
						xWForm.frm_Height - 1 - 30, 20, 28);
				g.drawRect(xWForm.frm_Width / 2 - 20 * 4 / 2 + 20,
						xWForm.frm_Height - 1 - 30, 20, 28);
				g.drawRect(xWForm.frm_Width / 2 - 20 * 4 / 2 + 20 * 2,
						xWForm.frm_Height - 1 - 30, 20, 28);
				g.drawRect(xWForm.frm_Width / 2 - 20 * 4 / 2 + 20 * 3,
						xWForm.frm_Height - 1 - 30, 20, 28);
			}
			if (++flashTime > 200) {
				flashTime = 0;
			}
		}*/
	/*	if (flash) {
			if (flashTime % 2 == 0) {
				g.drawImage(UIManager.top_ico_1,(cmForm.frm_Width-UIManager.top_ico_1.getWidth())/2, cmForm.frm_Height-5,  Graphics.LEFT | Graphics.BOTTOM);
			}
			if (alarm) {
			if(flashTime<14)
				flashTime++;
			else ui.cmForm.stopTimer();
			}
		}*/
		if (flash) {
		g.setColor(0,0,255);
		int h=cmForm.frm_Height-UIManager.bottom_bg.getHeight();
		g.fillRect(0, h,cmForm.frm_Width-smallFont.stringWidth("退出")-10 , UIManager.bottom_bg.getHeight());
        g.setColor(0);
        g.fillRect(0, h, xvalue, UIManager.bottom_bg.getHeight());
		g.setColor(255,255,255);
		g.drawString("正在加载...", 8, cmForm.frm_Height-3,	Graphics.LEFT | Graphics.BOTTOM);
		}
		}				
	private int xvalue;
	public void updateProgress(int value){
		flash=true;
		xvalue+=10;
		if(value==100){
			flash=false;
		}
	}
	public Menu leftMenu = null;

	public void setLeftMenu(Menu menu) {
		leftMenu = menu;
		leftMenu.ui=this.ui;
		leftMenu.setRoot(true);
	}

	public Menu rightMenu = null;

	public boolean menuOpen = false;

	public void setRightMenu(Menu menu) {
		rightMenu = menu;
		rightMenu.ui=this.ui;
		rightMenu.setRoot(true);
	}

	/*public void startFlash(int index) {
		flashIconStyle[index] = 1;
	}

	public void stopFlash(int index) {
		flashIconStyle[index] = 0;
	}*/

	public void setMessageNum(int num)
	{
		messageNum=num;
		flashTime=2;
		//ui.xWForm.startTimer();
	}
	
	public void keyPressed(int keyCode) {
		if (keyCode == Key.LEFT_SOFT_KEY(keyCode) && leftMenu != null) {

			leftMenuOpen = !leftMenuOpen;
			rightMenuOpen = false;
		} else if (keyCode == Key.RIGHT_SOFT_KEY(keyCode) && rightMenu != null) {
		
			rightMenuOpen = !rightMenuOpen;
			leftMenuOpen = false;
		} else {
			
			if (leftMenuOpen) {
				leftMenu.keyPressed(keyCode);
			} else if (rightMenuOpen) {
				rightMenu.keyPressed(keyCode);
			}
			if (keyCode == Key.FIRE) {
			}
		}
		// 更新状态
		if (!leftMenuOpen && !rightMenuOpen) {
			menuOpen = false;
		} else {
			menuOpen = true;
		}
		if(keyCode == Key.LEFT_SOFT_KEY(keyCode)&& leftMenu != null&&leftMenu.item.size()==0)			
		{
			leftMenu.mainMenu=true;
			leftMenu.keyPressed(keyCode);
			
		}else if(keyCode == Key.RIGHT_SOFT_KEY(keyCode)&& rightMenu != null&&rightMenu.item.size()==0)
		{
			rightMenu.mainMenu=true;
			rightMenu.keyPressed(keyCode);
		}
		super.keyPressed(keyCode);
		
	}

	public void keyReleased(int keyCode) {
		
	}

	private boolean isLeftPointer(int x,int y){
		if(x>=8+this.x && x<=8+this.x+smallFont.stringWidth(getLeftMenuLabel()) 
				&& y>=cmForm.frm_Height-3-smallFont.getHeight() && y<=cmForm.frm_Height-3){
			return true;
		}
		return false;
	}
	private boolean isRightPointer(int x,int y){
		if(x>=cmForm.frm_Width-8-smallFont.stringWidth(getRightMenuLabel()) && x<=cmForm.frm_Width-8 
				&& y>=cmForm.frm_Height-3-smallFont.getHeight() && y<=cmForm.frm_Height-3){
			return true;
		}
		return false;
	}
	public boolean isMenuPointer(int x,int y){
		if(isLeftPointer(x,y) ||isRightPointer(x,y)){
			return true;
		}
		return false;
	}

	public void pointerPressed(int x, int y) {
		boolean isLeft=isLeftPointer(x,y);
		boolean isRight=isRightPointer(x,y);
		if (isLeft && leftMenu != null) {

			leftMenuOpen = !leftMenuOpen;
			rightMenuOpen = false;
		} else if (isRight && rightMenu != null) {
		
			rightMenuOpen = !rightMenuOpen;
			leftMenuOpen = false;
		} else {
			
			if (leftMenuOpen) {
				leftMenu.pointerPressed(x,y);
			} else if (rightMenuOpen) {
				rightMenu.pointerPressed(x,y);
			}
			
		}
		// 更新状态
		if (!leftMenuOpen && !rightMenuOpen) {
			menuOpen = false;
		} else {
			menuOpen = true;
		}
		if(isLeft&& leftMenu != null&&leftMenu.item.size()==0)			
		{
			leftMenu.mainMenu=true;
			leftMenu.pointerPressed(x,y);
			
		}else if(isRight&& rightMenu != null&&rightMenu.item.size()==0)
		{
			rightMenu.mainMenu=true;
			rightMenu.pointerPressed(x,y);
		}
/*		
		
		
		
		if(x>=8+this.x && x<=8+this.x+smallFont.stringWidth(getLeftMenuLabel()) 
				&& y>=cmForm.frm_Height-3-smallFont.getHeight() && y<=cmForm.frm_Height-3){
			System.out.println("左软件");
			leftMenuOpen = !leftMenuOpen;
			rightMenuOpen = false;
//			 更新状态
			if (!leftMenuOpen && !rightMenuOpen) {
				menuOpen = false;
			} else {
				menuOpen = true;
			}
			if(leftMenu != null&&leftMenu.item.size()==0)			
			{
				leftMenu.mainMenu=true;
				leftMenu.pointerPressed(x,y);
				
			}
		}*/
		super.pointerPressed(x, y);
	}



	public void pointerReleased(int x, int y) {
		super.pointerReleased(x, y);
	}
	

}
