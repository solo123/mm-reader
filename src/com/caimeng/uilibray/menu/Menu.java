package com.caimeng.uilibray.menu;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;

//import container.QFrame;

public class Menu extends AbstructButton {

	public static int LEFT_ROOT_MENU = 0;

	public static int RIGHT_ROOT_MENU = 1;
	
	public boolean mainMenu;
	
	public static int MaxWidth;

	public Menu(String label, int root) {
		super(0, CMForm.frm_Height,114,20);
		this.text = label;
		this.root = root;
		if (root == LEFT_ROOT_MENU) {
			x = 0;
		} else if (root == RIGHT_ROOT_MENU) {
			x = CMForm.frm_Width  - width;
		}
	}

	public void setRoot(boolean isRoot) {
		isRoot = true;
	}
	public void removeMenuItem(){
		if(item!=null){
			
			item.removeAllElements();
			this.mainMenu=false;
		}
	}

	public Vector item = new Vector();// 0 menuItem 1 radio 2 check 3 menu

	private Vector style = new Vector();// 1表示拥有下级菜单

	private int root = 0;// 0表示从左菜单衍生

	private int selectedIndex = 0;

	private int sum = 0;

	public int someMenuOpen = -1;

	public Menu fatherMenu = null;

	public void addMenuItem(MenuItem menuitem) {
		item.addElement(menuitem);
		style.addElement("0");
		sum = item.size();
		//this.height+=20;
		//selectedIndex = sum;
		if(menuitem.labelWidth>MaxWidth)
		MaxWidth=menuitem.labelWidth;
		setLocation(menuitem, 0);
		
	}

	/*public void addRadionItem(QRadioButtonMenuItem menuitem) {
		item.addElement(menuitem);
		style.addElement("1");
		sum = item.size();
		//selectedIndex = sum;
		setLocation(menuitem, 1);
	}*/

	/*public void addCheckBoxItem(QCheckBoxMenuItem menuitem) {
		item.addElement(menuitem);
		style.addElement("2");
		sum = item.size();
		//selectedIndex = sum;
		setLocation(menuitem, 2);
	}*/
	
	public void closeMenu()
	{
		someMenuOpen=-1;
	}
	

	public void addMenu(Menu menu) {
		item.addElement(menu);
		menu.fatherMenu = this;
		style.addElement("3");
		sum = item.size();
		//selectedIndex = sum;
		setLocation(menu, 3);
	}

	public void setLocation(AbstructButton menuItem, int menuStyle) {
		// set y
		for (int i = 0; i < sum; i++) {
			((AbstructButton) item.elementAt(i)).y = y - (sum - i + 1) * 20;
			if (style.elementAt(i).equals("3")) {
				Menu menu = ((Menu) item.elementAt(i));
				menuItemaAdd(menu);
			}
		}

		// set x
		// 如果是menu 那么所有次级菜单level++
		if (menuStyle == 3) {
			Menu menu = ((Menu) menuItem);
			menu.level = level + 1;// 先设定循环前最底部menu的level
			menuLevelAdd(menu);
		}
	}

	// 需要有优化一下
	public void menuItemaAdd(Menu menu) {
		int sum = menu.item.size();
		for (int i = 0; i < sum; i++) {
			AbstructButton item = (AbstructButton) menu.item.elementAt(i);
			item.y = menu.y - (sum - i - 1) * 20;
			if (menu.style.elementAt(i).equals("3")) {
				menuItemaAdd((Menu) menu.item.elementAt(i));
			}
		}
	}

	public void menuLevelAdd(Menu menu) {
		int sum = menu.item.size();
		for (int i = 0; i < sum; i++) {
			AbstructButton item = (AbstructButton) menu.item.elementAt(i);
			item.level++;
			item.y = menu.y - (sum - i + 1) * 20;
			if (menu.style.elementAt(i).equals("3")) {
				menuLevelAdd((Menu) menu.item.elementAt(i));
			}
		}
	}

