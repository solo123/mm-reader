package com.caimeng.uilibray.component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.event.ItemStateListener;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.ImageDiv;



public class DataList extends List  {
	/**
	 *  1为双行文字显示   2 单行带复选框（产品列表）  3 双行（资讯） 5查看历史事务   6.通讯录模式
	 */
	private int layoutStyle;//1为双行文字显示   2 单行带复选框（产品列表）  3 双行（资讯） 5查看历史事务

	private Timer timer;

	private int[] stateArray;
	
	private String strTemp;
	
	private int noImageHeight = 42;
	
	private int color=0;
	
	private int[] colorArray;
	/**
	 * 确定是否要启动画字体滚动
	 */
    private  boolean  startTimer=false;

/*	public DataList() {
		super();
		onShowSelectedItemNum = -1;
		time = new Timer();
		time.schedule(new rollString(), 100, 200);		
	}
*/
	public DataList(int x, int y, int width, int height, int style) {
		super(x, y, width+5, height);
		onShowSelectedItemNum = -1;
		this.layoutStyle = style;
		this.style=this.isList;
		
	}
	public DataList() {
		// TODO 自动生成构造函数存根
	}
	public void startTimer(){
		if(timer==null){
			timer= new Timer();
			timer.schedule(new rollString(), 100, 500);	
		}
	}
	public void stopTimer(){
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
	}
	
	private Vector mainItem = new Vector();

	public Vector secondaryItem = new Vector();

	private Vector imgs = new Vector();

	public Image bigImage;
	
	//private Vector imgs_str = new Vector();

	int times;

	private int offsetY;

