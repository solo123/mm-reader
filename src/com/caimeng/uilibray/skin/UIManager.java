package com.caimeng.uilibray.skin;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.AbstructComponent;
import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.component.MessageBox;
import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.container.Selector;
import com.caimeng.uilibray.container.XMLForm;
import com.caimeng.uilibray.menu.ToolBar;
import com.caimeng.uilibray.utils.ImageBuilder;

public class UIManager {
	
	private int list = 0;

	public BaseControl[] container = null;// 2个一组实例队列

	// 作用区域(x.y.width.height)储存在实例中

	public Selector selector = null;

	//public int pointer = -1;

	public XMLForm xmlForm = null;// UIManager保留所有对象 包括Frame 其余对象保留UIManager

	public AbstructComponent em = null;
	
	//public RichTextForm rtf = null;

	public int offsetX = 0;

	public int offsetY = 0;
	
	public int width;
	
	public int height;

	public UIManager() {

	}

	public UIManager(XMLForm xmlForm) {
		this.xmlForm = xmlForm;
		selector = new Selector(this, false);
	}
	
	public void setSize(int width,int height)
	{
		this.width=width;
		this.height=height;
	}
  /*  public void setRichTextForm(RichTextForm rtf)
    {
    	this.rtf=rtf;
    }*/

	public UIManager(AbstructComponent em) {
		this.em = em;
		selector = new Selector(this, false);
	}

	public void addItem(BaseControl baseControl) {
		list++;
		BaseControl[] temp=container;
//		System.arraycopy(container, 0, arg2, arg3, arg4)
		container=null;
		container=new BaseControl[list];
		if(temp!=null){
			
			System.arraycopy(temp, 0, container, 0, temp.length);
			
		}
		container[list-1]=baseControl;
		
		// System.out.print("sum"+container.size());
	}

	/*public void setItem(int index, BaseControl baseControl) {
		container.setElementAt(baseControl, 2 * index);
	}*/

	public void removeAllItem() {
		list = 0;
//		container.removeAllElements();
		container=new BaseControl[list];
		selector.selected=0;
	}
	public void deleteItem(){
		Vector v=new Vector();
		v.addElement(container[0]);
		container=new BaseControl[1];
		container[0]=(BaseControl)v.elementAt(0);
		selector.selected=0;
		list = 1;
//		this.height=container[0].height;
	}

	public Selector getSelector() {
		return selector;
	}

	public int getItemSum() {
		return list;
	}

