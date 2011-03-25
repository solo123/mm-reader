package com.caimeng.uilibray.component;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;
import com.caimeng.software.readerobj.BookInfoAllBean;
import com.caimeng.uilibray.common.AbstructButton;
import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;
import com.caimeng.uilibray.utils.MathFP;





public class ImageLabel extends AbstructButton {
	private  int ANIMATION_INTERVAL = 2000 / 12;
	
//	public  boolean isProBar=false;

	private int spinnerHour;

	private Timer timer;

	public Image BigImage = null;//存放大图

	
	
	public String HttpUrl;
	
	private String ID;
	
    private String alarm = null;
    
    
    private int value=0;
//    private Camera camera;
//    private boolean photoMode=false;
    
    private int mode=0; //0为等待界面  1为显示一张图片 2为显示拍照模式 3为全屏显示模式
	
    public ImageLabel(){
    	
    }
    
	public ImageLabel(int x, int y, int width, int height,Image img) {
		super(x, y, width, height);
//		if(mode==0){
//			
//			this.startTimer();
//			
//		}
		this.setStyle(BaseControl.isImageLabel);
		this.BigImage=img;
//		url="2010120209594559452202.jpg";
		/*if(url!=null && !url.equals("")){
			url=Consts.IMG_URL+url;
		}
		System.out.println("url=="+url);
		this.HttpUrl = url;
		if(url!=null &&!url.equals("")){
			
			new Thread(){
				public void run(){
					Connect connect=new Connect(HttpUrl,"","GET");
					try {
						byte[] data=connect.queryServer("".getBytes());
						if(data!=null){
							
							BigImage=Image.createImage(data,0,data.length);
							ui.xmlForm.repaint();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}*/
		
	}
	private BookInfoAllBean bookinfo;
	private String state="";
	public void setBookInfo(BookInfoAllBean info){
		bookinfo=info;
		if(info.getStatus().equals("0")){
			state="已完成";
		}else{
			state="连载中";
		}
	}
//	public void setTitleAndStar(String title,String star){
//		this.title=title;
//		this.star=star;
//	}

	public Image getBigImage(){
		return BigImage;
	}
	public void setBigImage(Image img){
		BigImage=img;
	}

/*	public ImageLabel(int x,int y ,int width,int height,String title,boolean photomode){
		super(x,y,width,height);
		this.title=title;
		this.photoMode=photomode;
	}
	*/
	private int imgW=55;
	private int imgH=65;
//	private String star;
	public void paint(Graphics g) {
		int posX=x+ui.offsetX;
		int posY=y+ui.offsetY;
		g.setColor(0);
//		int w=ui.cmForm.frm_Width-imgW-posX;
		if(BigImage==null){
			
			g.drawRect(posX, posY,imgW, imgH);
		}else{
			g.drawImage(BigImage, posX, posY, Graphics.LEFT|Graphics.TOP);
//			if(imgW>BigImage.getWidth()){
//				w=ui.cmForm.frm_Width-BigImage.getWidth()-posX;
//			}
		}
		
//		g.setColor(arg0, arg1, arg2)
		if(bookinfo!=null){
			
//			g.setFont(smallHeavryFont);
//			g.drawString(bookinfo.getBookName(),posX+imgW+(w>>1), posY+5, Graphics.TOP|Graphics.HCENTER);
//			g.setColor(0, 0, 255);
			g.setFont(smallFont);
			g.drawString("作者："+bookinfo.getAuthor(),posX+imgW, posY+5, Graphics.TOP|Graphics.LEFT);
			g.setColor(0, 0, 0);
			g.drawString("状态："+state,posX+imgW, posY+5+smallFont.getHeight(), Graphics.TOP|Graphics.LEFT);
			
			g.drawString("推荐数："+bookinfo.getHot(),posX+imgW, posY+5+smallFont.getHeight()*2, Graphics.TOP|Graphics.LEFT);
		}
/*		if(mode==0){	
			
			paintSpinner(g);
		}else
			if(mode!=3)
		     {
			if(mode==2){
				
				if(BigImage!=null){
					if(refreshForm){
						ScaleImage=scale(BigImage ,width-10,imageH-10);
						refreshForm=false;
					}else{
						
						if(ScaleImage==null){
							ScaleImage=scale(BigImage ,width-10,imageH-10);
							refreshForm=false;
						}
					}
					
				}
			}
			g.setColor(0);
			if(title!=null){
				g.setFont(smallHeavryFont);
				g.drawString(title, posX, posY, Graphics.TOP|Graphics.LEFT);
				posY=posY+smallHeavryFont.getHeight()+5;
				posX=x+ui.offsetX+5;
				
			}
			if(width<100){
				if(mode!=1)
					g.drawRect(posX, posY,100, 100);
				else
					g.drawRect(posX, posY,width, imageH);
			}else{
//				g.drawString(process, posX+5, posY+5, 0);
				g.drawRect(posX, posY, width, imageH);
			}
			g.setFont(smallFont);
			if(mode==2){
				
				if(BigImage!=null){			
					g.drawImage(ScaleImage, posX+5, posY+5, g.TOP|g.LEFT);
//					pc=null;
				}else{
					//显示一张默认图片，暂用文字代替
					if(focus){
						g.setColor(0, 220, 247);
					}
					g.drawString("暂无图片", posX+(width>>1), posY+(imageH>>1)-(smallFont.getHeight()>>1), g.TOP|g.HCENTER);
				}
				
				if(BigImage!=null){
					
					if(focus && keyfocus==0){
						g.setColor(0, 220, 247);
					}else{
						g.setColor(0);
					}
					g.drawRect(posX+5-1, posY+4, width-10+1, imageH-10+1);
				}
			}else{
				if(BigImage!=null){	
					if(BigImage.getWidth()>width-1){
//						System.out.println("压缩文件"+width+  "hight"+imageH);
						if(ScaleImage==null){
							ScaleImage=scale(BigImage ,width,imageH);
						}
						g.drawImage(ScaleImage, posX, posY, g.TOP|g.LEFT);
//						System.out.println("posy-=---"+posY);
					}else{
						
						g.drawImage(BigImage, posX+width/2, posY+imageH/2-BigImage.getHeight()/2, g.TOP|g.HCENTER);
					}
				}else{
					//显示一张默认图片，暂用文字代替
					if(width>=100)
					g.drawString("暂无图片", posX+(width>>1), posY+(imageH>>1)-(smallFont.getHeight()>>1), g.TOP|g.HCENTER);
					
				}
			}
			
			
			
		}else
		{
		   /// g.setColor(0);
			g.setClip(0, 0, Attribute.scr_width, Attribute.scr_height);
			g.fillRect(0, 0, Attribute.scr_width, Attribute.scr_height);
			if(map!=null)
			g.drawImage(map, (Attribute.scr_width-map.getWidth())/2, (Attribute.scr_height-map.getHeight())/2, 0);
		}*/

	}

