package com.caimeng.uilibray.container;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.component.DataList;
import com.caimeng.uilibray.component.MessageBox;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.menu.ToolBar;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;


public class CMForm extends Canvas {

	private UIManager ui = null;

	public static int frm_Width = 0;

	public static int frm_Height = 0;

	public static Display display;
	
	public int height;

	// Font f;
	// public boolean flashFlag;

	private Vector lastPaint = new Vector();

	private Timer timer;

/*	private int br = 214;

	private int bg = 231;

	private int bb = 245;

	private int fr = 0;

	private int fg = 0;

	private int fb = 0;*/
	
	public String errorMessage="";
	
	public boolean alarm=false;
	
	public static int tempstate=0;
	
	public boolean online;
	
	private Image logoImage=null;

	private String menuTitle = "";
	private Graphics graphics;
	
	//public boolean hasTitle;
	public int keyCode=0;
	public Image backImage=null;
	public Image map=null;
	// public boolean flag=true;
	// flag 为是否有状态栏
	public CMForm(String title, Display display, boolean flag) {
		
		setFullScreenMode(true);	
		this.title = title;
		CMForm.display = display;
		frm_Width =this.getWidth();
		frm_Height = this.getHeight();
	}
	public void initUI(XMLForm form){
		ui = new UIManager(form);
	}
	public CMForm()
	{
		setFullScreenMode(true);	
		frm_Width =this.getWidth();
		frm_Height = this.getHeight();
	}
	

	public String title = "";

	public void setTitle(String title) {
		this.title = title;
	}	

	private boolean labelScroll=false;
	private boolean hasScrollBar=true;
	public void add(BaseControl baseControl) {
		this.ui.addItem(baseControl);
		baseControl.setUIManager(this.ui);
		if(baseControl.style==baseControl.isList){
			hasScrollBar=false;
		}
		height=baseControl.height+height;
		if (baseControl.style == 5) {
			baseControl.getIndex();
			lastPaint.addElement(new Integer(baseControl.index));
		}
	}

	public void addItem(BaseControl baseControl) {
		this.ui.addItem(baseControl);
		
		baseControl.setUIManager(ui);
	}

	public void addMenuBar(ToolBar toolBar) {
		toolBar.ui=this.ui;
//		System.out.println("ui....."+ui);
		ui.addMenuBar(toolBar);
	}

