package com.caimeng.uilibray.component;

import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.control.UIControl;
import com.caimeng.uilibray.event.ActionListener;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.utils.StringEx;


public class Label extends BaseControl {



	private int alignment = 0;

	private String ID;
	
	private String label = "label";

	private Vector text = null;
		
	private int labelcolor=0;
	public int screenWidth;
	
	private int labelStyle=0;
	
	public Label() {
		super();
		this.setStyle(BaseControl.isLabel);
		this.setPass(true);
	}

	public Label(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setStyle(BaseControl.isLabel);
		this.setPass(true);
		screenWidth=CMForm.frm_Width-15;
		//bgTricolorR = Attribute.text_backR;
		//bgTricolorG = Attribute.text_backG;
		//bgTricolorB = Attribute.text_backB;
	}

	public Label(int x, int y) {
		super(x, y, 36, 22);
		this.setStyle(BaseControl.isLabel);
		this.setPass(true);
		screenWidth=CMForm.frm_Width-10;
		//bgTricolorR = Attribute.text_backR;
		//bgTricolorG = Attribute.text_backG;
		//bgTricolorB = Attribute.text_backB;
	}
	boolean isLink=false;
	/**
	 * 添加label事件
	 * @param label
	 */
	public void addLabelAction(){
		isLink=true;
		this.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				UIControl.getInstance().clickLabel(Label.this);
			}
			
		});
	}

	public void pointerDragged(int x, int y) {
		// TODO 自动生成方法存根
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO 自动生成方法存根
		focus=true;
		ui.xmlForm.repaint();
		super.pointerPressed(x, y);
	}

	public void pointerReleased(int x, int y) {
		// TODO 自动生成方法存根
		super.pointerReleased(x, y);
	}

	public void setLabel(String label) {
		this.label = label;
//		System.out.println("label"+label);
		int charsWidth = smallFont.stringWidth(label);
		if (charsWidth > width&&charsWidth<screenWidth) {
			width = charsWidth;
		}else if(charsWidth>screenWidth)
		{
			text = new Vector();
			text=StringEx.lineCast(label, screenWidth, smallFont);
			width=screenWidth-smallFont.stringWidth("1");
			
		}
		int charsHeight = smallFont.getHeight();
		
		if (charsHeight > height&&text==null) {
			height = charsHeight;
		}else{
			if(text!=null){
//				changeLine=true;
				label=null;
				for(int i=0;i<text.size();i++)
				{
					height+=charsHeight;
				}
				height-=charsHeight;
			}
			
		}
		
//		text=null;
		
	}
	boolean changeLine=false;
	public String getLabel(){
		return this.label;
	}
	public int getLabelWidth(){
		int w=smallHeavryFont.stringWidth(label);
		return w;
	}
	/*public void setBackColor(int br, int bg, int bb) {
		this.bgTricolorR = br;
		this.bgTricolorG = bg;
		this.bgTricolorB = bb;
	}

	public void setForeColor(int fr, int fg, int fb) {
		this.fgTricolorR = fr;
		this.fgTricolorG = fg;
		this.fgTricolorB = fb;
	}*/
	public void setLableColor(int color){
		labelcolor=color;
	}
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public void drawFocus(Graphics g) {

	}
	public void setlabelStyle(int style){
		this.labelStyle=style;
		if(style==1){
			
			setLableColor(3, 91, 165);
		}
	}
	/**
	 * 
	 * @return 0代表普通条目   1代表标题，类型
	 */
	public int getlabelStyle(){
		return this.labelStyle;
	}
	public void setLableColor(int red,int green,int blue)
	{
		int color =red << 16 | green << 8 | blue;
		labelcolor=color;
	}
	public void paint(Graphics g) {
		g.setClip(0, 0, ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
		g.setColor(labelcolor);
		// back
//		if (!notBgColor) {
//			g.fillRect(x + ui.offsetX, y + ui.offsetY, width, height);
//			g.setColor(255, 255, 255);
//		}
		// fore
		if(labelStyle==1){
			g.setFont(smallHeavryFont);
		}else{
			
			g.setFont(smallFont);
		}
		if(text==null)
		{
			if (focus) {
				if(labelStyle==0){
					
					g.setColor(ColorAttribute.label_selected_text_bg);
					g.fillRect( x + ui.offsetX, y + ui.offsetY, this.getLabelWidth(), smallFont.getHeight());
					g.setColor(ColorAttribute.label_selected_text_fg);
					g.setFont(smallFont);
				}else{
					g.setColor(ColorAttribute.label_selected_text_bg);
					g.fillRect( x + ui.offsetX, y + ui.offsetY, this.getLabelWidth(), smallFont.getHeight());
					g.setColor(ColorAttribute.label_selected_text_fg);
					g.setFont(smallHeavryFont);
				}
			}else{
				if(labelStyle==0){
					g.setColor(ColorAttribute.label_context);
				}else{
					g.setColor(ColorAttribute.label_title_text);
				}
				
			}
			
////			g.setColor(0);
//			if (alignment == LEFT_ALIGNMENT) {
				g.drawString(label, x + ui.offsetX, y + ui.offsetY, Graphics.LEFT| Graphics.TOP);
//			} else if (alignment == MIDDLE_ALIGNMENT) {
//				g.drawString(label, x + ui.offsetX + width / 2, y + ui.offsetY,
//						Graphics.HCENTER | Graphics.TOP);
//			} else if (alignment == RIGHT_ALIGNMENT) {
//				g.drawString(label, x + ui.offsetX + width, y + ui.offsetY,
//						Graphics.RIGHT | Graphics.TOP);
//			}else if(alignment==HyperLink)
//			{
//				g.setFont(hypeSmallFont);
//				g.drawString(label, x + ui.offsetX + width, y + ui.offsetY,
//						Graphics.RIGHT | Graphics.TOP);
//			}
//			if(targetParameter!=null)
//			if(isLink)
//			g.drawLine( x + ui.offsetX , y + ui.offsetY+smallFont.getHeight(), x + ui.offsetX+this.getLabelWidth()-5, y + ui.offsetY+smallFont.getHeight());
		}else 
		{

			for(int i=0;i<text.size();i++){		
				if(focus){
					
					if(isLink)//连接模式  //画线条
					{
						g.setColor(ColorAttribute.label_selected_text_bg);
						g.fillRect( x + ui.offsetX, y + ui.offsetY+smallFont.getHeight()*i, this.getLabelWidth(), smallFont.getHeight());
//					g.drawLine( x + ui.offsetX , y + ui.offsetY+smallFont.getHeight()*i+smallFont.getHeight(), x + ui.offsetX+smallFont.stringWidth(text.elementAt(i).toString()), y + ui.offsetY+smallFont.getHeight()*i+smallFont.getHeight());
					}
					g.setColor(ColorAttribute.label_selected_text_fg);
				}
//				AjustDrawString(g,label,width,x+ui.offsetX,y+ui.offsetY,smallFont.getHeight());
				g.drawString(text.elementAt(i).toString(), x + ui.offsetX, y + ui.offsetY+smallFont.getHeight()*i, Graphics.LEFT| Graphics.TOP);
			}
		}
		
		
		//if focus
		
	}

	public String getID() {
		return ID;
	}
      
	public void setID(String id) {
		ID = id;
	}

	public int getAlignment() {
		return alignment;
	}
	
	private  Object[]  targetParameter;
	
	private int type=0;
	public void setBookAndType(int type,Object obj){
		this.type=type;
		this.BookObj=obj;
	}
	public Object getBookObj(){
		return BookObj;
	}
	private Object BookObj=null;
	public int getType(){
		return this.type;
	}
	
/*//	功能: 计算需要换行的位置 
//	str:   需要显示的文字 
//	font： 文字的字体 
//	linewd： 每行的宽度限制 
//	返回          文本中需要换行的位置
	 public int ChangLine(String str, Font font, int linewd ) { 
	   int wd = 0;
	   char ch;
	   for (int i = 0; i < str.length(); i++) 
	   { 
	    ch = str.charAt(i);
	    if (ch == '\n')
	     return i + 1;
	                            
	   
	    wd += font.charWidth(ch);
	    if (wd > linewd)
	     return i;
	   }
	   return 0;
	} 

//	功能: 分行显示字符串
//	g:   当前显示的缓冲
//	strText: 显示的字符串
//	linewd: 每行的宽度限制
//	x,y:   字符串左上角显示的位置
//	yDis:         显示文字时每行间隔的距离

	 public void AjustDrawString( Graphics g, String strText,  int linewd, int x, int y, int yDis )
	{
	   String subStr;
	   int nPos; //需要换行的位置
	   while (true)
	   {
	    nPos = ChangLine(strText, g.getFont(), linewd );
	    if (nPos == 0)
	    {
	     g.drawString( strText, x, y, 0);
	     break;
	    }else
	    {
	    	
	     if (strText.charAt(nPos - 1) == '\n' ){
	    	 
//	    	 System.out.println("strTex="+strText.charAt(nPos-2));
	    	 if(strText.charAt(nPos - 2)==','){
	    		 
	    	 }else{
	    		 
	    		 subStr = strText.substring(0, nPos - 1);
	    		 g.drawString( subStr, x, y, 0);
	    		 strText = strText.substring(nPos, strText.length());
	    		 y = y + yDis;
	    	 }
	     } else{
	    	 
	    	 subStr = strText.substring(0, nPos);
	    	 g.drawString( subStr, x, y, 0);
	    	 strText = strText.substring(nPos, strText.length());
	    	 y = y + yDis;
	     }
	    }
	   }
	}*/


	
	
}
