package com.caimeng.uilibray.common;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.event.ActionListener;
import com.caimeng.uilibray.event.EventListenerList;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Tip;
import com.caimeng.uilibray.skin.UIManager;


public class BaseControl {

	public int x = -1;

	public int y = -1;
	
	public int pox=-1;
	public int poy=-1;

	public int width = 0;

	public int height = 0;

	public boolean keyPressed = false;

	private Tip tip = null;

	public boolean visible = true;

	public UIManager ui = null;

	public boolean focus = false;

	public int index;

	public int style = 0;

	public static int isHorizontalLine = 1;

	public  static int isLabel = 2;

	public static int isImageLabel = 3;

	public static int isMessageBox = 4;
	
	public static int isComboBox=5;
	
	public  static int isCheckBox = 6;
	
	public  static int isList=7;
	
	public static int isTextBox=8;
	
	public  static int isTab=9;
	
	public static int isRadioBox=10;
	
	public  static int isNineGrid=11;
	
	public static int isMap=12;
	
	public static int isBarCode=13;
	
	private boolean pass = false;
	

	public BaseControl() {
	}

	public BaseControl(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void paint(Graphics g) {

	}

	public void setStyle(int style) {
		this.style = style;
	}

	public void setUIManager(UIManager ui) {
		this.ui = ui;
	}

	public int getIndex() {
		this.index = ui.getList() - 1;
		return index;
	}

	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setBounds(int x, int y, int width, int height) {
		setPosition(x, y);
		setSize(width, height);
	}

	public EventListenerList listenerList = new EventListenerList();

	public void addActionListener(ActionListener e) {
		listenerList.add(e);
	}



	public boolean notBgColor = false;

	public void setNoBack() {
		notBgColor = true;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
/**
 * 键盘按下
 * @param keyCode
 */
	public void keyPressed(int keyCode) {
		keyPressed = true;
		actionPerformed(keyCode);
	}
	/**
	 * 键盘释放
	 * @param keyCode
	 */

	public void keyReleased(int keyCode) {
		keyPressed = false;
		actionPerformed(keyCode);
	}
	
	/**
	 * 触摸屏按下事件
	 */
	public void pointerPressed(int x, int y) {
		actionPerformed(x,y);
	}
	/**
	 * 触摸屏释放事件
	 */
	public void pointerReleased(int x, int y) {
//		actionPerformed(x,y);
	}
/**
 * 触摸屏拖拽事件
 */
	public void pointerDragged(int x, int y) {
	}
	/**
	 * 按键
	 * @param keyCode
	 */
	public void actionPerformed(int keyCode) {
		ActionListener action = this.listenerList.getActionListener();
		if (action != null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			action.actionPerformed(e);
		}
	}
	/**
	 * 指针
	 * @param x
	 * @param y
	 */
	public void actionPerformed(int x,int y) {
		ActionListener action = this.listenerList.getActionListener();
		if (action != null) {
			ItemEvent e = new ItemEvent(this, x,y);
			action.actionPerformed(e);
		}
	}

	public boolean enableTip = false;

	public boolean editable = true;

	public void setEidtable(boolean editable) {
		this.editable = editable;
	}

	public void enableTip() {
		enableTip = true;
		tip = new Tip(this);
	}

	public void disableTip() {
		enableTip = false;
	}

	public Tip getTip() {
		return tip;
	}

	public Font smallFont = Font.getFont(0, 0, 8);

	public Font mediumFont = Font.getFont(0, 1, 16);
	
	public Font smallHeavryFont=Font.getFont(0, 1, 8);

	public int mediumFontHeight = mediumFont.getHeight();

	public int smallFontHeight = smallFont.getHeight();

	public Font defaultFont = Font.getDefaultFont();

	public int defaultFontHeight = defaultFont.getHeight();
	
	public Font hypeSmallFont = Font.getFont(0, 4, 8);
	
	public int hypeSmallFontHeight=hypeSmallFont.getHeight();

	public void setFont(int face, int style, int size) {
		defaultFont = Font.getFont(face, style, size);
		defaultFontHeight = defaultFont.getHeight();
	}

	public Font getFont() {
		return defaultFont;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	

}
