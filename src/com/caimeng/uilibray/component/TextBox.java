package com.caimeng.uilibray.component;


import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.Input;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageDiv;


public class TextBox extends Input {

	public TextBox() {
		super();
	}

	public char passShow = '*';

	private String title = null;
    /**
     * 0为普通文本模式 1为密码模式 2为全显示模式 3为搜索栏模式 4为不可修改模式
     */
	private int inputstyle;// 0为普通文本模式 1为密码模式 2为全显示模式 3为搜索栏模式 4为不可修改模式
	
	public int titleStyle;//0为左右分布 1为上下分布

	private String ID;

	public boolean flag=false;
	
	
	
		
	public TextBox(int x, int y, int width, int height) {
		super(x, y, width, height);
//		fgTricolorR = 1;
//		fgTricolorG = 93;
//		fgTricolorB = 156;
		int charsHeight = smallHeavryFont.getHeight();
		if (charsHeight > this.height) {
			this.height = charsHeight + 5;
		}
		this.style=super.isTextBox;
	}

	public void setStyle(int style) {
		this.inputstyle = style;
		if(style==4)iswriteStyle=false;//不可修改模式
	}

	
	public int getENG(String str){
		int num=0;
		char c;
		 for (int i = 0; i < str.length(); i++) {
             c = str.charAt(i);
             if ((c > '0' && c< '9')|| (c > 'a' && c < 'z') || (c >'A' && c < 'Z')) {
            	 
            	 num++;
             }
		 }
		return num;
	}

	public static int[ ]  date(String str){
		int year=0;
		int month=0;
		int day=0;
		int pos=4;
		int p=str.indexOf(" ");
		if(p==9 ||p==8)
		str=str.substring(0, p);
		
		if(str.length()==10 && str.substring(pos, pos+1).equals("-") && str.substring(pos+3, pos+4).equals("-")){
			int temp=Integer.parseInt(str.substring(0, pos));
			if(temp>1970 && temp<2009 ){
				year=temp;
				System.out.println("year=="+year);
			}
			temp=Integer.parseInt(str.substring(pos+1, pos+3));
			if(temp>0 && temp<=12){
				month=temp;
				System.out.println("month=="+month);
			}
			temp=Integer.parseInt(str.substring(pos+4, pos+6));
			if(temp>0 && temp<=31){
				day=temp;
				System.out.println("day=="+day);
			}
		}else if(str.length()==9 && str.substring(pos, pos+1).equals("-") && str.substring(pos+2, pos+3).equals("-")){
			int temp=Integer.parseInt(str.substring(0, pos));
			if(temp>1970 && temp<2009 ){
				year=temp;
				System.out.println("year=="+year);
			}
			temp=Integer.parseInt("0"+str.substring(pos+1, pos+2));
			if(temp>0 && temp<=9){
				month=temp;
				System.out.println("month=="+month);
			}
			temp=Integer.parseInt(str.substring(pos+3, pos+5));
			if(temp>0 && temp<=31){
				day=temp;
				System.out.println("day=="+day);
			}
		}else if(str.length()==9 && str.substring(pos, pos+1).equals("-")  && str.substring(pos+3, pos+4).equals("-")){
			int temp=Integer.parseInt(str.substring(0, pos));
			if(temp>1970 && temp<2009 ){
				year=temp;
				System.out.println("year=="+year);
			}
			temp=Integer.parseInt(str.substring(pos+1, pos+3));
			if(temp>0 && temp<=12){
				month=temp;
				System.out.println("month=="+month);
			}
			temp=Integer.parseInt(str.substring(pos+4, pos+5));
			if(temp>0 && temp<=9){
				day=temp;
				System.out.println("day=="+day);
			}
		}else if(str.length()==8 && str.substring(pos, pos+1).equals("-") && str.substring(pos+2, pos+3).equals("-")){
			int temp=Integer.parseInt(str.substring(0, pos));
			if(temp>1970 && temp<2009 ){
				year=temp;
				System.out.println("year=="+year);
			}
			temp=Integer.parseInt("0"+str.substring(pos+1, pos+2));
			if(temp>0 && temp<=9){
				month=temp;
				System.out.println("month=="+month);
			}
			temp=Integer.parseInt(str.substring(pos+3, pos+4));
			if(temp>0 && temp<=9){
				day=temp;
				System.out.println("day=="+day);
			}
		}
		int[] date={year,month,day};
		return date;
	}
	

