package com.caimeng.uilibray.form;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.button.ImageButton;
import com.caimeng.uilibray.button.TextButton;
import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.common.Theme;
import com.caimeng.uilibray.common.Themeable;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.event.NineGridListener;



public class NineGrid extends AbstructButton implements Themeable {
	private Vector components;

	private Vector listeners;

	private int foreground;

	private int background;

	private int buttonBgLight;

	private int buttonBgDark;

	private int buttonSelLight;

	private int buttonSelDark;

	private int border1;

	private int border2;

	private int selectedForeground;

	private int selectedBackground;

	private int titleForeground;

	private int titleBackground;

	private int rows;

	private int cols;

	private int width;

	private int height;

	private boolean stretchIcon;

	private int buttonStyle;

	private int boxWidth;

	public static  int boxHeight;
	
	public static int offsetY=0;

	public int model;
	
	public Vector itemID=new Vector();
	public Vector linkID=new Vector();
	private String ID;
	private Graphics g;
	public NineGrid(int w, int h) {
		this(1, 1, w, h);
	}
	public static int mode=0;
	public void setMode(int mode){
		this.mode=mode;
	}
	public NineGrid(int r, int c, int w, int h) {
		this.width = w;
		this.height = h;
		this.rows = r;
		this.cols = c;
		this.components = new Vector();
		this.listeners = new Vector();
		setStretchIcon(false);
		mode=0;
		setButtonStyle(1);
		applyTheme(Theme.getTheme());
		if(CMForm.frm_Width>=320){
			setBox(107,62);
		}else
		if(CMForm.frm_Height>220){
			setBox(80,80);
		}else{
			setBox(58,52);
		}
	}

	public void setID(String id){
		ID=id;
	}
	public void addPopperListener(NineGridListener pl) {
		this.listeners.addElement(pl);
	}

	public TextButton addItem(String label, String imageRes) {
		return ((TextButton) addItem(new TextButton(label, imageRes)));
	}
	
	public TextButton addString(String label, String iconString)
	{
		return ((TextButton) addItem(new TextButton(label!=null?label:"", iconString!=null?iconString:"")));
	}

	public ImageButton addItem(ImageButton newItem) {
		synchronized (this.components) {
			newItem.setParent(this);
			newItem.setStyle(getButtonStyle());
			newItem.setStretchIcon(isStretchIcon());
			this.components.addElement(newItem);
			if (getRows() * getCols() < this.components.size()) {
				//setCols(calculateCols());
				//setRows(calculateRows());
			}
		}
		setSelectedIndex(0);
		return newItem;
	}