	//onShowItemNum 在此实际显示的是选中的item-1（为了与comboBox统一）
	public void paint(Graphics g) {
		
		if(layoutStyle!=1&&layoutStyle!=5&&startTimer) //模式一不进行绘制滚动条
		{   
			startTimer();
			startTimer=false;
		}
		// 填充区域
		g.setColor(255,255,255);
		g.fillRect(x + ui.offsetX, y + ui.offsetY, width, height);
		if (layoutStyle == 1 || layoutStyle==2 ||layoutStyle==4 || layoutStyle==5) {
			if(bigImage!=null)
				itemHeight = bigImage.getHeight()+5;
			else
				itemHeight=smallFont.getHeight()+5;
		}else	if(layoutStyle==3){
				if(bigImage!=null ){
					if(bigImage.getHeight()<(smallFont.getHeight()<<1)+10){
						itemHeight=(smallFont.getHeight()<<1)+10;
					}else{						
						itemHeight=bigImage.getHeight();
					}
				}else{		
					if(secondaryItem.size()!=0){
						
						itemHeight=smallFont.getHeight()<<1;
					}else{
						itemHeight=smallFont.getHeight();
					}
				}
			}else if(layoutStyle==7){
				itemHeight=smallFont.getHeight()+12	;
			}
		
		if ((onShowSelectedItemNum + 2) * itemHeight > height) {
			offsetY = (onShowSelectedItemNum + 2) * itemHeight - height;
		} else {
			offsetY = 0;
		}

		/**************通讯录模式**************************/
		if(layoutStyle==6)
		{
			/*for(int i=0;i<width/UIManager.blacklist_top_bg.getWidth()+1;i++)
			{
		        g.drawImage(UIManager.blacklist_top_bg, x + ui.offsetX+i*UIManager.connect_bg.getWidth(), y+ui.offsetY + 0*itemHeight- offsetY, 0);
			}*/
			ImageDiv.drawJiuGong(g, UIManager.connect_bg, x + ui.offsetX,  y+ui.offsetY + 0*itemHeight- offsetY, width, itemHeight);
		}
		/****************************************/
		
		
		
		if (onShowSelectedItemNum + 1 >= 0) {
			g.setClip(x + ui.offsetX, y+ui.offsetY + (onShowSelectedItemNum + 1) * itemHeight- offsetY, width, itemHeight);
			/*for(int i=0;i<width/UIManager.connect_bg.getWidth()+1;i++)
			{
				if(focus){					
				g.drawImage(UIManager.connect_bg, x + ui.offsetX+i*UIManager.connect_bg.getWidth(), y+ui.offsetY + (onShowSelectedItemNum + 1) * itemHeight- offsetY, 0);
				}
				
			}*/
			if(focus){
				ImageDiv.drawJiuGong(g, UIManager.connect_bg, x + ui.offsetX,  y+ui.offsetY + (onShowSelectedItemNum + 1) * itemHeight- offsetY, width, itemHeight);
			}
			g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
			for(int i=0;i<mainItem.size();i++)
			{
				g.setColor(211, 226, 231);
				g.drawLine(x + ui.offsetX, y+ui.offsetY + i * itemHeight- offsetY+itemHeight-2, x + ui.offsetX+width, y+ui.offsetY + i * itemHeight- offsetY+itemHeight-2);			
//			g.drawImage(UIManager.longline, width/2, y+ui.offsetY + i * itemHeight- offsetY+itemHeight, Graphics.TOP|Graphics.HCENTER);
			}
		}
		// paint other items
		int clipx = g.getClipX();
		int clipy = g.getClipY();
		int clipwidth = g.getClipWidth();
		int clipheight = g.getClipHeight();
		g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
		g.setColor(0);
		g.setFont(smallFont);
		int length = mainItem.size();
		for (int i = 0; i < length; i++) {
			if(i==this.onShowSelectedItemNum+1 && focus){
//				g.drawImage(UIManager.triangle_color, width-25, y + ui.offsetY	+ i * itemHeight - offsetY+(itemHeight>>1)-UIManager.triangle_color.getHeight()/2 ,Graphics.LEFT|Graphics.TOP);
				g.setColor(255, 255, 255);
			}else{
//				g.drawImage(UIManager.triangle_gray, width-25, y + ui.offsetY	+ i * itemHeight - offsetY+(itemHeight>>1)-UIManager.triangle_gray.getHeight()/2  ,Graphics.LEFT|Graphics.TOP);
				g.setColor(0);
			}
			if (layoutStyle==1) {
				int tempx=0;
				int tempy=0;
				if(bigImage!= null){
					
					tempx=45;
					ImageBuilder.drawSmallImage(g, bigImage, x + ui.offsetX + 2,y+ ui.offsetY + i * itemHeight - offsetY+3, 35, 35, 35*i, 0);
				}else{
					tempx=0;
				}
				if(secondaryItem.elementAt(i).toString().equals("") || secondaryItem.elementAt(i).toString()==null){
					if(bigImage!=null)
						tempy=smallFont.getHeight()>>1;
					 else
						 tempy=0;
				}else{
					tempy=0;
				}
				g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
				g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5+ tempx, y + ui.offsetY+tempy	+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
			
			}else	if(layoutStyle==2){
				
					if(UIManager.select_block!=null){	
						g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
						int style2Y=y+ ui.offsetY + i * itemHeight+(itemHeight-UIManager.select_block.getHeight())/2-offsetY ;
							
							ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX + 5,style2Y, 18, 15, 18*(stateArray[i]==0?1:0), 0);
//						g.setColor(colorArray[i]);
						g.setClip(x + ui.offsetX, y + ui.offsetY, width-smallFont.stringWidth(secondaryItem.elementAt(i).toString())-15, height);
						if((onShowSelectedItemNum+1==i)&&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())>width-15){
							g.drawString(strTemp, x + ui.offsetX + 5+20, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
							
						}else{
							
							g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5+20, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
						}
						
					}
					g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
					g.setFont(smallFont);
					g.drawString(secondaryItem.elementAt(i).toString(), ui.xmlForm.frm_Width-15-smallFont.stringWidth(secondaryItem.elementAt(i).toString()), y + ui.offsetY	+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
				}else if(layoutStyle==3){
					if(bigImage!=null){						
						int style3Y=y+ ui.offsetY + i * itemHeight+(itemHeight-bigImage.getHeight())/2 ;
//						int topY=UIManager.top_bg.getHeight()+UIManager.address_bg.getHeight();
//							g.drawImage(bigImage, x + ui.offsetX + 5+(bigImage.getWidth()>>1), style3Y, g.TOP|g.HCENTER);
						ImageBuilder.drawSmallImage(g, UIManager.file, x + ui.offsetX + 5,style3Y, 27, 24, 0, 0);
					}
					int tempx=0;
					int tempy=0;
					if(bigImage==null){
						tempx=10;
					}else{
						tempx=bigImage.getWidth()/3+10;
					}
					if(secondaryItem.elementAt(i).toString().length()>0){
						tempy=0;
					}else{
						tempy=smallFont.getHeight()>>1;
					}
					if ((onShowSelectedItemNum + 1 == i) &&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum + 1).toString()) >( width-(bigImage!=null?bigImage.getWidth()/3:0)-5)) {
						g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
						g.setFont(smallFont);
						g.drawString(strTemp, x + ui.offsetX + 5+ tempx, y + ui.offsetY	+ i * itemHeight - offsetY+tempy+5, Graphics.LEFT| Graphics.TOP);	
					}else
					{
						g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
						g.setFont(smallFont);
						g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5+ tempx, y + ui.offsetY+ i * itemHeight - offsetY+tempy+5, Graphics.LEFT| Graphics.TOP);
					}
					int secondaryX= x + ui.offsetX + 5	+tempx+2+UIManager.unreaded.getWidth();
					g.setFont(smallFont);
					g.drawString(secondaryItem.elementAt(i).toString(), secondaryX, y+ ui.offsetY + i * itemHeight+ smallFont.getHeight() - offsetY+5,Graphics.LEFT | Graphics.TOP);
					
				}else if(layoutStyle==4){
					
					if(UIManager.select_block!=null){	
						g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
						g.setFont(smallFont);
						g.setColor(colorArray[i]);
						g.drawString(secondaryItem.elementAt(i).toString(), x + ui.offsetX + 5, y + ui.offsetY	+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
						g.setClip(x + ui.offsetX, y + ui.offsetY, width, height);
						int style2Y=y+ ui.offsetY + i * itemHeight+(itemHeight-UIManager.select_block.getHeight())/2 - offsetY;
							
							ImageBuilder.drawSmallImage(g, UIManager.select_block, x + ui.offsetX + 10+smallFont.stringWidth(secondaryItem.elementAt(i).toString()),style2Y, 18, 15, 18*(stateArray[i]==0?1:0), 0);
						/*if(bigImage!=null){
							
							g.drawImage(bigImage, x+ui.offsetX+5+13+3, style2Y, g.TOP|g.HCENTER);
						}*/
						g.setColor(colorArray[i]);
						g.setClip(x + ui.offsetX, y + ui.offsetY, width-5, height);
						if((onShowSelectedItemNum+1==i)&&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())>width){
							g.drawString(strTemp, x + ui.offsetX + 10+smallFont.stringWidth(secondaryItem.elementAt(i).toString())+30, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);

						}else{
							
							g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 10+smallFont.stringWidth(secondaryItem.elementAt(i).toString())+30, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
						}
						
					}
				}else if(layoutStyle==5){
						g.setFont(smallFont);
						g.setColor(ColorAttribute.label_context);
						
						g.setClip(x + ui.offsetX, y + ui.offsetY, width-smallFont.stringWidth(secondaryItem.elementAt(i).toString())-35, height);
						if((onShowSelectedItemNum+1==i)&&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())>width-smallFont.stringWidth(secondaryItem.elementAt(i).toString())){
							g.drawString(strTemp, x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);

						}else{
							
							g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
						}
						
				
					g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
					
					g.drawString(secondaryItem.elementAt(i).toString(), ui.xmlForm.frm_Width-35-smallFont.stringWidth(secondaryItem.elementAt(i).toString()), y + ui.offsetY	+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
				  /******************通讯录模式**********************/
				  }else if(layoutStyle==6)
				  {  
					 if(i==0)
					 {
					     g.setFont(smallHeavryFont);
//					     g.setColor(Theme.getTheme().getColor(3));
					}else
					{
						g.setFont(smallFont);
//						g.setColor(colorArray[i]);
					}
				    
					g.setClip(x + ui.offsetX, y + ui.offsetY, width-smallFont.stringWidth(secondaryItem.elementAt(i).toString())-15, height);
					if((onShowSelectedItemNum+1==i)&&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())>width-smallFont.stringWidth(secondaryItem.elementAt(i).toString())){
						 g.drawString(strTemp, x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
                 	}else{
						g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
					}
				    g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
				    g.drawString(secondaryItem.elementAt(i).toString(), ui.xmlForm.frm_Width-15-smallFont.stringWidth(secondaryItem.elementAt(i).toString()), y + ui.offsetY	+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
	           
			    }
		       /***********************巡店输入排面*****************/	
				  else if(layoutStyle==7){
					  g.setFont(smallFont);
//						g.setColor(colorArray[i]);
						g.setClip(x + ui.offsetX, y + ui.offsetY, width-45, height);
						if((onShowSelectedItemNum+1==i)&&smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())>width-30){
							g.drawString(strTemp, x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);

						}else{
							
							g.drawString(mainItem.elementAt(i).toString(), x + ui.offsetX + 5, y + ui.offsetY+ i * itemHeight - offsetY, Graphics.LEFT| Graphics.TOP);
						}
						
				
					g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
					int dx= ui.xmlForm.frm_Width-45;
					int dy= y + ui.offsetY	+ i * itemHeight - offsetY;
					g.setColor(255,255,255);
					g.fillRect(dx-2, dy+2, 30, smallFont.getHeight()+5);
					g.setColor(0);
					g.drawRect(dx-2, dy+2, 30, smallFont.getHeight()+5);
					g.setClip(dx-2, dy+2, 30, height);
					g.drawString(secondaryItem.elementAt(i).toString(), dx,dy+5, Graphics.LEFT| Graphics.TOP);
					g.setClip(x+ui.offsetX, y+ui.offsetY, width, height);
				  }
		}

		int scrollH=itemHeight+5;

		int dh=((height-scrollH)/(length>0?length:length+1));
		if(((height-scrollH)%(length>0?length:length+1))>length/2){
			dh++;
		}
		int dy=(dh)*(onShowSelectedItemNum+1);
		if((onShowSelectedItemNum+1)==length-1){
			int mistake=height-dy-dh-scrollH;//误差
			if(mistake!=0)
				dy=dy+mistake;
			
		}
		
		g.setClip(0, 0,ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);		
		g.setColor(124,124,124);
		g.drawRect( x + ui.offsetX + width,y+ ui.offsetY,5,height);		
		g.setColor(99, 99, 99);
		g.fillRect( x + ui.offsetX + width+1, y+ ui.offsetY+1, 4,height-1);
		g.setColor(124,124,124);	
		g.drawRect(x+ui.offsetX+width, y+ui.offsetY+dy-1, 4, scrollH+1+dh);
		g.setColor(253, 253, 253);	
		g.fillRect( x + ui.offsetX + width+1, y+ ui.offsetY + dy, 4, scrollH+dh);
		g.setClip(clipx, clipy, clipwidth, clipheight);
		

	}

	public void drawFocus(Graphics g) {

	}

	public void setSelectedItem(int itemNum) {
		onShowSelectedItemNum = itemNum - 1;
	}
	public void addItem1(String item) {
		mainItem.addElement(item);
		//imgs_str.addElement(null);
		imgs.addElement(null);
		itemSum = mainItem.size();
		if(!startTimer)
		{
			int fonW = smallFont.stringWidth(item)+(bigImage!=null?bigImage.getWidth():0);
			if(fonW>width-5)
			{
				startTimer=true;
			}
		}
	}
	public void addItem2(String item1, String item2) {
		mainItem.addElement(item1);
		secondaryItem.addElement(item2);
		//imgs_str.addElement(null);
		imgs.addElement(null);
		itemSum = mainItem.size();
		if(!startTimer)
		{
			if(layoutStyle==2 || layoutStyle==4 ||layoutStyle==5){
				strTemp=mainItem.elementAt(0).toString();
				if(layoutStyle==5)
				{
					int fonW = smallFont.stringWidth(item1+item2);
					if(fonW>width-5)
					{
						startTimer=true;
					}
				}else
				{
					int fonW = smallFont.stringWidth(item1)+(bigImage!=null?bigImage.getWidth():0);
					if(fonW>width-5)
					{
						startTimer=true;
					}	
				}
			}else{
				strTemp = mainItem.elementAt(0).toString();
				int fonW = smallFont.stringWidth(item1)+(bigImage!=null?bigImage.getWidth():0);
				if(fonW>width-5)
				{
					startTimer=true;
				}	
			}
		}
		colorArray=new int[itemSum];
		stateArray=new int[itemSum];
	}

	public void addItem(String item, Image img) {
		mainItem.addElement(item);
		//imgs_str.addElement(img);
		imgs.addElement(img);
		itemSum = mainItem.size();
		if(!startTimer)
		{
			int fonW = smallFont.stringWidth(item)+(bigImage!=null?bigImage.getWidth():0);
			if(fonW>width-5)
			{
				startTimer=true;
			}
		}
	}
	
	public void addItem(String item1, String item2, Image img) {
		
		mainItem.addElement(item1);
		secondaryItem.addElement(item2);
		//imgs_str.addElement(img);
		imgs.addElement(img);
		itemSum = mainItem.size();
		strTemp = mainItem.elementAt(0).toString();
		if(!startTimer)
		{
			if(layoutStyle==5||layoutStyle==2||layoutStyle==4)
			{   if(layoutStyle==5)
			    {
					int fonW = smallFont.stringWidth(item1+item2);
					if(fonW>width-5)
					{
						startTimer=true;
					}
			    }else
				{   int fonW = smallFont.stringWidth(item1)+(bigImage!=null?bigImage.getWidth():0);
					if(fonW>width-5)
					{
						startTimer=true;
					}
			    }
			}else 
			{
				int fonW = smallFont.stringWidth(item1)+(bigImage!=null?bigImage.getWidth():0);
				if(fonW>width-5)
				{
					startTimer=true;
				}
			}
		}
	}
	public void setListData(Vector data) {
		mainItem = data;
		for (int i = 0; i < data.size(); i++) {
			//imgs_str.addElement(null);
			imgs.addElement(null);
		}
		itemSum = mainItem.size();
	}

	public void setListData(Vector data1, Vector data2) {
		mainItem = data1;
		secondaryItem = data2;
		for (int i = 0; i < data1.size(); i++) {
			//imgs_str.addElement(null);
			imgs.addElement(null);
		}
		itemSum = mainItem.size();
	}
	
	public void setListData(Vector data1,Vector data2,Vector data3)
	{
		mainItem = data1;
		secondaryItem = data2;
		imgs= data3;
		itemSum = mainItem.size();
	}

	public boolean removeItem1(int index) {
		try {
			mainItem.removeElementAt(index);
			//imgs_str.removeElementAt(index);
			imgs.removeElementAt(index);
			itemSum = mainItem.size();
			// initItemImg();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeItem2(int index) {
		try {
			mainItem.removeElementAt(index);
			secondaryItem.removeElementAt(index);
			//imgs_str.removeElementAt(index);
			imgs.removeElementAt(index);
			itemSum = mainItem.size();
			// initItemImg();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeAllItem() {
		try {
			mainItem.removeAllElements();
			secondaryItem.removeAllElements();
			itemSum = 0;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void initItemImg() {
		try {
			/*imgs.removeAllElements();
			for (int i = 0; i < itemSum; i++) {
				if (imgs_str != null) {
					Image img = Image.createImage(imgs_str.elementAt(i)
							.toString());
					imgs.addElement(img);
				} else {
					imgs.addElement(null);
				}
			}*/
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 触屏向上
	 *
	 */
	public void pointerUp(){
		if(onShowSelectedItemNum + 1-5>0){
			this.setSelectedItem(onShowSelectedItemNum + 1-5);
		}else if(onShowSelectedItemNum + 1==0){
			this.setSelectedItem(itemSum-1);
		}else{
			this.setSelectedItem(0);
			
		}
	}
	/**\
	 * 触屏向下滚动
	 *
	 */
	public void pointerDown(){
		if(onShowSelectedItemNum + 1+5<itemSum){
			this.setSelectedItem(onShowSelectedItemNum + 1+5);
		}else if(onShowSelectedItemNum + 1==itemSum-1){
			this.setSelectedItem(0);
		}else{
			this.setSelectedItem(itemSum-1);
			
		}
	}
	private int getPointIndex(int x,int y){
		for(int i=0;i<itemSum;i++){
			int y1=this.y+ui.offsetY + i * itemHeight- offsetY;
			if(y>=y1  && y<=y1+itemHeight){
				return i-1;
			}
		}
		
		return 0;
	}

	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}
	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		onShowSelectedItemNum=getPointIndex(x, y);
		ui.xmlForm.repaint();
		itemStateChanged(x,y);
//		super.pointerPressed(x, y);
	}
	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}
	public void keyPressed(int keyCode) {	
		if(this.isPass()){
			return;
		}
		if (!ui.selector.inComboItem) {
			ui.selector.inComboItem = true;
		} else {
			if(keyCode>=48 && keyCode<=57){
				if(layoutStyle==7){
					
					String text=secondaryItem.elementAt(onShowSelectedItemNum + 1).toString();
					secondaryItem.setElementAt(text+(keyCode-48), onShowSelectedItemNum + 1);			
					text=null;
				}
			}else if(keyCode==-8 || keyCode==42){
				if(layoutStyle==7){
					
					if(secondaryItem.elementAt(onShowSelectedItemNum + 1).toString().length()>0){
						String text=secondaryItem.elementAt(onShowSelectedItemNum + 1).toString();
						
						text=text.substring(0,text.length()-1);
						secondaryItem.setElementAt(text, onShowSelectedItemNum + 1);
						text=null;
					}
				}
			}else	if (keyCode == Key.UPKEY(keyCode)) {
				if (onShowSelectedItemNum == -1)
					{
					if(layoutStyle==3){
						focus=false;
						ui.selector.inComboItem = false;
						if(ui.selector.selected>0)
							ui.selector.selected--;
						(ui.getItem(ui.selector.selected)).focus=true;
						ui.selector.Tab=true;
					}else{
						onShowSelectedItemNum=itemSum - 2;
					}
					}
				else if (onShowSelectedItemNum > -1) {
					onShowSelectedItemNum--;
				}
				if(layoutStyle!=0) {
					if(layoutStyle==2|| layoutStyle==4||layoutStyle==5){
						if (smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum + 1).toString()) > width- 35-smallFont.stringWidth(secondaryItem.elementAt(onShowSelectedItemNum + 1).toString())) 
						{				
							strTemp="";
							strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
							times = 0;
						}
					}else{
						if (smallFont.stringWidth(mainItem.elementAt(
								onShowSelectedItemNum + 1).toString()) > ( width-(bigImage!=null?bigImage.getWidth():0)-5)) {
							strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
							times = 0;
						}
					}
					
				}
			} else if (keyCode == Key.DOWNKEY(keyCode)) {
				if (onShowSelectedItemNum < (itemSum - 2)) {
					onShowSelectedItemNum++;
				}else{
					onShowSelectedItemNum=-1;
					
				}
				if(layoutStyle!=0) {
					if(layoutStyle==2|| layoutStyle==4||layoutStyle==5){
						if (smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum + 1).toString()) >width- 35-smallFont.stringWidth(secondaryItem.elementAt(onShowSelectedItemNum + 1).toString())) 
						{
							strTemp="";
							strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
							times = 0;
						}
					}else{
						if (smallFont.stringWidth(mainItem.elementAt(
								onShowSelectedItemNum + 1).toString()) > ( width-(bigImage!=null?bigImage.getWidth():0)-5)) {
							strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
							times = 0;
						}
					}
					
				}
			} else if ((keyCode == Key.LEFTKEY(keyCode))) {
				if(onShowSelectedItemNum + 1-5>0){
					this.setSelectedItem(onShowSelectedItemNum + 1-5);
				}else if(onShowSelectedItemNum + 1==0){
					this.setSelectedItem(itemSum-1);
				}else{
					this.setSelectedItem(0);
					
				}
			}else if((keyCode == Key.RIGHTKEY(keyCode))){
				if(onShowSelectedItemNum + 1+5<itemSum){
					this.setSelectedItem(onShowSelectedItemNum + 1+5);
				}else if(onShowSelectedItemNum + 1==itemSum-1){
					this.setSelectedItem(0);
				}else{
					this.setSelectedItem(itemSum-1);
					
				}
				
			}else if(keyCode == Key.FIREKEY(keyCode))
			{	
				if(itemSum>0)
				if(layoutStyle==2 || layoutStyle==4){
					
					if(stateArray[onShowSelectedItemNum+1]==1){
						
						stateArray[onShowSelectedItemNum+1]=0;
					}else{
						stateArray[onShowSelectedItemNum+1]=1;
					}
				}
				itemStateChanged(keyCode);
			}
		}
		super.keyPressed(keyCode);
	}
	
	/*public void addItemListener(ItemStateListener e) {
		listenerList.add(e);
	}
	public void itemStateChanged(int keyCode){
		ItemStateListener item = this.listenerList.getItemStateListener();
		if (item!= null) {
			ItemEvent e = new ItemEvent(this, keyCode);
			item.itemStateChanged(e);
		}
	}*/

	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
		
	}

	public void initStateArray(){
		if(itemSum>0)
		if(layoutStyle==2 ||layoutStyle==4){
			stateArray=new int[itemSum];
			for(int i=0;i<itemSum;i++){
				stateArray[i]=0;//初始0，为不选中
			}
		}
	}
	public void initColorArray(){
		if(itemSum>0)
		if(layoutStyle==2 ||layoutStyle==4){
			colorArray=new int[itemSum];
			for(int i=0;i<itemSum;i++){
				colorArray[i]=0xffffff;//初始为白色
			}
		}
	}
	public int getColor(int index){
		return colorArray[index];
	}
	public void setColor(int index, int color){
		if(index<colorArray.length)
		colorArray[index]=color;
	}
	public void setSelectedIndex(int index, int selected){
		if(index<stateArray.length)
			stateArray[index]=selected;
	}
	
	public void setListText(int index, String str1, String str2)
	{
		if(index<mainItem.size())
		{
			mainItem.setElementAt(str1, index);
			secondaryItem.setElementAt(str2, index);
		}
	}
	public int[] getStateArrary(){
		return stateArray;
	}
	/**
	 * 返回选择的状态
	 * @return
	 */
	public int[] getSelectedIndex(){
		int[] index=null;
		Vector v=new Vector();
		for(int i=0;i<itemSum;i++){
		  if(stateArray[i]==1){
			  v.addElement(Integer.toString(i));
			  
		  }
		}
		if(v.size()==0){
			return null;
		}
		index=new int[v.size()];
		for(int i=0;i<v.size();i++){
			index[i]=Integer.parseInt(v.elementAt(i).toString());
		}
		v=null;

		return index;
	}
	/*public int getSumIndex(){
	
	}*/
	
	public String getSelectedItem() {
		return mainItem.elementAt(onShowSelectedItemNum + 1).toString();
	}