	public void addMessageBox(MessageBox messageBox){
		messageBox.ui=this.ui;
		ui.addMessageBox(messageBox);
	}
	
	
	protected void paint(Graphics g) {
		// 画背景
		g.setColor(ColorAttribute.screen_bg);
		g.fillRect(0, 0, frm_Width, frm_Height);
		graphics = g;
		if(backImage!=null)
		{
			ImageDiv.fillScreen(g, backImage, 0, 0, frm_Width, frm_Height);
			if(logoImage!=null){
				g.drawImage(logoImage, frm_Width>>1, frm_Height>220?40:26, Graphics.TOP|Graphics.HCENTER);
			}
		}
		int list = 0;
		try{
			
			while (list < ui.getItemSum()) {
				if (ui.getItem(list).isVisible() && ui.getItem(list).style != 5) {
					if(ui.getItem(list).style==4 && ui.getItem(list).isVisible()){
						continue;
					}
					
					ui.getItem(list).paint(g);
				}
				list++;
			}
			if (lastPaint.size() > 0) {
				for (int i = 0; i < lastPaint.size(); i++) {
					if(ui.getItem(((Integer) lastPaint.elementAt(i)).intValue()).isVisible()){
						
						ui.getItem(((Integer) lastPaint.elementAt(i)).intValue()).paint(g);	
					}
					
				}
			}
			if(ui.getItem(ui.selector.selected).focus && ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isComboBox){
				ui.getItem(ui.selector.selected).paint(g);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
		if (ui.getMenuBar() != null) {
			ui.getMenuBar().paint(g);
		}
		if(ui.getMessageBox()!=null && ui.getMessageBox().getVisible()){
			ui.getMessageBox().paint(g);
		}
		if(hasScrollBar){
			
			/********************画框架的滚动条部分**************************/
			int scrollH=frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-20;
			int onShowSelectedItemNum=ui.selector.selected;
			int length=ui.getItemSum();
			int dh=scrollH/(length>0?length:length+1);
			int dy=(dh)*(onShowSelectedItemNum);
			if((onShowSelectedItemNum)==length-1){
				if(onShowSelectedItemNum!=0){
					
					int mistake=scrollH-dy-dh;//误差
					if(mistake!=0)
						dy=dy+mistake-2;
					else
						dy=dy-2;
				}else{
					dh=dh-2;
				}
			}
			
			g.setClip(0, 0,frm_Width,frm_Height);		
			g.setColor(99, 99, 99);
			g.fillRect( frm_Width-5, UIManager.top_bg.getHeight()+21, 5,scrollH);
			g.setColor(253, 253, 253);	
			g.fillRect( frm_Width-4, UIManager.top_bg.getHeight()+22 + dy, 4, dh);
		}
	}

	public boolean isMainTab=false;
	public UIManager getUIManager() {
		return ui;
	}

	// 按键按下响应动作
	protected   void keyPressed(int keyCode) {
		this.keyCode=keyCode;
		if(alarm){
			ui.getMessageBox().keyPressed(keyCode);
			
		}else {	
		
			
		if (ui.getMenuBar() != null) {
			ui.getMenuBar().keyPressed(keyCode);
		}
		if(isMainTab){
			if(ui.getItem(ui.selector.selected).style!=ui.getItem(ui.selector.selected).isBarCode){
				
				if ((keyCode == Key.LEFTKEY(keyCode))) {
					
					ui.selector.selected=0;
					ui.getItem(ui.selector.selected).keyPressed(keyCode);
					return;
				}else if((keyCode == Key.RIGHTKEY(keyCode))){
					ui.selector.selected=0;
					ui.getItem(ui.selector.selected).keyPressed(keyCode);
					return;
				}
			}
		}
		if (ui.selector.messageBox) {
			ui.getItem(ui.selector.selected).keyPressed(keyCode);
		} else if ((ui.getMenuBar() != null) && ui.getMenuBar().menuOpen) {
		} else {
			if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isBarCode && ui.selector.barCode){
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			}else
			if (ui.selector.inComboItem || ui.selector.inInputItem) {
				
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			} else if (ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isNineGrid) {
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			}else if (ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isTab) {
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
				
				if ((keyCode == Key.UPKEY(keyCode))) {
					
					int index1 = ui.selector.selected;
					while (!ui.selector.preElement()) {
						if (ui.selector.isLast) {
							ui.selector.selectorBack(ui.selector.selected,index1);
							break;
						}
					}
					if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isCheckBox )
					{
						
					BaseControl baseControl = ui.getItem(ui.selector.selected);
					if (baseControl != null &&!baseControl.isPass()) {
						
						baseControl.keyPressed(keyCode);
					  }
					}				
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
					
					
				} else 	if ((keyCode == Key.DOWNKEY(keyCode))) {						
					int index = ui.selector.selected;
				
					while (!ui.selector.nextElement()) {
						if (ui.selector.isLast) {
							ui.selector.selectorBack(ui.selector.selected,	index);
							break;
						}
					}
					if((ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isList))
					{	
						BaseControl baseControl = ui.getItem(ui.selector.selected);
						
					if (baseControl != null &&!baseControl.isPass()) {
						
						baseControl.keyPressed(keyCode);
					   }
					}					
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
				}
				
			}/*else if (ui.selector.locate) {
				System.out.println("come in");
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			} */
			else if (ui.selector.checkItem) {
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			} else if(ui.selector.checkBox)
			{
				ui.getItem(ui.selector.selected).keyPressed(keyCode);				
			}else if(ui.selector.barCode){
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			}/*else if(ui.selector.imageLabel){
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
			}*/else if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isRadioBox){
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
				if(keyCode==Key.DOWNKEY(keyCode)){
					//System.out.println("=========================");
					int index = ui.selector.selected;
					while (!ui.selector.nextElement()) {
						if (ui.selector.isLast) {
							ui.selector.selectorBack(ui.selector.selected,	index);
							break;
						}
					}
					if((ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isList))
					{	
						BaseControl baseControl = ui.getItem(ui.selector.selected);
						
					if (baseControl != null && !baseControl.isPass()) {
						
						baseControl.keyPressed(keyCode);
					   }
					}	
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
				}else if(keyCode==Key.UPKEY(keyCode)){
					int index1 = ui.selector.selected;
					while (!ui.selector.preElement()) {
						if (ui.selector.isLast) {
							
							ui.selector.selectorBack(ui.selector.selected,index1);
							break;
						}
					}
					if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isCheckBox )
					{
					BaseControl baseControl = ui.getItem(ui.selector.selected);
					if (baseControl != null &&!baseControl.isPass()) {
						
						baseControl.keyPressed(keyCode);
					  }
					}				
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
					
				}
			}/*else if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isMap)
			{
				
				ui.getItem(ui.selector.selected).keyPressed(keyCode);
				if(keyCode==Key.DOWNKEY(keyCode)){
					//System.out.println("=========================");
					int index = ui.selector.selected;
					while (!ui.selector.nextElement()) {
						if (ui.selector.isLast) {
							ui.selector.selectorBack(ui.selector.selected,	index);
							break;
						}
					}									
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
				}else if(keyCode==Key.UPKEY(keyCode)){
					int index1 = ui.selector.selected;
					while (!ui.selector.preElement()) {
						if (ui.selector.isLast) {
							
							ui.selector.selectorBack(ui.selector.selected,index1);
							break;
						}
					}							
					ui.selector.setoffset(ui.getItem(ui.selector.selected));
					
				}
			}*/
			else {
				//System.out.println("do 111");
				if ((keyCode == Key.UPKEY(keyCode))) {
					BaseControl base=ui.getItem(ui.selector.selected);
					if(base.height>40 && (base.style==base.isLabel) ){
						//向上可以跳过
//						System.out.println("向上"+(ui.offsetY)+"    "+(base.y+ui.offsetY-25));
						if(base.y+ui.offsetY>this.frm_Height-25 || ui.offsetY<=0){
							int index1 = ui.selector.selected;
							while (!ui.selector.preElement()) {
								if (ui.selector.isLast) {
									
									ui.selector.selectorBack(ui.selector.selected,index1);
									break;
								}
							}
						}
						ui.selector.setoffset(ui.getItem(ui.selector.selected));
					}else{
						int index1 = ui.selector.selected;
						while (!ui.selector.preElement()) {
							if (ui.selector.isLast) {
								
								ui.selector.selectorBack(ui.selector.selected,index1);
								break;
							}
						}
						ui.selector.setoffset(ui.getItem(ui.selector.selected));
					}
						
				
					
						if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isCheckBox  ||ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isList
								||ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isRadioBox  )
						{
							BaseControl baseControl = ui.getItem(ui.selector.selected);
							if (baseControl != null &&!baseControl.isPass()) {
								
								baseControl.keyPressed(keyCode);
							}
						}
				
					
				} else if ((keyCode == Key.DOWNKEY(keyCode))) {
					BaseControl base=ui.getItem(ui.selector.selected);
					
					if(base.height>40 && (base.style==base.isLabel)){
						//向下可以跳过
//						System.out.println("向下"+(frm_Height-base.y-ui.offsetY-25));
						if((frm_Height-base.y-ui.offsetY-25> base.height)){
							
							int index = ui.selector.selected;					
							while (!ui.selector.nextElement()) {
								if (ui.selector.isLast) {
									ui.selector.selectorBack(ui.selector.selected,	index);
									break;
								}
							}	
							if(!ui.selector.isLast)
								ui.selector.setoffset(ui.getItem(ui.selector.selected));
						}else{
							ui.selector.setoffset(ui.getItem(ui.selector.selected));
						}
						
					}else{
						int index = ui.selector.selected;					
						while (!ui.selector.nextElement()) {
							if (ui.selector.isLast) {
								ui.selector.selectorBack(ui.selector.selected,	index);
								break;
							}
						}
						ui.selector.setoffset(ui.getItem(ui.selector.selected));
					}

						if((ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isCheckBox) ||(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isList) 
								||ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isRadioBox ||ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isTab)
						{	
							BaseControl baseControl = ui.getItem(ui.selector.selected);
							
						if (baseControl != null &&!baseControl.isPass()) {
							
							baseControl.keyPressed(keyCode);
						}
						}
					
					
				} else if (keyCode == Key.FIREKEY(keyCode)) {					
					if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isTextBox && this.tempstate==1){
						
					}else{
						
						BaseControl baseControl = ui.getItem(ui.selector.selected);
						if (baseControl != null &&!baseControl.isPass()) {
							baseControl.keyPressed(keyCode);
						}
					}

				}else{
					if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isTextBox 						
							||ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isList		){
						
						BaseControl baseControl = ui.getItem(ui.selector.selected);
						if (baseControl != null &&!baseControl.isPass()) {
							baseControl.keyPressed(keyCode);
						}
					}
				}
			}
		}
		}
		
		this.repaint();
		this.serviceRepaints();
	}

	// 按键抬起只响应事件
	protected   void keyReleased(int keyCode) {
		if (ui.getMenuBar() != null) {
			ui.getMenuBar().keyReleased(keyCode);
		}

		if ((ui.getMenuBar() != null) && ui.getMenuBar().menuOpen) {

		} else {
			
			if(ui.getItem(ui.selector.selected).style==ui.getItem(ui.selector.selected).isTextBox){
				
				BaseControl baseControl = ui.getItem(ui.selector.selected);
				if (baseControl != null) {
					baseControl.keyReleased(keyCode);
				}
			}
		
		}
		
		
	}

	public void keyRepeated(int keyCode) {

	}
	/**
	 * 触摸屏按下事件
	 */
	public void pointerPressed(int x, int y) {
		
		pressTime=System.currentTimeMillis();
		pointPressY=y;
		
	}
	private void dealPointer(int x,int y){
		if(alarm){
			ui.getMessageBox().pointerPressed(x, y);
			
		}else if (ui.getMenuBar() != null && ui.getMenuBar().isMenuPointer(x, y)) {
				ui.getMenuBar().pointerPressed(x, y);
		}else if ((ui.getMenuBar() != null) && ui.getMenuBar().menuOpen) {
			ui.getMenuBar().pointerPressed(x, y);
		} else {
			
			int size=ui.getItemSum();
			for(int i=0;i<size;i++){
				if(x>=ui.getItem(i).x+ui.offsetX && x<=(ui.getItem(i).x+ui.offsetX+ui.getItem(i).width)
						&& y>=ui.getItem(i).y+ui.offsetY && y<=(ui.getItem(i).y+ui.offsetY+ui.getItem(i).height)){
//					System.out.println("找到对应控件");
					
					BaseControl baseControl = ui.getItem(i);
					if (baseControl != null &&!baseControl.isPass()) {
						
						baseControl.pointerPressed(x,y);
					}
					
					ui.selector.setoffset(ui.getItem(i));
					break;
				}
			}
		}
		this.repaint();
		this.serviceRepaints();
	}
	/**
	 * 触摸屏释放事件
	 */
	public void pointerReleased(int x, int y) {
		releasseTime=System.currentTimeMillis();
		
		int dy=pointReleaseY-pointPressY;
		if(releasseTime-pressTime>200 && dy!=0){//大于0.2秒，默认为拖放
			if(ui.getItem(0).style==BaseControl.isList){//列表在屏幕内自身滚动
				if(dy<0){
					((DataList)ui.getItem(0)).pointerUp();
				}else{
					
					((DataList)ui.getItem(0)).pointerDown();
				}
			}else{
				
//				int dy=pointReleaseY-pointPressY;			
				if(dy<0){//向上拉屏，判断最末一个控件是否显示完
					int size=ui.getItemSum();
					if(ui.getItem(size-1).y+ui.offsetY>frm_Height-UIManager.bottom_bg.getHeight()-22){
						if(ui.getItem(size-1).y+ui.offsetY+dy<frm_Height-UIManager.bottom_bg.getHeight()-22){
							ui.offsetY=ui.offsetY-40;
						}else{
							ui.offsetY=ui.offsetY+dy;
						}
					}
					
					
				}else if(dy>0){//向下拉屏，判断第一个控件，是否显示完
					if(ui.getItem(0).y+ui.offsetY<0){
						if(ui.offsetY+dy>0){
							ui.offsetY=0;
						}else{						
							ui.offsetY=ui.offsetY+dy;
						}
					}
				}
			}
			
			this.repaint();
			this.serviceRepaints();
		}else{//单击事件
			dealPointer(x,y);
		}
	}
/**
 * 触摸屏拖拽事件
 */
	public void pointerDragged(int x, int y) {
		pointReleaseY=y;
	}
	/**拖动 屏幕滚动距离**/
	private int pointReleaseY=0;
	private int pointPressY=0;
    private long pressTime=0;
    private long releasseTime=0;
	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new Flash(), 100, 500);
			// System.out.print("run");
		}
	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	// 另一个类的方法
	private  class Flash extends TimerTask {
		public void run() {
			//System.out.println("界面绘");
			repaint();
			serviceRepaints();
		}
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public Graphics getGraphics()
	{
		return graphics;
	}
	protected void hideNotify() {
		// TODO 自动生成方法存根
		super.hideNotify();
		
		
	}
	protected void showNotify() {
		// TODO 自动生成方法存根
		super.showNotify();
//		display.setCurrent(this);
		
	}

}