	public BaseControl getItem(int num) {
		try{
			if(num>-1)
			{
			if(num<container.length){				
				return container[num];
			}else{
				return new BaseControl();
			}
			}else return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public BaseControl getItem(int pointerx, int pointery) {
		BaseControl baseControl = null;
		try{
			
			int size = container.length, list = size - 1;
			while (list >= 0) {
				BaseControl tempItem = container[list];
				int x = tempItem.x;
				int y = tempItem.y;
				int width = tempItem.width;
				int height = tempItem.height;
				if ((pointerx > x) && (pointerx < (x + width)) && (pointery > y)
						&& (pointery < (y + height)) && tempItem.isVisible()) {// 作用范围
					baseControl = tempItem;
					break;
				}
				list = list - 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return baseControl;
	}

	public ToolBar toolBar = null;

	public void addMenuBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public ToolBar getMenuBar() {
		// TODO 自动生成方法存根
		return toolBar;
	}
	public MessageBox messageBox=null;
	public void addMessageBox(MessageBox messageBox){
		this.messageBox=messageBox;
	}
	public MessageBox getMessageBox(){
		if(messageBox!=null)
		return messageBox;
		else
			return null;
	}
	
	
	
	public int getList() {
		return list;
	}
	
	
	
	
	
	
	
	
	
	public static Image mainMenu=null;
	public static Image selected_bg=null;
	public static Image bar=null;
	public static Image loading=null;
	
	public static Image bottom_bg;

	public static Image btn_1;

	public static Image btn_2;

	public static Image connect_bg;

	public static Image information;

	public static Image login;

	public static Image menu_bg;

	public static Image menu_bright;

//	public static Image review;
	
	public static Image btn_bg;

	public static Image search_bar_bg;

	public static Image select_block;

	public static Image select_bright;

	public static Image top_ico_1;
//	
	public static Image top_bg;
	
	public static Image triangle;
	
	public static Image unreaded;
	
	public static Image review_list_triangle_right;
	
	public static Image review_list_triangle_left;	
	
    public static Image plus_icon;
    
    public static Image subtract_icon;
    
    public static Image browse_title_bg;
    
    public static Image search_2;
    
    public static Image tab_bg;    
    
    public static Image review_home;    
    
//    public static Image logo;
    
//    public static Image bottom_p;
    
    public static Image txt_selected;
    
    public static Image txt_unselect;
    
    public static Image msg_top;
    
    public static Image msg_bottom;
    
    public static Image line;
    
    public static Image file;
    
    public static Image longline;
    
//    public static Image graylogo;

    public static Image triangle_color;
    
    public static Image triangle_gray;
    
    public static Image vline;
	
    public static Image tab_bottom;
    
    public static Image tab_select_bg;
    
//    public static Image tab_bg;
    
    public static Image tab_brew;

    public static Image tab_ico;

	public static Image tab_selected;

	public static void loadSkin() {
		if(new CMForm().frm_Width>=240){
			//加载宽屏资源
//			review_home=ImageBuilder.loadImage("/review_home.png");
			msg_top=ImageBuilder.loadImage("/msg_top.png");
			msg_bottom=ImageBuilder.loadImage("/msg_bottom.png");
			information=ImageBuilder.loadImage("/information.png");
			tab_bg=ImageBuilder.loadImage("/tab_bg.png");
			tab_ico=ImageBuilder.loadImage("/tab_ico.png");
			tab_selected=ImageBuilder.loadImage("/tab_selected.png");
		}else{
//			加载中屏资源
//			review_home=ImageBuilder.loadImage("/screen2/home.png");
			msg_top=ImageBuilder.loadImage("/screen2/msg_top.png");
			msg_bottom=ImageBuilder.loadImage("/screen2/msg_bottom.png");
			information=ImageBuilder.loadImage("/screen2/information.png");
			tab_bg=ImageBuilder.loadImage("/screen2/tab_bg.png");
			tab_ico=ImageBuilder.loadImage("/screen2/tab_ico.png");
			tab_selected=ImageBuilder.loadImage("/screen2/tab_selected.png");
		}
//		mainMenu=ImageBuilder.loadImage("/MainBG.png");
//		selected_bg=ImageBuilder.loadImage("/bg.png");
//		bar=ImageBuilder.loadImage("/bar.png");
//		loading=ImageBuilder.loadImage("/loading.png");
		
		
//		tab_bottom=ImageBuilder.loadImage("/tab_bottom.png");
//		tab_brew=ImageBuilder.loadImage("/tab_brew.png");
//		tab_bg=ImageBuilder.loadImage("/tab_bg.png");
//		tab_select_bg=ImageBuilder.loadImage("/tab_select_bg.png");
		bottom_bg=ImageBuilder.loadImage("/bottom_bg.png");
//		triangle_color=ImageBuilder.loadImage("/triangle_color.png");
//		graylogo=ImageBuilder.loadImage("/graylogo.png");
		btn_1=ImageBuilder.loadImage("/btn_1.png");
		btn_2=ImageBuilder.loadImage("/btn_2.png");
		
		connect_bg=ImageBuilder.loadImage("/connect_bg.png");
//		logo=ImageBuilder.loadImage("/logo.png");
		
//		txt_selected=ImageBuilder.loadImage("/txt_selected.png");
//		txt_unselect=ImageBuilder.loadImage("/txt_unselect.png");
		login=ImageBuilder.loadImage("/logo.png");
		menu_bg=ImageBuilder.loadImage("/menu_bg.png");
		menu_bright=ImageBuilder.loadImage("/menu_bright.png");
//		bottom_p=ImageBuilder.loadImage("/bottom_p.png");
		line=ImageBuilder.loadImage("/line.png");
//		review=ImageBuilder.loadImage("/review.png");
//		logo_login=ImageBuilder.loadImage("/logo_login.png");
//		top_ico_1=ImageBuilder.loadImage("/top_ico_1.png");
		
//		file=ImageBuilder.loadImage("/file.png");
//		longline=ImageBuilder.loadImage("/longline.png");
//		search_bar_bg=ImageBuilder.loadImage("/search bar_bg.png");
//		select_block=ImageBuilder.loadImage("/select block.png");
//		select_bright=ImageBuilder.loadImage("/select_bright.png");

		top_bg=ImageBuilder.loadImage("/top_bg.png");
//		triangle=ImageBuilder.loadImage("/triangle.png");
//		unreaded=ImageBuilder.loadImage("/unreaded.png");
		review_list_triangle_right=ImageBuilder.loadImage("/review-list_triangle-right.png");
		review_list_triangle_left=ImageBuilder.loadImage("/review-list_triangle-left.png");
//		plus_icon=ImageBuilder.loadImage("/add ico.png");
//		subtract_icon=ImageBuilder.loadImage("/reduce ico.png");
//		browse_title_bg=ImageBuilder.loadImage("/browse_title_bg.png");
//		search_2=ImageBuilder.loadImage("/search_2.png");
		btn_bg=ImageBuilder.loadImage("/btn_bg.png");
	
	}
	

}
