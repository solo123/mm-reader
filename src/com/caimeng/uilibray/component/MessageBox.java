package com.caimeng.uilibray.component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.event.MessageBoxListener;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;
import com.caimeng.uilibray.utils.MathFP;
import com.caimeng.uilibray.utils.StringEx;

public final class MessageBox extends BaseControl {

	public static final int WARN_DIALOG = 0;
//
	public static final int ERROR_DIALOG = 1;

	public static final int INFORMATION_DIALOG = 2;

	public static final int QUESTION_DIALOG = 3;

	public static final int SIZE_SMALL = 0;

	public static final int SIZE_MEDIUM = 1;

	public static final int SIZE_LARGE = 2;
	
	public static final int QUIT=100;
	
	private static final int ANIMATION_INTERVAL = 2000 / 12;
	
	private int spinnerHour;

	private Timer timer;

	private int value=0;
	private int size = 0;// 2--32 1--24 0--16 图标大小

	private int yes = 1;
	
	private String alertMes="加载中，请稍候...";
	
	private boolean visible=false;


	private int no = 0;

	private int style;
	
	public int messageStyle=0;//0为对话框模式  1为进度条模式
	
	public String title="系统消息";
	
	private int bkW;
	private int bkH;
	private int msgStyle;

	public void setVisible(boolean visible){
		this.visible=visible;
	}
	
	public boolean getVisible(){
		return this.visible;
	}
	// public static void showMessageDialog(String title,String text,int
	// style,int width,int height){

	// }

	// public static void showMessageDialog(String title,String text,int
	// style,int x,int y,int width,int height){

	// }
	public MessageBox(int x, int y, int width, int height, int style, int size) {
		super(x, y, width, height);
		this.setStyle(BaseControl.isMessageBox);
		this.size = size;
		this.style = style;
		if (style == WARN_DIALOG) {
			option_icon = UIManager.information;
		} else if (style == ERROR_DIALOG) {
			option_icon = UIManager.information;
		} else if (style == INFORMATION_DIALOG) {
			option_icon = UIManager.information;
		} else if (style == QUESTION_DIALOG) {
			option_icon = UIManager.information;
		}
		if(CMForm.frm_Height>220){
			btn_width=52;
			btn_height=22;
		}else{
			super.x=x-5;
//			super.y=y-5;
			super.width=width+10;
			btn_width=35;
			btn_height=20;
		}
		
	}
	public void setMegStyle(int style){
		this.msgStyle=style;
	}
	public int getMsgStyle(){
		return this.msgStyle;
	}
	public MessageBox() {

	}