	public void paint(Graphics g) {
		int posX = 0;
		int posY = 0;
		int num=0;
//		g.setColor(this.fgTricolorR,this.fgTricolorG,this.fgTricolorB);
		posX = x + ui.offsetX;
		posY = y + ui.offsetY;
		/*if(focus){
			if(posY+height>ui.xWForm.frm_Height-30){
				ui.offsetY=ui.offsetY-(height+30);
				ui.xWForm.repaint();
			}
		}*/
		g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);		
		if (title != null) {
			g.setFont(smallHeavryFont);
			if(focus){
				g.setColor(52, 52, 52);
			}else{
				g.setColor(102, 102, 102);
			}
			if(titleStyle==0)
			{
				int length=title.length();
				num=getENG(title);//得到英文字符个数
//				System.out.println("lenght"+length);
				if(num>0){
					length=length-num/2;
				}
				if(length<5){
					if(!flag)
						posX = x + ui.offsetX+smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth("用")*4;
					else
						posX = x + ui.offsetX+smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth(title);
				}else {
					posX = x + ui.offsetX+smallHeavryFont.stringWidth(":")+smallHeavryFont.stringWidth(title);
				}
				
				g.drawString(title + ":",x+ui.offsetX, y + ui.offsetY+3, g.TOP|g.LEFT);
			}
			else if(titleStyle==1)
				{				
					posY = y + ui.offsetY-smallFont.getHeight();
					if(!"".equals(title))
					g.drawString(title + ":", x + ui.offsetX, posY+3, 0);
				
				}
		}
		
