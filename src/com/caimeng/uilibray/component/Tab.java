package com.caimeng.uilibray.component;


import java.util.Vector;

import javax.microedition.lcdui.Graphics;


import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.control.UIControl;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.ImageDiv;




public class Tab extends AbstructButton
{

	private Vector tips=new Vector();
	
	//private Vector names;
	
	private int sum = 0;
	
	private int selectedIndex = 0;
	
	//private int maxIndex;
	
	//private int width;
	
	//private int height;
	
	private int boxWidth=50;
	
	private int boxHeight=28;
	
	public int tabStyle;//0纯字符
	
	public Tab(int x,int y,int width,int height)
	{
		super.width=width;
		super.height=height;
		super.x=x;
		super.y=y;
		this.style=this.isTab;
	}
			
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
		
	public void setSelectedIndex(int index){
		this.selectedIndex=index;
	}
	public void addItem(String s)
	{
		tips.addElement(s);
		this.sum=tips.size();
	}
	
	public void setData(Vector v)
	{
		this.tips=v;
		this.sum=v.size();
	}
	
	public void paint(Graphics g) {
		 
		g.setClip(0, 0, width, height);
		g.setFont(smallHeavryFont);
		g.setColor(0);
		if(tabStyle==0)
		{
			g.setColor(255,255,255);
			boxWidth=width/sum;
			ImageDiv.fillScreen(g, UIManager.tab_bg,  x+ui.offsetX, y+ui.offsetY,width, height);
			g.setClip(0, 0, width, ui.xmlForm.frm_Height);
			int w=UIManager.tab_ico.getWidth()/3;
			for(int i=0;i<sum;i++)
			{
				if(selectedIndex==i){
					g.drawImage(UIManager.tab_selected, boxWidth*i+boxWidth/2, y+ui.offsetY, Graphics.TOP|Graphics.HCENTER);
				}
				ImageBuilder.drawSmallImage(g, UIManager.tab_ico, boxWidth*i+boxWidth/2-w/2, y+ui.offsetY+5,w, UIManager.tab_ico.getHeight(),w*i,0);
				g.setClip(0, 0, width, ui.xmlForm.frm_Height);
				g.drawString(tips.elementAt(i).toString(), boxWidth*i+boxWidth/2,y+ui.offsetY+UIManager.tab_ico.getHeight()+5, Graphics.TOP|Graphics.HCENTER);
			}				
		}/*else if(tabStyle==1)
		{
			g.setClip(0,0, ui.cmForm.getWidth(), ui.cmForm.getHeight());
			ImageDiv.fillScreen(g, UIManager.tab_bg,  x+ui.offsetX,  y+ui.offsetY, width, height);
			ImageDiv.fillScreen(g, UIManager.tab_select_bg, x+ui.offsetX+2, y+ui.offsetY, UIManager.examine_title_ico.getWidth()+smallFont.stringWidth(tips.elementAt(selectedIndex).toString()), height);
			ImageBuilder.drawSmallImage(g, UIManager.examine_title_ico,  x+ui.offsetX+5,  y+ui.offsetY+5, UIManager.examine_title_ico.getWidth()/2, UIManager.examine_title_ico.getHeight(), focus?0:UIManager.examine_title_ico.getWidth()/2, 0);
			g.setClip(0,0, ui.cmForm.getWidth(), ui.cmForm.getHeight());
			g.drawImage(UIManager.tab_brew, x+ui.offsetX+2+UIManager.examine_title_ico.getWidth()+smallFont.stringWidth(tips.elementAt(selectedIndex).toString()), y+ui.offsetY, Graphics.LEFT|Graphics.TOP);
			ImageDiv.fillScreen(g,  UIManager.tab_bottom, x, y+ui.offsetY+height-UIManager.tab_bottom.getHeight(), width, UIManager.tab_bottom.getHeight());
			if(focus){
				g.setColor(255,255,255);
			}else{
				g.setColor(196, 196, 196);
			}
			g.drawString(tips.elementAt(selectedIndex).toString(),x+ui.offsetX+15+UIManager.examine_title_ico.getWidth()/2, y+ui.offsetY+8, 0);		
			g.setClip(0,0, ui.cmForm.getWidth(), ui.cmForm.getHeight());
		}else if(tabStyle==2)
		{
			
		}*/
	}
	
	
	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		selectedIndex=getPointSelectIndex(x,y);
		UIControl.getInstance().tabeChangeState(selectedIndex);
		super.pointerPressed(x, y);
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}
	private int getPointSelectIndex(int x,int y){
		for(int i=0;i<sum;i++){
			int x1=boxWidth*i;
			if(x>=x1 && x<=x1+boxWidth &&
					y>=y+ui.offsetY&& y<=y+ui.offsetY+UIManager.tab_selected.getHeight()){
				return i;
			}
		}
		return 0;
	}

	public void keyPressed(int keyCode) {
		if(this.isPass()){
			return;
		}
			if(keyCode == Key.UPKEY(keyCode)|keyCode == Key.DOWNKEY(keyCode))
			{
		       if(ui.selector.Tab){
		    	
		    	 ui.selector.Tab=false;	   
		    	
		    	
		       }else{	    	   
		    	   ui.selector.Tab=true;
		       }
	
			}else	if(keyCode==Key.LEFTKEY(keyCode))
			{
				if(selectedIndex>0) {
					selectedIndex--;
				}else{
					selectedIndex=sum-1;
				}
				UIControl.getInstance().tabeChangeState(selectedIndex);
				super.keyPressed(keyCode);
				//((AbstructButton)instance).keyPressed(keyCode);
			}else if(keyCode==Key.RIGHTKEY(keyCode)) 
			{
				if(selectedIndex<(sum-1)) {
					selectedIndex++;
				}else{
					selectedIndex=0;
				}
				UIControl.getInstance().tabeChangeState(selectedIndex);
				super.keyPressed(keyCode);
				//((AbstructButton)instance).keyPressed(keyCode);
			}
		
		
		
		
	}
	
	public void keyReleased(int keyCode) {
		
		/*if(keyCode==Key.LEFTKEY(keyCode)|keyCode==Key.RIGHT)
		{
			super.keyReleased(keyCode);
		}*/
	}
	
}