//	StringBuffer sb=new StringBuffer();
	class rollString extends TimerTask {
		public void run() {
//    	System.out.println("正在绘制");	
		if(layoutStyle==5||layoutStyle==2|| layoutStyle==4){
				if(smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum+1).toString())> width-smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum + 1).toString())-45)
				{
					if (times >= strTemp.length() + 4) {
						strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
						times=0;
					}
					if (times < strTemp.length() + 6) {
						strTemp = strTemp.substring(1, strTemp.length())+ strTemp.substring(0, 1);
						times++;
					}
					ui.xmlForm.repaint();
				}
		}else if (smallFont.stringWidth(mainItem.elementAt(onShowSelectedItemNum + 1).toString()) >( width-(bigImage!=null?bigImage.getWidth():0)-5)) {
			    if (times < strTemp.length() + 6) {
					times++;
				}
				if ((times > 4) && (times < strTemp.length() + 4)) {
					strTemp = strTemp.substring(1, strTemp.length())
							+ strTemp.substring(0, 1);
				}
				if (times >= strTemp.length() + 4) {
					strTemp = mainItem.elementAt(onShowSelectedItemNum + 1).toString();
				}
				ui.xmlForm.repaint();
		  }
	  }
	}
    /************************通讯录模式************************/
	private Vector threeItem=null;
	public void addItem3(String item1, String item2,String item3)
	{    addItem2(item1,item2);
		
		if(threeItem==null)
		 threeItem=new Vector();
	   
		threeItem.addElement(item3);
	}
	public String getThreeItem()
	{
		if(threeItem!=null&&threeItem.size()>=onShowSelectedItemNum + 1)
		{
			return threeItem.elementAt(onShowSelectedItemNum + 1)!=null?threeItem.elementAt(onShowSelectedItemNum + 1).toString():"";
		}
		return "";
	}
	public  boolean removeAllItem3()
	{  
		try{
			   mainItem.removeAllElements();
			   secondaryItem.removeAllElements();
			   if(threeItem!=null){
			   threeItem.removeAllElements();
			   }
			   itemSum = 0;
			   onShowSelectedItemNum=0;
			   return true;
		  }catch(Exception e)
		  {
			  return false;
		  }
	}
	/***
	 * 获取第1列的第3item个值
	 * @return (String)threeItem.elementAt(0);
	 */
	public String getFristItem3Value()
	{
		return (String)threeItem.elementAt(0);
	}
	/***
	 * 获取第1列的第1item个值
	 * @return   (String)mainItem.elementAt(0);
	 */
	public String getFristItem1Value()
	{
		return (String)mainItem.elementAt(0);
	}
	private String ID;
	/**************************************************/
	public void setID(String controlID) {
		// TODO 自动生成方法存根
		ID=controlID;
	}
	public String getID(){
		return ID;
	}
	
	
	
	

}
