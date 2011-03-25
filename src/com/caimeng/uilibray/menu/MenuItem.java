package com.caimeng.uilibray.menu;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.event.ItemStateListener;


public class MenuItem extends AbstructButton {

	// private Vector item = new Vector();// 0 menuItem 1 radio 2 check 3 menu

	// private Vector style = new Vector();// 1表示拥有下级菜单

	// private Vector open = new Vector();// 1表示打开

	public int root = 0;// 0表示从左菜单衍生
    
	public int labelWidth;
	public MenuItem(String label,int root) {
		super(0, CMForm.frm_Height-5, 75, 21);
		this.text = label;
		this.root=root;
		labelWidth=smallFont.stringWidth(label);
	}
    
	public void keyPressed(int keyCode){
		//super.keyPressed(keyCode);
		itemStateChanged(keyCode);
	}
	
	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		itemStateChanged(x,y);
//		super.pointerPressed(x, y);
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		
		super.pointerReleased(x, y);
	}

	public void addItemListener(ItemStateListener e) {
		listenerList.add(e);
	}
	public void itemStateChanged(int keyCode){
		ItemStateListener item = this.listenerList.getItemStateListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			item.itemStateChanged(e);
		}
	}
	public void itemStateChanged(int x,int y){
		ItemStateListener item = this.listenerList.getItemStateListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this, x,y);
			item.itemStateChanged(e);
		}
	}
	
	public void paint(Graphics g) {
		
		g.setFont(smallFont);
//		g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
		int offx = 0;
		if (level != 0) {
			if (root == 0) {
				offx = level * width;
			} else if (root == 1){					
				offx = -level * width;				
			}
		}
		if (root == 1){	
			x = (CMForm.frm_Width-Menu.MaxWidth-13)+Menu.MaxWidth/2;
		}else{
			x=(Menu.MaxWidth+25)>>1;
		}
		
		int str_offy = 10 - smallFont.getHeight() / 2;		
		//g.drawImage(UIManager.menu_back, x+ offx, y ,  Graphics.LEFT | Graphics.TOP);
		g.drawString(getLabel(), x, y  + str_offy-12, Graphics.HCENTER| Graphics.TOP);
		
		//System.out.print("root"+root);
		//System.out.print("offx"+offx);
		//System.out.print("X"+x);
		//System.out.print("level"+level);
	}
}