	public int getSelectedIndex() {
		for (int i = 0; i < this.components.size(); ++i) {
			if (((ImageButton) this.components.elementAt(i)).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public ImageButton getSelected() {
		return ((ImageButton) this.components.elementAt(getSelectedIndex()));
	}

	public void setSelectedIndex(int index) {
		for (int i = 0; i < this.components.size(); ++i) {
			if (((ImageButton) this.components.elementAt(i)).isSelected()) {
				((ImageButton) this.components.elementAt(i)).setSelected(false);
				break;
			}
		}

		((ImageButton) this.components.elementAt(index)).setSelected(true);
	}

	public void removeAll() {
		this.components.removeAllElements();
	}

	public void dispose() {
		removeAll();
		this.listeners.removeAllElements();
	}

	/*private int calculateRows()
	 {
	 double ratio = Math.sqrt(this.width * this.height / this.components.size());

	 int xcount = (int)Math.ceil(this.width / ratio);
	 int ycount = (int)Math.ceil(this.height / ratio);
	 if (xcount <= 0) xcount = 1;
	 if (ycount <= 0) ycount = 1;

	 while (xcount * ycount < this.components.size())
	 if (this.width > this.height)
	 ++xcount;
	 else
	 ++ycount;

	 return ycount;
	 }*/

	/*private int calculateCols()
	 {
	 double ratio = Math.sqrt(this.width * this.height / this.components.size());

	 int xcount = (int)Math.ceil(this.width / ratio);
	 int ycount = (int)Math.ceil(this.height / ratio);
	 if (xcount <= 0) xcount = 1;
	 if (ycount <= 0) ycount = 1;

	 while (xcount * ycount < this.components.size())
	 if (this.width > this.height)
	 ++xcount;
	 else
	 ++ycount;

	 return xcount;
	 }*/

	public void keyPressed(int gameAction) {
		int selectedIndex = getSelectedIndex();
//		System.out.print("key"+gameAction);
		if (!ui.selector.nineGrid) {
			ui.selector.nineGrid = true;
		}
		int index=0;
		switch (gameAction) {
		case -1://上
			if (selectedIndex > 0) {
				if (selectedIndex >= getRows()) {
					index=selectedIndex - getRows();
					setSelectedIndex(index);
					this.paint(g);
					/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
						offsetY-=boxHeight;
						
					}*/
					return;
				}
				index=selectedIndex-1;
				setSelectedIndex(index);
				this.paint(g);
				/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
					offsetY=0;
				}*/
				return;
			}

			/*index=this.components.size() - 1;
			setSelectedIndex(index);
			this.paint(g);
			if(((ImageButton) this.components.elementAt(index)).needoffset()){
				offsetY-=boxHeight;
			}*/
			break;
		case -2://下
			if (selectedIndex < this.components.size() - 1) {
				if (selectedIndex + getRows() < this.components.size()) {
					index=selectedIndex + getRows();
					setSelectedIndex(index);
					this.paint(g);
					/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
						offsetY+=boxHeight;
					}*/
					return;
				}
				/*index=this.components.size() - 1;
				setSelectedIndex(index);
				if(((ImageButton) this.components.elementAt(index)).needoffset()){
					offsetY+=boxHeight;
				}
				return;*/
			}
			index=0;
			setSelectedIndex(index);
			this.paint(g);
			/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
				offsetY=0;
				
			}*/
			break;
		case -3://左
			if (selectedIndex > 0) {
				index=selectedIndex-1;
				setSelectedIndex(index);
				this.paint(g);
				/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
					offsetY-=boxHeight;
				}*/
				return;
			}
			index=this.components.size() - 1;
			setSelectedIndex(index);
			this.paint(g);
//			if(((ImageButton) this.components.elementAt(index)).needoffset()){
//				offsetY-=boxHeight;
//			}
			break;
		case -4://右
			if (selectedIndex < this.components.size() - 1) {
				index=selectedIndex + 1;
				setSelectedIndex(index);
				this.paint(g);
				/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
					offsetY+=boxHeight;
				}*/
				return;
			}
			index=0;
			setSelectedIndex(index);
			this.paint(g);
			/*if(((ImageButton) this.components.elementAt(index)).needoffset()){
				offsetY=0;
			}*/
			break;
		case -5:
			for (int i = 0; i < this.listeners.size(); ++i) {
				NineGridListener pl = (NineGridListener) this.listeners	.elementAt(i);
				pl.clicked(getSelected());
			}
		case 3:
		case 4:
		case 7:
		}
		
	}

	public int getForeground() {
		return this.foreground;
	}

	public void setForeground(int foreground) {
		this.foreground = foreground;

		for (int i = 0; i < this.components.size(); ++i) {
			ImageButton button = (ImageButton) this.components.elementAt(i);

			button.setSelected(button.isSelected());
		}
	}

	public int getBackground() {
		return this.background;
	}

	public void setBackground(int background) {
		this.background = background;

		for (int i = 0; i < this.components.size(); ++i) {
			ImageButton button = (ImageButton) this.components.elementAt(i);

			button.setSelected(button.isSelected());
		}
	}
	
	public int  getSumIndex(){
		return this.components.size();
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return this.cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setBox(int width, int height) {
		this.boxWidth = width;
		this.boxHeight = height;
	}
	
	public int getBoxWidth(){
		return this.boxWidth;
	}
	
	public int getBoxHeight(){
		return this.boxHeight;
	}

	private int index=0;
	public void paint(Graphics gc) {
		this.g=gc;
		/* int box_width =80;//width/3;//width /getRows();//如果是9宫格则分别设为3
		 int box_height =80;//height/3;//height/getCols();  240 320设具体数值
		 */
//		int index = 0;
		if (getSelectedIndex() < 9) {
			index = 0;

		} else {
			index = 9;
		}
//		  gc.setClip(0, 0, XWForm.frm_Width, XWForm.frm_Height);
//			gc.setColor(255, 0, 0);
//			gc.fillRect(0, 0, XWForm.frm_Width, XWForm.frm_Height);
		int dy=0;
		if(CMForm.frm_Height>240){
			dy=28;
		}else if(CMForm.frm_Height>=220){
			dy=18;
		}else{
			dy=15;
		}
//		gc.setClip(0, 0, width, height);
//	    ImageDiv.fillScreen(gc, UIManager.login, 0, 0, width, height);
		for (int y = 0; y < getCols(); ++y) {
			for (int x = 0; x < getRows(); ++x) {
				if (index < this.components.size()) {
//					ImageButton item = ((ImageButton) this.components.elementAt(index++));
//					item.setBoxWidth(boxWidth);
//					item.setBoxHeight(boxHeight);
					 
					((ImageButton) this.components.elementAt(index++)).paint(gc, x * boxWidth, y * boxHeight + dy-offsetY, boxWidth,boxHeight);
				}
			}
		}
		if (focus) {
//			uiLibrary.utils.ImageDiv.drawJiuGong(gc, uiLibrary.common.UIManager.light, 2, 20,
//					width - 4, boxHeight * 3 + 10);
		}
	}

	public int getButtonBgLight() {
		return this.buttonBgLight;
	}

	public void setButtonBgLight(int buttonBgLight) {
		this.buttonBgLight = buttonBgLight;
	}

	public int getButtonBgDark() {
		return this.buttonBgDark;
	}

	public void setButtonBgDark(int buttonBgDark) {
		this.buttonBgDark = buttonBgDark;
	}

	public int getButtonSelLight() {
		return this.buttonSelLight;
	}

	public void setButtonSelLight(int buttonSelLight) {
		this.buttonSelLight = buttonSelLight;
	}

	public int getButtonSelDark() {
		return this.buttonSelDark;
	}

	public void setButtonSelDark(int buttonSelDark) {
		this.buttonSelDark = buttonSelDark;
	}

	public int getBorder1() {
		return this.border1;
	}

	public void setBorder1(int highLight1) {
		this.border1 = highLight1;
	}

	public int getBorder2() {
		return this.border2;
	}

	public void setBorder2(int highLight2) {
		this.border2 = highLight2;
	}

	public int getTitleForeground() {
		return this.titleForeground;
	}

	public void setTitleForeground(int titleForeground) {
		this.titleForeground = titleForeground;
	}

	public int getTitleBackground() {
		return this.titleBackground;
	}

	public void setTitleBackground(int titleBackground) {
		this.titleBackground = titleBackground;
	}

	public void applyTheme(Theme t) {
		setForeground(t.getColor(1));
		setBackground(t.getColor(0));

		setButtonBgLight(t.getColor(125));
		setButtonBgDark(t.getColor(126));

		setButtonSelLight(t.getColor(127));
		setButtonSelDark(t.getColor(128));

		setBorder1(t.getColor(4));
		setBorder2(t.getColor(5));

		setTitleForeground(t.getColor(124));
		setTitleBackground(t.getColor(123));
	}

	public int getSelectedForeground() {
		return this.selectedForeground;
	}

	public void setSelectedForeground(int selectedForeground) {
		this.selectedForeground = selectedForeground;
	}

	public int getSelectedBackground() {
		return this.selectedBackground;
	}

	public void setSelectedBackground(int selectedBackground) {
		this.selectedBackground = selectedBackground;
	}

	public boolean isStretchIcon() {
		return this.stretchIcon;
	}

	public void setStretchIcon(boolean stretchIcon) {
		this.stretchIcon = stretchIcon;
	}

	public int getButtonStyle() {
		return this.buttonStyle;
	}

	public void setButtonStyle(int buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

}