	public void paint(Graphics g) {
//		g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
		g.setFont(smallFont);
		int str_offy = 10 - smallFont.getHeight() / 2;
		g.setClip(0, 0, CMForm.frm_Width, CMForm.frm_Height);
		if ((selectedIndex >= 0) && (selectedIndex < sum)) {
			//if(someMenuOpen>0)
			if (root == 0)
				ImageDiv.drawJiuGong(g, UIManager.menu_bg, 1,CMForm.frm_Height - UIManager.top_bg.getHeight() - UIManager.bottom_bg.getHeight() - sum * height+3,MaxWidth+25, 25 + sum * height);
			else if (root == 1)
				ImageDiv.drawJiuGong(g, UIManager.menu_bg,(CMForm.frm_Width-MaxWidth-25) - 1,CMForm.frm_Height - UIManager.top_bg.getHeight() - UIManager.bottom_bg.getHeight() - sum * height+3,MaxWidth+25, 25 + sum * height);
		}		
		//		 画选择框
		if ((selectedIndex >= 0) && (selectedIndex < sum) && (someMenuOpen == -1)) {
			int offx = 0;
			if (level != 0) {
				if (root == 0) {
					offx = level * width;
				} else if (root == 1) {
					offx = -level * width;
				}
			}
			if (level == 0) {
				if(root==1){
					
					ImageDiv.drawJiuGong(g, UIManager.menu_bright,(CMForm.frm_Width-MaxWidth-17) , y  - (sum + 1 - selectedIndex)* height-16, MaxWidth+11, height+1);
				}else{
					ImageDiv.drawJiuGong(g, UIManager.menu_bright,x+5 , y  - (sum + 1 - selectedIndex)* height-smallFont.getHeight()+7, MaxWidth+11, height);
				}
			} else {
				if(root==0){
					
					ImageDiv.drawJiuGong(g, UIManager.menu_bright,x+offx+5 , y  - (sum + 1 - selectedIndex)* height-smallFont.getHeight()+7, MaxWidth+11, height);
				}else{
					ImageDiv.drawJiuGong(g, UIManager.menu_bright,(CMForm.frm_Width-MaxWidth-17)+offx , y  - (sum + 1 - selectedIndex)* height-16, MaxWidth+11, height+1);
				}
			}			
		}
		// 画这个Menu拥有的Item		
		for (int i = 0; i < sum; i++) {
			 if (style.elementAt(i).toString().equals("0")) {
					
					MenuItem temp = (MenuItem) item.elementAt(i);
//					if(selectedIndex==i){
//						g.setColor(255, 255, 255);
//					}else{
						
						g.setColor(ColorAttribute.menu_text);
//					}
					temp.paint(g);
					
			} else if (style.elementAt(i).toString().equals("1")) {
				/*QRadioButtonMenuItem temp = (QRadioButtonMenuItem) item
						.elementAt(i);
				temp.paint(g);*/
			
			} else if (style.elementAt(i).toString().equals("2")) {
				/*QCheckBoxMenuItem temp = (QCheckBoxMenuItem) item.elementAt(i);
				temp.paint(g);*/
			
			} else if (style.elementAt(i).toString().equals("3")) {
				//g.setClip(0, 0, ToolBar.xWForm.frm_Width, ToolBar.xWForm.frm_Height);

				// 画自身
				
				int offx = 0;
				if (level != 0) {
					if (root == 0) {
						offx = level * width;
					} else if (root == 1) {
						offx = -level * width;
					}
				}
//				if(selectedIndex==i){
//					g.setColor(255, 255, 255);
//				}else{
					g.setColor(ColorAttribute.menu_text);
//				}
				//g.setColor(255,255,255);
				Menu temp = (Menu) item.elementAt(i);
				//g.drawImage(UIManager.menu_back, temp.x+ offx, temp.y ,  Graphics.LEFT | Graphics.TOP);
				g.drawString(temp.getLabel(), temp.x + offx + 2, temp.y + str_offy, Graphics.LEFT | Graphics.TOP);
				
				// 画箭头
				if(temp.sum!=0){
					if (root == 0) {
						g.drawImage(UIManager.review_list_triangle_right, temp.x + offx + width - 7,temp.y  + 5, Graphics.LEFT | Graphics.TOP);
					} else {
						g.drawImage(UIManager.review_list_triangle_left, temp.x + offx - 1, temp.y  + 5,Graphics.LEFT | Graphics.TOP);
					}
	
					if (someMenuOpen == i) {
						temp.level = level + 1;
						temp.root = root;
						temp.paint(g);
					}
				}
			}
		}

		
		
	}