		g.setFont(smallFont);
		if (inputstyle == 0)
		// 画背景
		{
			
			if (focus) {
			/*g.setColor(204, 204, 204);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, smallFont.getHeight()+5+2);				
				g.setColor(63	, 155, 255);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_selected, posX, y + ui.offsetY, width, UIManager.txt_selected.getHeight());
				
				
				g.setColor(255	, 255, 255);
//				g.drawString(text, posX + 6, posY + height-3, Graphics.LEFT	| Graphics.BOTTOM);
			} else {
				/*g.setColor(204, 204, 204);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.setColor(255, 255, 255);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);
				g.setClip(posX+1,  y + ui.offsetY+1, width-8, smallFont.getHeight()+5-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_unselect, posX, y + ui.offsetY, width, UIManager.txt_unselect.getHeight());
				g.setColor(102, 102, 102);
			}
			g.setClip(posX+1,  y + ui.offsetY+1, width-8, smallFont.getHeight()+5-1);
			g.drawString(text, posX + 6, posY + height-1, Graphics.LEFT	| Graphics.BOTTOM);
			
			
				super.setCoordnate(posX+6, posY);
				g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
		} else if (inputstyle == 1) {
			if (focus) {
				/*g.setColor(204, 204, 204);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, smallFont.getHeight()+5+2);				
				g.setColor(63	, 155, 255);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);
				g.setClip(posX+1,  y + ui.offsetY+1, width-8, smallFont.getHeight()+5-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_selected, posX, y + ui.offsetY, width, UIManager.txt_selected.getHeight());
			} else {
			/*	g.setColor(204, 204, 204);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.setColor(255, 255, 255);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);
				g.setClip(posX+1,  y + ui.offsetY+1, width-8, smallFont.getHeight()+5-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_unselect, posX, y + ui.offsetY, width, UIManager.txt_unselect.getHeight());
			}
			// 边框（可有可无）
			// 画label 缩减5像素
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
			
			if(focus){
				g.setColor(255	, 255, 255);
			}else{
				g.setColor(102, 102, 102);
			}
			g.setClip(posX+1,  y + ui.offsetY+1, width-8, smallFont.getHeight()+5-1);	
//			g.setFont(smallHeavryFont);
			int chrWidth = smallFont.charWidth(passShow);
			int length = text.length();
			int maxChars = (width - 10) / chrWidth;
			if (chrWidth * length > width) {
				for (int i = 0; i < maxChars; i++) {
					g.drawChar(passShow, posX + width - 6 - i * chrWidth, posY
							+ height - 3, Graphics.RIGHT | Graphics.BOTTOM);
				}
			} else {
				for (int i = 0; i < length; i++) {
					g.drawChar(passShow, posX + 6 + i * chrWidth, posY + height
							, Graphics.LEFT | Graphics.BOTTOM);
				}
			}
			super.setCoordnate(posX+6, posY);
			g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
		}else if(inputstyle==2)
		{
			if (focus) {
				/*g.setColor(204, 204, 204);
				g.drawRect(posX, y + ui.offsetY, width, height);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, height+2);
				g.setColor(63	, 155, 255);
				g.fillRect(posX+1, y + ui.offsetY+1, width-1, height-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_selected, posX, y + ui.offsetY, width, UIManager.txt_selected.getHeight());
			} else {
				
				ImageDiv.drawJiuGong(g, UIManager.txt_unselect, posX, y + ui.offsetY, width, UIManager.txt_unselect.getHeight());
			}
			// 画label 缩减5像素
			if(focus){
				g.setColor(255	, 255, 255);
			}else{
				g.setColor(102, 102, 102);
			}
			g.setFont(smallFont);
			int h=0;
			
			if(content!=null)
			for(int i=0;i<content.size();i++)
				{
				if ((h+smallFont.getHeight())<maxHeight) {
					g.drawString(content.elementAt(i).toString(), posX + 6,
							y + ui.offsetY + smallFont.getHeight() + i
									* smallFont.getHeight(), Graphics.LEFT
									| Graphics.BOTTOM);
					h += smallFont.getHeight();
				}				
				}
			if(content==null)
			g.drawString(text, posX + 6, y + ui.offsetY + height/2 - 3, Graphics.LEFT
					| Graphics.BOTTOM);
			super.setCoordnate(posX+6, posY);
			g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
			
		}else if(inputstyle==3)
		{
			ImageDiv.drawJiuGong(g, UIManager.search_bar_bg, posX, y + ui.offsetY, width, UIManager.search_bar_bg.getHeight()+1);
//			for(int i=0;i<width/UIManager.search_bar_bg.getWidth()+1;i++)
//			{
//		        g.drawImage(UIManager.search_bar_bg, x + ui.offsetX+i*UIManager.search_bar_bg.getWidth()-10, y+ui.offsetY , 0);
//			}
			if(focus)
			{
				g.drawImage(UIManager.search_2, width-UIManager.search_2.getWidth()-5,  y + ui.offsetY+2, 0);
			}
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
//			g.setFont(smallHeavryFont);
			if (smallFont.stringWidth(text) > width) {
				int length = text.length();
				int charsWidth = 0;
				for (int i = length - 1; i >= 0; i--) {
					charsWidth = charsWidth
							+ smallFont.charWidth(text.charAt(i));
					if (charsWidth < width - 10) {
						g.drawChar(text.charAt(i), posX + width - 6
								- charsWidth, posY + height - 3, Graphics.LEFT
								| Graphics.BOTTOM);
					} else {
						break;
					}
				}
			} else {
			
				g.drawString(text, posX + 6, posY + height-3, Graphics.LEFT
						| Graphics.BOTTOM);
				super.setCoordnate(posX+6, posY);
				g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
			} 
		}else if(inputstyle==4){
			if (focus) {
				/*g.setColor(0, 0,0);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, smallFont.getHeight()+5+2);
				g.setColor(241, 240, 232);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);*/
				ImageDiv.drawJiuGong(g, UIManager.txt_selected, posX, y + ui.offsetY, width, UIManager.txt_selected.getHeight());
			} else {
				
				ImageDiv.drawJiuGong(g, UIManager.txt_unselect, posX, y + ui.offsetY, width, UIManager.txt_unselect.getHeight());
			}
				/*g.setColor(0);
				g.drawRect(posX, y + ui.offsetY, width, smallFont.getHeight()+5);
				g.setColor(241, 240, 232);
				g.fillRect(posX+1,  y + ui.offsetY+1, width-1, smallFont.getHeight()+5-1);*/
			
			// 画label 缩减5像素
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
//			g.setFont(smallHeavryFont);
			if (smallFont.stringWidth(text) > width) {
				int length = text.length();
				int charsWidth = 0;
				for (int i = length - 1; i >= 0; i--) {
					charsWidth = charsWidth
							+ smallFont.charWidth(text.charAt(i));
					if (charsWidth < width - 10) {
						g.drawChar(text.charAt(i), posX + width - 6
								- charsWidth, posY + height - 3, Graphics.LEFT
								| Graphics.BOTTOM);
					} else {
						break;
					}
				}
			} else {
				g.drawString(text, posX + 6, posY + height-3, Graphics.LEFT
						| Graphics.BOTTOM);
				super.setCoordnate(posX+6, posY);
				g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
			} 
		}else if(inputstyle==5){
			if (focus) {
				/*ImageDiv.drawJiuGong(g, UIManager.menu_bright, posX, posY,
						width, height);*/
				g.setColor(204, 204, 204);
				//g.fillRect(posX+1, posY+1, width-1, height-1);
				//g.setColor(0);
				g.drawRect(posX, y + ui.offsetY, width, height);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, height+2);
				g.setColor(241, 240, 232);
				g.fillRect(posX+1, y + ui.offsetY+1, width-1, height-1);
			} else {
				/*ImageDiv.drawJiuGong(g, UIManager.text_normal, posX, posY,
						width, height);*/
				g.setColor(0);
				g.drawRect(posX, y + ui.offsetY, width, height);
				g.setColor(241, 240, 232);
				g.fillRect(posX+1, y + ui.offsetY+1, width-1, height-1);
			}
			// 画label 缩减5像素
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
			g.setFont(smallFont);
			int h=0;
			