	public void addMsgBoxListener(MessageBoxListener e){
		listenerList.add(e);
	}
	public void messageBoxStateShow(int keyCode){
		MessageBoxListener item = this.listenerList.getMessageBoxListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			item.messageBoxStateShow(e);
		}
		
	}
	
	/*public void messageBoxStateShow(){
		MessageBoxListener item = this.listenerList.getMessageBoxListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this);
			item.messageBoxStateShow(e);
		}
		System.out.println("前："+this.isVisible());
		if(this.isVisible()){
			
			this.ui.xWForm.alarm=true;
			stopTimer();
			this.visible=false;
		}else{
			this.ui.xWForm.alarm=true;
			this.visible=true;
			startTimer();
		}
		System.out.println("后："+this.isVisible());
	}*/
	
	public void setGroundSize(int w,int h){
		this.bkW=w;
		this.bkH=h;
	}
	private Image option_icon = null;

	public int btn_width = 35;// 窗口按钮宽52

	public int btn_height = 20;// 窗口按钮高24

	public void drawFocus(Graphics g) {

	}
	
	public void setMessageStyle(int style){
		this.messageStyle=style;
	}

	private Vector mesg = null;

	public void setMessage(String mesg) {
		this.mesg=null;
		if(CMForm.frm_Height>220){
			
			this.height=CMForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*((CMForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2);
		}else{
			this.height=CMForm.frm_Height-UIManager.top_bg.getHeight()*3;
		}
		if(CMForm.frm_Width>CMForm.frm_Height ||CMForm.frm_Width>240 ){
			width=UIManager.msg_top.getWidth()-8;
			System.out.println("width=="+width);
			if(CMForm.frm_Height<=320)
			this.height=CMForm.frm_Height-UIManager.top_bg.getHeight()*3;
			x=(CMForm.frm_Width-UIManager.msg_top.getWidth())/2;
		}
		this.mesg = StringEx.lineCast(mesg, width -10- option_icon.getWidth(), smallFont);
		// 自适应因为字改变高度（宽度不能自适应）
//		System.out.println("size========"+this.mesg.size());
		if ((height-(UIManager.bottom_bg.getHeight()<<1))+8 < this.mesg.size()*(smallFont.getHeight()+2)) {
			height = this.mesg.size() * (smallFont.getHeight()+2)+ (UIManager.bottom_bg.getHeight()<<1);
		}
		if(y+height>CMForm.frm_Height){
			y=y-(y+height-CMForm.frm_Height);
		}
	}
	
	public void stopMessageTimer(){
		ui.xmlForm.alarm=false;
		stopTimer();
		messageStyle=0;
		this.setVisible(false);
		ui.xmlForm.repaint();
//		ui.xWForm.repaint();
	}
	
	public void startMessgeTimer(){
		
		messageStyle=1;
		ui.xmlForm.alarm=true;
		setVisible(true);
		startTimer();
		ui.xmlForm.repaint();
	}
	
	public void setMessageMode(int mode,String message){
		this.setStyle(mode);
		stopTimer();
		messageStyle=0;
		ui.xmlForm.alarm=true;
		setVisible(true);
		if(mode==3){
			focus=true;
		}
		setMessage(message);
		ui.xmlForm.repaint();
		
	}

	public Vector getMessage() {
		return mesg;
	}	
	 public   void drawRGB(Graphics g) { 
	        int ai[] = new int[bkW]; 
	        for (int j1 = 0; j1 < ai.length; j1++) 
	            ai[j1] = 0x90000000; 
	        g.drawRGB(ai, 0, 0, 0, 0, bkW, bkH, true); // 绘制透明景色 
	    } 

	public void paint(Graphics g) {
		drawRGB(g);
		if(messageStyle==0){
			if(messageStyle==0||messageStyle==1){
				// 背景
				
				g.setColor(255,255,255);
				g.fillRect(x , y +UIManager.msg_top.getHeight(), width+2, height-UIManager.msg_bottom.getHeight()+8);
				// 标题
				g.drawImage(UIManager.msg_top, x, y, 0);
//			g.drawImage(UIManager.graylogo, x + 0+5, y + 4, 0);
//			g.drawImage(UIManager.msg_bottom,  x, y+btn_offy , Graphics.TOP|Graphics.LEFT);
				g.setColor(255, 255, 255);
				g.setFont(smallHeavryFont);
				if(title!=null)
					g.drawString(title, x+10, y + 4, 0);
			}
			
			
			g.drawImage(option_icon, x+ 4 , y +( height>>1)-(option_icon.getHeight()>>1), Graphics.LEFT | Graphics.TOP);
			// 内容（内容区域 (x + 4 + size * 4,y+12+4)）
			g.setFont(smallFont);
			g.setColor(52, 52, 52);
			if (mesg != null) {
				int length = mesg.size();
				int ty=0;
				int h1=height-(UIManager.top_bg.getHeight()<<1);
				int top=h1/(h1/(smallFont.getHeight()+2)*length);
				top=top+y+UIManager.top_bg.getHeight()+smallFont.getHeight()/2;
				for (int i = 0; i < length; i++) {	
					ty= top+(smallFont.getHeight()+2)*i;
					g.drawString("" + mesg.elementAt(i), x +  8+ option_icon.getWidth(),ty, Graphics.LEFT| Graphics.TOP);
				}
			}
			
				g.drawImage(UIManager.line, x+8, y+height-UIManager.bottom_bg.getHeight()+3, Graphics.TOP|Graphics.LEFT);
				g.drawImage(UIManager.msg_bottom,  x, y+height-UIManager.bottom_bg.getHeight()+8 , Graphics.TOP|Graphics.LEFT);
//				g.setClip(x + 0, y + 0, width, height+20);		
				g.setColor(211, 226, 231);
				
			// 按钮(默认72*24)
			if (this.style == INFORMATION_DIALOG) {
				
					g.drawImage( UIManager.btn_1, x + btn_offx + (width) / 2,y+height-UIManager.bottom_bg.getHeight()+10, Graphics.TOP	| Graphics.HCENTER);
				// 按钮文字
				g.setColor(52, 52, 52);
				g.drawString("OK", x + btn_offx + width / 2,y+height-UIManager.bottom_bg.getHeight()+10, Graphics.TOP	| Graphics.HCENTER);
			} else if (this.style == QUESTION_DIALOG) {
				if (focus) {
					if (yes == 1){
						if(CMForm.frm_Height>220){
							g.drawImage(UIManager.btn_1, x + 0+ btn_offx + (width - btn_width) / 4,y+height-UIManager.bottom_bg.getHeight()+10, 0);
							g.drawImage(UIManager.btn_2, x + 0+ btn_offx + (width - btn_width) / 4+ btn_width+ 10,y+height-UIManager.bottom_bg.getHeight()+10, 0);
						}else{
							
							g.drawImage(UIManager.btn_1, x + width/4-btn_width/2, y+height-UIManager.bottom_bg.getHeight()+10, 0);
							g.drawImage(UIManager.btn_2, x + 0	+ btn_offx + (width - btn_width) / 4 + btn_width+ 10,  y+height-UIManager.bottom_bg.getHeight()+10, 0);
						}
					}
						
					
					else if (no == 1){
						
						if(CMForm.frm_Height>220){	
							g.drawImage(UIManager.btn_2, x + 0+ btn_offx + (width - btn_width) / 4, y+height-UIManager.bottom_bg.getHeight()+10, 0);
							g.drawImage(UIManager.btn_1, x + 0+ btn_offx + (width - btn_width) / 4+btn_width+ 10,  y+height-UIManager.bottom_bg.getHeight()+10, 0);
						}else{
							
							g.drawImage(UIManager.btn_2, x + width/4-btn_width/2, y+height-UIManager.bottom_bg.getHeight()+10, 0);
							g.drawImage(UIManager.btn_1, x + 0+ btn_offx + (width - btn_width) / 4 + btn_width+ 10, y+height-UIManager.bottom_bg.getHeight()+10, 0);
						}
					}
				} else {
					if(CMForm.frm_Height>220){
						g.drawImage(UIManager.btn_2,  x + 0+ btn_offx + (width - btn_width) / 4, y+height-UIManager.bottom_bg.getHeight()+10, 0);
						g.drawImage(UIManager.btn_1,  x + 0+ btn_offx + (width - btn_width) / 4+ btn_width + 10,y+height-UIManager.bottom_bg.getHeight()+10, 0);
					}else{
						
						g.drawImage(UIManager.btn_2, x + width/4-btn_width/2,  y+height-UIManager.bottom_bg.getHeight()+10 , 0);
						
						g.drawImage(UIManager.btn_1,  x + 0	+ btn_offx + (width - btn_width) / 4 + btn_width + 10,y+height-UIManager.bottom_bg.getHeight()+10, 0);
					}
				}
				g.setColor(52,52, 52);
				g.drawString("确定", x + 0 + btn_offx + width / 4+10, y+height-UIManager.bottom_bg.getHeight()+10, Graphics.TOP| Graphics.HCENTER);
				g.drawString("取消", x + 0 + btn_offx + btn_width + width / 4	+ 25, y+height-UIManager.bottom_bg.getHeight()+10 , Graphics.TOP| Graphics.HCENTER);
			}
		}else if(messageStyle==1){
			
			paintSpinner(g);
			
	  }/***********************************动态绘制 messagetstyl=2*************************************************/	
	  else  if(messageStyle==2)
	  {
		paintDynamicFrm(g);
		if(isPaintDynamic)
		{     
			  isPaintDynamic=false;
		      new Thread() {
				public void run() {
					//计算增量
					int increment_x=initi_x-x;
					int	increment_y=initi_y-dynamic_y;
					int increment_width=width-initi_width;
					int increment_height=dynamic_height-initi_height;
					int sun=increment_x+increment_y+increment_width+increment_height;
					
					while (true) {
						if (initi_x > x) {
							initi_x = initi_x - (increment_x*20)/sun;
						}
						if (initi_y > dynamic_y) {
							initi_y = initi_y - (increment_y*20)/sun;
						}
						if (initi_width < width) {
							initi_width = initi_width + (increment_width*20)/sun;
						}
						if (initi_height < dynamic_height) {
							initi_height = initi_height + (increment_height*20)/sun;
						}
						if (initi_x <= x && initi_y <= dynamic_y && initi_width >= width&& initi_height >= dynamic_height) {
							   if(!isPaintBox)
							   { 
								   isPaintBox=true;
								   ui.xmlForm.repaint();
							   }
							break;
						}
						ui.xmlForm.repaint();
					}
				}
			}.start();
		 }
	    }
		/***********************************动态绘制 messagetstyl=2*************************************************/
		// if focus
//		if (focus) {
//			ImageDiv.drawJiuGong(g, UIManager.browse_review_bright, x + 0 - 8, y
//					+ 0 - 8, width + 16, height + 16);
//		}
	}
	
	public void setStyle(int style){
		this.style=style;
	}

	protected void paintSpinner(Graphics g) {
//		System.out.println("进度条");
		// The component is square so get the size of a side.
		int side = Math.min(width,height/2);

		// Get the diameter of a circle that makes up the spinner.
		int diameter = side /8;
		int radius = diameter / 2;

		// The centers of all the circles are on the hour hands of a clock.
		int toCenter = MathFP.toFP(side /5);
//		int temp = (side / 2) - radius;
//		int temp=bkW-2*x;
		int left = x +width/2;
		int top =y+UIManager.msg_top.getHeight()+(height-UIManager.msg_top.getHeight())/2-10;

		// Adjust for the horizontal alignment.
		int horizontalAlignment = Graphics.LEFT;

		if (horizontalAlignment == Graphics.HCENTER) {
			left += (width - side) / 2;
		} else if (horizontalAlignment == Graphics.RIGHT) {
			left += (width - side);
		}

		// The color of this circle is between the background and primary
		// component color. (spinnerHour + 1) is at full color while one
		// more is at the background color. This gives the illusion of
		// it moving clockwise.
		// int background = theme.getBackgroundColor();
		// int foreground = theme.getBorderColor();

		int redStart = 255;//(Attribute.backR);// >> 16;
		int greenStart =255;//(Attribute.backG); // >> 8;
		int blueStart = 255;//(Attribute.backB);
//
//		int redDelta = 35; // >> 16;
//		int greenDelta = 60; // >> 8;
//		int blueDelta = 201;

		int redDelta = 49; // >> 16;
		int greenDelta = 138; // >> 8;
		int blueDelta = 189;
		redDelta = (redDelta - redStart) / 12;
		greenDelta = (greenDelta - greenStart) / 12;
		blueDelta = (blueDelta - blueStart) / 12;


		// Draw each circle.
		for (int hour = 1; hour <= 12; hour++) {
			// Each hour hand on a clock is spaced 30 degrees apart (360 degrees
			// / 12 hours).
			// They fall onto a 30-60-90 degree triangle.

			// Calculate the center of this circle.
			int angle = ((hour - 3) * -30 + 360) % 360; // 3 o'clock = 0
														// degrees, 12 = 90
														// degrees
			int radians = MathFP.div(MathFP.mul(MathFP.toFP(angle), MathFP.PI), MathFP.toFP(180));
			int cx = MathFP.toInt(MathFP.round(MathFP.cos(radians) * MathFP.toInt(toCenter),1));
			int cy = MathFP.toInt(MathFP.round(MathFP.sin(radians) * MathFP.toInt(toCenter),0) * -1); // Y-coordinates		
			// Draw a circle for this spoke on the spinner.
			int offset = ((hour - spinnerHour) + 12) % 12;
			int red = redStart + (offset * redDelta);
			int green = greenStart + (offset * greenDelta);
			int blue = blueStart + (offset * blueDelta);
			
			g.setColor(red, green, blue);
			g.fillRoundRect(left + cx+0, top + cy+0, diameter, diameter, diameter,
					diameter);
			
		}
//		g.setColor(52,52,52);
//		g.setFont(smallFont);
//		g.drawString(alertMes, left+(diameter>>1), top+(diameter<<2)+13, Graphics.TOP|Graphics.HCENTER);
//		g.drawString("已完成"+Integer.toString(value)+"%",left+ 0+(diameter>>1), top+y + 0+(diameter<<1), Graphics.TOP|Graphics.HCENTER);
//		if(value==100){
//			//完成,销毁进度界面
//		}
	}
	
	
	public void setAlertMessage(String str){
		alertMes=str;
	}
	boolean start=false;
	private void startTimer() {
	if (timer == null) {
			timer = new Timer();
			timer.schedule(new SpinnerTask(), 100, ANIMATION_INTERVAL);			
			
		}
		
	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private  class SpinnerTask extends TimerTask {
		public void run() {
			// Increment the position of the lead spoke.
			spinnerHour = (spinnerHour + 1) % 12;
			// System.out.print("run");
			// Repaint the spinner.
			ui.xmlForm.repaint();
			ui.xmlForm.serviceRepaints();
			
		}
	}
	
	
	private int btn_offx = 0;

	private int btn_offy =88+UIManager.msg_top.getHeight();

	public void keyPressed(int keyCode) {
		// if(Attribute.skin==Attribute.SKIN_PLAIN){
		// }
//		this.focus=true;
		
//		if (!ui.selector.button) {
//			ui.selector.button = true;
//		}

		if (this.style == QUESTION_DIALOG) {
			
			if (keyCode == Key.RIGHTKEY(keyCode)  || keyCode==Key.DOWNKEY(keyCode) ) {
				if (no != 1) {
					System.out.println("ssss");
					no = 1;
					yes = 0;
					//this.ui.xWForm.repaint();
				}
				
			} else if (keyCode == Key.LEFTKEY(keyCode)  || keyCode==Key.UPKEY(keyCode)) {
				if (yes != 1) {
					yes = 1;
					no = 0;
					//this.ui.xWForm.repaint();
				}
			}
		}
		/*****************************************messageStyle==2**********************************************/
		if(messageStyle==2)
		{
			 if (keyCode == Key.DOWNKEY(keyCode)) {
                 for(int i=0;i<btnItemV.size();i++)
	    		 {  
	    			  buttonItem  btni=(buttonItem)btnItemV.elementAt(i);
	    			  if(btni.isChosen)
	    			  {
	    				   ((buttonItem)btnItemV.elementAt(i)).isChosen=false;
	    				  if(i+1<btnItemV.size())
	    				  {
		    				((buttonItem)btnItemV.elementAt(i+1)).isChosen=true;
	    				  }else
	    				  {
	    					 ((buttonItem)btnItemV.elementAt(0)).isChosen=true;
	    				  }
	    				  break;
	    			  }
	    		  }
	          	} else if (keyCode == Key.UPKEY(keyCode)) {
				for(int i=0;i<btnItemV.size();i++)
	    		{
	    			  buttonItem  btni=(buttonItem)btnItemV.elementAt(i);
	    			  if(btni.isChosen)
	    			  {      
	    				      ((buttonItem)btnItemV.elementAt(i)).isChosen=false;
	    				      if(i-1>=0)
	    				      {
	    				    	  ((buttonItem)btnItemV.elementAt(i-1)).isChosen=true;
	    				      }else  
	    				      {
			    				  ((buttonItem)btnItemV.elementAt(btnItemV.size()-1)).isChosen=true;
			    			  }
	    			          break;
	    			  }
	    		 }
		    }
		
		}
	   /***************************************************************************************/
		if (keyCode == Key.FIREKEY(keyCode)) {
			
			if(messageStyle==0){
				if(ui.xmlForm.alarm)
				ui.xmlForm.alarm=false;
				this.setVisible(false);
				super.keyPressed(keyCode);
				ui.xmlForm.tempstate=2;
			}/******************messageStyle==2***************/
			else if(messageStyle==2)
	        {  
	            	super.keyPressed(keyCode);
	        }
          }
		

	}
	/**
	 * 对话框按钮 类型 （确定或取消）
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean getPointerBtnStyle(int x,int y){
		boolean isPointer=false;
		if (this.style == INFORMATION_DIALOG) {
			int x1=this.x + btn_offx + (width) / 2-UIManager.btn_1.getWidth()/2;
			int y1=this.y+height-UIManager.bottom_bg.getHeight()+10;
			if(x>=x1&& x<=x1+UIManager.btn_1.getWidth()
					&& y>=y1 && y<=y1+UIManager.btn_1.getHeight()){
				yes=1;
				no=0;
				isPointer=true;
			}
				
		}else{
			
			if(CMForm.frm_Height>220){
				int x1=this.x + btn_offx + (width - btn_width) / 4 ;
				int y1=this.y+height-UIManager.bottom_bg.getHeight()+10;
				if(x>=x1&& x<=x1+UIManager.btn_1.getWidth()
						&& y>=y1 && y<=y1+UIManager.btn_1.getHeight()){
					yes=1;
					no=0;
					isPointer=true;
				}else if(x>=x1+btn_width+10 && x<=x1+btn_width+10+UIManager.btn_2.getWidth()
						&& y>=y1 && y<=y1+UIManager.btn_2.getHeight()){
					no=1;
					yes=0;
					isPointer=true;
				}
			}else{
				int x1=this.x + width/4-btn_width/2;
				int y1=this.y+height-UIManager.bottom_bg.getHeight()+10;
				if(x>=x1&& x<=x1+UIManager.btn_1.getWidth()
						&& y>=y1 && y<=y1+UIManager.btn_1.getHeight()){
					yes=1;
					no=0;
					isPointer=true;
				}else if(x>=this.x + width/4-btn_width/2 && x<=this.x + width/4-btn_width/2+btn_width+10+UIManager.btn_2.getWidth()
						&& y>=y1 && y<=y1+UIManager.btn_2.getHeight()){
					no=1;
					yes=0;
					isPointer=true;
				}
			}
		}
		return isPointer;
	}

	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		if(getPointerBtnStyle(x,y)){
			ui.xmlForm.repaint();
			if(messageStyle==0){
				if(ui.xmlForm.alarm)
				ui.xmlForm.alarm=false;
				this.setVisible(false);
				super.pointerPressed(x, y);
			}
		}
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}

	public int getSelect() {
		if (yes == 1)
			return 1;
		else if (no == 1)
			return 2;
		return 0;
	}

	public void keyReleased(int keyCode) {
		//if(Attribute.skin==Attribute.SKIN_PLAIN){			
		//}
		if (keyCode == Key.FIREKEY(keyCode)) {
			//btn_offx--;
			//btn_offy--;
			super.keyReleased(keyCode);
			ui.selector.button = false;
		}

	}
	
	/***********************************动态绘制 messagetstyl=2*************************************************/
    //初始值  
	private  int initi_x=0;   //定义动态绘制的起点
	private  int initi_y=0;
	private  int initi_width=0;
	private  int initi_height=0;
    public   boolean isPaintDynamic=true;//动态绘制
    public   boolean isPaintBox=false;//绘制案例的添加
    /**
	 * 动态框的按钮项容器
	 */
	public Vector btnItemV=new Vector();
    /**
     * 初始化动态绘制的坐标，宽高
     * @param initi_x
     * @param initi_y
     * @param initi_width
     * @param initi_height
     */
    public   void  initiDiaogs(int initi_x ,int initi_y,int initi_width,int initi_height)
    {
    	this.initi_x=initi_x;
    	this.initi_y=initi_y;
    	this.initi_width=initi_width;
    	this.initi_height=initi_height;
    }
   
	/**
	 * @param itemName
	 * @param nodecode
	 * @param nodeParentId
     * @param executeC
	 * @param isChosen
	 */
    public  void  addButtonItem(String itemName,String nodecode ,String nodeParentId,executeCommad executeC,boolean isChosen)
    {    
    	  buttonItem  btnItem=new buttonItem(itemName,nodecode,nodeParentId,executeC,isChosen);
    	
    	  if(btnItemV==null)
    	  {  
    		  btnItemV=new Vector();
    	  }else   if(isChosen)
	      {       for(int i=0;i<btnItemV.size();i++)
			      {
					  buttonItem  btni=(buttonItem)btnItemV.elementAt(i);
					  if(btni.isChosen)
					  {
						  btnItemV.removeElementAt(i);
						  btni.isChosen=false;
						  btnItemV.insertElementAt(btni, i);
					  }
			       }
	      }
    	  btnItemV.addElement(btnItem);
    }
    public  class  buttonItem
    {   
    	public  boolean isChosen=false;
    	public  String  itemName;
    	public  String  nodecode;
    	public  executeCommad executeC;
    	public  String nodeParentId;
    
    	public  buttonItem(String itemName,String nodecode,String nodeParentId, executeCommad executeC,boolean isChosen)
    	{
    	    this.itemName=itemName;
    	    this.nodecode=nodecode;
    	    this.isChosen=isChosen;
    	    this.executeC=executeC;
    	    this.nodeParentId=nodeParentId;
    	}
    }
    public class  executeCommad
    {
        public  void execute(buttonItem  btnitem){}
    }   
    /***
	* 画动态窗口
	* @param g
	*/
    private void paintDynamicFrm(Graphics g) {
		drawRGB(g);
		g.fillRect(initi_x + 0, initi_y + 0+UIManager.msg_top.getHeight(), initi_width, initi_height);
//		g.setColor(77, 130, 242);
//		g.drawRect(initi_x, initi_y, initi_width, initi_height);
		if (isPaintBox) {  
			ImageDiv.fillScreen(g, UIManager.msg_top, initi_x, initi_y, initi_width + initi_x,UIManager.msg_top.getHeight());
//			g.drawImage(UIManager.graylogo, initi_x+2, initi_y+4, 0);
			   
				setButtonSize(104, btn_height);
			     //标题
				 g.setColor(255, 255, 255);
				 g.setFont(smallFont);
//				 g.setClip(0, 0, XWForm.frm_Width,XWForm.frm_Height	);
				 if (title != null) 
					 g.drawString(title, initi_x+ 5, initi_y + 0 + 4, 0);
				
				 int btn_x=initi_width/2+initi_x-btn_width/2;
			     int btn_y=initi_y+UIManager.msg_top.getHeight()+UIManager.msg_top.getHeight()/2;
				 for(int i=0;i<btnItemV.size();i++)
				 {
					  buttonItem  btnitem=(buttonItem)btnItemV.elementAt(i);
					  if(btnitem.isChosen)
				      {
				    	  ImageDiv.drawJiuGong(g,UIManager.connect_bg, btn_x-2, btn_y+btn_height*i+i*4-2, btn_width + 5,btn_height + 5);
						  ImageDiv.drawJiuGong(g,UIManager.btn_1, btn_x, btn_y+btn_height*i+i*4, btn_width, btn_height);
				      }else
				      {
				    	  ImageDiv.drawJiuGong(g,UIManager.btn_2, btn_x, btn_y+btn_height*i+i*4, btn_width, btn_height);
				      }
					  g.setColor(52, 52, 52);
					  g.drawString(btnitem.itemName, btn_x+btn_width/2, btn_y+btn_height*i+i*4+3, Graphics.TOP| Graphics.HCENTER);
					  ui.xmlForm.repaint();
				}
				 if(CMForm.frm_Height>220){
					 
					 setButtonSize(52, 24);
				 }else{
					 setButtonSize(35, 20);
				 }
				 
			}
		
	}
    /**
     * 设置button按钮的宽高
     * @param width
     * @param hiegth
     */
	public void setButtonSize(int width,int hiegth)
	{
	    this.btn_width=width;	
	    this.btn_height=hiegth;
	    
	}
	/**
	 * 初始化动态划出窗口参数
	 */
	 public  void  initiDynamic()
	 {  setVisible(true);
		focus=true;
		isPaintDynamic=true;
		isPaintBox=false;
		initiDiaogs(CMForm.display.getCurrent().getWidth()/2, CMForm.display.getCurrent().getHeight()/2, 0, 0);
		setButtonSize(104, btn_height);
		
		setMessageStyle(2);
	   
	    int height=0;
	    if(btnItemV!=null){
	        height=btnItemV.size()*(btn_height+1)+UIManager.msg_top.getHeight()+btn_height;
	        dynamic_y=(CMForm.display.getCurrent().getHeight()-height)/2;
	     }else
	     {
	    	 height=(CMForm.display.getCurrent().getHeight()*2)/5; //使用默认
	    	 dynamic_y=y;//使用默认
	     }
	   // setSize(XWForm.display.getCurrent().getWidth()-((XWForm.display.getCurrent().getWidth()/7)<<1),height);
	     dynamic_height=height;
	 }
	private  int dynamic_y;
	private  int dynamic_height;
	/*****************************************************************************************************************/


}