	public Image getImage()
	{
		return BigImage;
	}

	protected void paintSpinner(Graphics g) {
		
		// The component is square so get the size of a side.
//		System.out.println("大屏进度条");
//		g.setColor(247, 247, 247);
		g.fillRect(0, 0, CMForm.frm_Width, CMForm.frm_Height);
		ImageDiv.fillScreen(g, UIManager.login, 0, 0, CMForm.frm_Width, CMForm.frm_Height);
		int side = Math.min(width, height);
		// Get the diameter of a circle that makes up the spinner.
		int diameter = side /8;
		int radius = diameter / 2;

		// The centers of all the circles are on the hour hands of a clock.
		int toCenter = MathFP.toFP(side /5);
		int temp = (side / 2) - radius;
		int left = x + temp;
		int top = y+temp/2;

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

		int redDelta = 35; // >> 16;
		int greenDelta = 60; // >> 8;
		int blueDelta = 201;

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
			int cy =  MathFP.toInt(MathFP.round(MathFP.sin(radians) * MathFP.toInt(toCenter),0) * -1); // Y-coordinates		
			// Draw a circle for this spoke on the spinner.
			int offset = ((hour - spinnerHour) + 12) % 12;
			int red = redStart + (offset * redDelta);
			int green = greenStart + (offset * greenDelta);
			int blue = blueStart + (offset * blueDelta);
			g.setColor(red, green, blue);
			g.fillRoundRect(left + cx+ui.offsetX, top + cy+ui.offsetY, diameter, diameter, diameter,
					diameter);
			
		}
		g.setColor(52,52,52);
		g.drawString(alarm, left+ ui.offsetX+(diameter>>1), top+y + ui.offsetY+(diameter), Graphics.TOP|Graphics.HCENTER);
//		g.drawString("已完成"+Integer.toString(value)+"%",left+ ui.offsetX+(diameter>>1), top+y + ui.offsetY+(diameter<<1), Graphics.TOP|Graphics.HCENTER);
		if(value==100){
			//完成,销毁进度界面
		}
	}
	
	
	public void keyPressed(int keyCode) {
		if(this.isPass()){
			return;
		}
		if(!ui.selector.imageLabel){
			ui.selector.imageLabel=true;
		}
		
			if(keyCode==Key.FIREKEY(keyCode)){
				if(mode==2){
					
					if(BigImage!=null){
						//已经有图片，则打开浏览窗口
						System.out.println("浏览图片");
					}else{
						//暂无图片，则调用相机
					}
				}
			}else if((keyCode == Key.RIGHTKEY(keyCode)  || keyCode==Key.DOWNKEY(keyCode) )){

					ui.selector.imageLabel=false;
				System.out.println("ssssssssssss");
				
			}else if((keyCode == Key.LEFTKEY(keyCode) || keyCode==Key.UPKEY(keyCode) )){
				
					ui.selector.imageLabel=false;
					System.out.println("ssssssssssss");
			
			}
		
	}
	
	
	public void setImage(Image img){
		this.BigImage=img;
	}
	public void setValue(int value){
		this.value=value;
	}
	public int getValue(){
		return value;
	}

	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new SpinnerTask(), 100, ANIMATION_INTERVAL);
			// System.out.print("run");
		}
	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private class SpinnerTask extends TimerTask {
		public void run() {
			// Increment the position of the lead spoke.
			spinnerHour = (spinnerHour + 1) % 12;
			// System.out.print("run");
			// Repaint the spinner.
			
			ui.xmlForm.repaint();
		}
	}
	
	public void getImg()
	{
		
	}
	public int getMode(){
		return mode;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
    /**
     * 提交给服务的图片宽度
     */
	public  int submitWidth=100;
	/**
    * 提交给服务的图片长度
    */
	public  int submitHeight=100;
	
	public String resolutions[];
}