			if(content!=null)
			for(int i=0;i<content.size();i++)
				{
				if ((h+smallFont.getHeight())<maxHeight) {
					g.drawString(content.elementAt(i).toString(), posX + 6,
							y + ui.offsetY + smallFont.getHeight() + i
									* smallFont.getHeight(), Graphics.LEFT
									| Graphics.BOTTOM);
					h += smallFont.getHeight();
				}				
				}
			if(content==null)
			g.drawString(text, posX + 6, y + ui.offsetY + height/2 - 3, Graphics.LEFT
					| Graphics.BOTTOM);
			super.setCoordnate(posX+6, posY);
			g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
		}else if(inputstyle==6)
		{

			if (focus) {
				/*ImageDiv.drawJiuGong(g, UIManager.menu_bright, posX, posY,
						width, height);*/
				g.setColor(0, 220, 247);
				//g.fillRect(posX+1, posY+1, width-1, height-1);
				//g.setColor(0);
				g.drawRect(posX, y + ui.offsetY, width, height);
				g.drawRect(posX-1, y + ui.offsetY-1, width+2, height+2);
				g.setColor(255, 255, 255);
				g.fillRect(posX+1, y + ui.offsetY+1, width-1, height-1);
			} else {
				/*ImageDiv.drawJiuGong(g, UIManager.text_normal, posX, posY,
						width, height);*/
				g.setColor(0);
				g.drawRect(posX, y + ui.offsetY, width, height);
				g.setColor(255, 255, 255);
				g.fillRect(posX+1, y + ui.offsetY+1, width-1, height-1);
			}
			// 画label 缩减5像素
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
			g.setFont(smallFont);
			int h=0;
			
			if(content!=null)
			for(int i=0;i<content.size();i++)
				{
				if ((h+smallFont.getHeight())<maxHeight) {
					g.drawString(content.elementAt(i).toString(), posX + 6,
							y + ui.offsetY + smallFont.getHeight() + i
									* smallFont.getHeight(), Graphics.LEFT
									| Graphics.BOTTOM);
					h += smallFont.getHeight();
				}				
				}
			if(content==null)
			g.drawString(text, posX + 6, y + ui.offsetY + height/2 - 3, Graphics.LEFT
					| Graphics.BOTTOM);
			super.setCoordnate(posX+6, posY);
			g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);	
		}
		
		super.paint(g);
	}

	
	public void keyPressed(int keyCode) {
		if(this.isPass()){
			return;
		}
		super.keyPressed(keyCode);
	}
	public void keyReleased(int keyCode) {
		
		super.keyReleased(keyCode);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;	
	}
	
	public int getTitleWidth(){
		int w=smallHeavryFont.stringWidth(title);
		return w;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
	public void setTitleStyle(int titleStyle) {
		this.titleStyle = titleStyle;
		if(titleStyle==0)
			width += smallHeavryFont.stringWidth(title + ":") + 5;
			else if(titleStyle==1)
				height+=smallHeavryFont.getHeight();
	}
	

}