	public void keyPressed(int keyCode) {
		if (someMenuOpen == -1&&!mainMenu) {
			if (keyCode == Key.UPKEY(keyCode)) {
				selectedIndex = (selectedIndex + sum - 1) % sum;
			} else if (keyCode == Key.DOWNKEY(keyCode)) {
				selectedIndex = (selectedIndex + sum + 1) % sum;
			} else if (keyCode == Key.LEFTKEY(keyCode)) {
				if ((selectedIndex>=0) && (selectedIndex<sum)
						&& (root == Menu.RIGHT_ROOT_MENU)
						&& style.elementAt(selectedIndex).equals("3")
						&& (((Menu)item.elementAt(selectedIndex)).sum!=0)) {
					someMenuOpen = selectedIndex;
				} else if (root == Menu.LEFT_ROOT_MENU&&sum>0) {
					if (fatherMenu != null) {
						fatherMenu.someMenuOpen = -1;
					}
				}
			} else if (keyCode == Key.RIGHTKEY(keyCode)) {
				if ((selectedIndex>=0) && (selectedIndex<sum)
						&& (root == Menu.LEFT_ROOT_MENU)
						&& style.elementAt(selectedIndex).equals("3")
						&& (((Menu)item.elementAt(selectedIndex)).sum!=0)) {
					someMenuOpen = selectedIndex;
				} else if (root == Menu.RIGHT_ROOT_MENU&&sum>0) {
					if (fatherMenu != null) {
						fatherMenu.someMenuOpen = -1;
					}
				}
			} else if (keyCode == Key.FIREKEY(keyCode)) {
				if(style.elementAt(selectedIndex).equals("3")){
					if(((Menu)item.elementAt(selectedIndex)).sum!=0){
						super.keyPressed(keyCode);	
					}
					
				}else{
					CMForm.tempstate=1;
					((BaseControl) item.elementAt(selectedIndex))
					.keyPressed(keyCode); //消息事件的传递
					//System.out.print("menu ok");
					
				}
			} 
		} else {
			if(!mainMenu)
			((Menu) item.elementAt(selectedIndex)).keyPressed(keyCode);
		}
		if(keyCode==Key.LEFT_SOFT_KEY(keyCode))
		{
			super.keyPressed(keyCode);	
			//System.out.print("left");
		}else if(keyCode==Key.RIGHT_SOFT_KEY(keyCode))
		{
			super.keyPressed(keyCode);	
			//System.out.print("right");
		}  
		
	}
	/**
	 * 触屏时，返回被选中item index
	 * @param x
	 * @param y
	 * @return
	 */
	private int getSelectIndex(int x,int y){
		int offx = 0;
		if (level != 0) {
			if (root == 0) {
				offx = level * width;
			} else if (root == 1) {
				offx = -level * width;
			}
		}
		for(int i=0;i<sum;i++){
			int y1=this.y  - (sum + 1 - i)* height-smallFont.getHeight()+7;
			if(x>=this.x+offx+5 && x<=this.x+offx+5+MaxWidth+11 &&
			y>=y1 && y<=y1+height){
				return i;
			}
		}
		return 0;
	}
	public void keyReleased(int keyCode) {
		if (someMenuOpen == -1) {
			if (keyCode == Key.FIREKEY(keyCode)) {
				if(style.elementAt(selectedIndex).equals("3")){
					if(((Menu)item.elementAt(selectedIndex)).sum!=0){
						super.keyReleased(keyCode);	
					}
				}else{
					/*((AbstructButton) item.elementAt(selectedIndex))
					.keyReleased(keyCode);
					System.out.print("menu ok");*/
				}
			}
		} else {
			((Menu) item.elementAt(selectedIndex)).keyReleased(keyCode);
		}
	}

	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		selectedIndex=getSelectIndex(x, y);
		if (item.size()>0) {
			((BaseControl) item.elementAt(selectedIndex)).pointerPressed(x,y);
		}
		super.pointerPressed(x, y);
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}
	
}
