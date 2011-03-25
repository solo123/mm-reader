package com.caimeng.uilibray.control;


import java.util.Vector;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import com.caimeng.software.model.AlertMessage;
import com.caimeng.software.model.AppContext;
import com.caimeng.software.model.Consts;
import com.caimeng.software.readerobj.Book;
import com.caimeng.software.readerobj.BookChapterAllBean;
import com.caimeng.software.readerobj.BookInfoAllBean;
import com.caimeng.software.readerobj.HistoryBook;
import com.caimeng.software.readerobj.SimpleBook;
import com.caimeng.software.rms.RmsManager;
import com.caimeng.system.MIDlet.MainMIDlet;
import com.caimeng.system.control.Handle;
import com.caimeng.uilibray.common.BaseControl;
import com.caimeng.uilibray.component.DataList;
import com.caimeng.uilibray.component.ImageLabel;
import com.caimeng.uilibray.component.Label;
import com.caimeng.uilibray.component.MessageBox;
import com.caimeng.uilibray.component.Tab;
import com.caimeng.uilibray.container.XMLForm;
import com.caimeng.uilibray.event.ActionListener;
import com.caimeng.uilibray.event.ItemEvent;
import com.caimeng.uilibray.event.ItemStateListener;
import com.caimeng.uilibray.menu.Menu;
import com.caimeng.uilibray.menu.MenuItem;
import com.caimeng.uilibray.menu.ToolBar;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.tempcanvas.FlashCanvas;
import com.caimeng.uilibray.utils.URLEncoder;

public class UIControl {
	static UIControl ui=null;
	private Display display;
	private MainMIDlet midlet;
	private XMLForm mainForm=null;
	private FlashCanvas flash=null;
	boolean isFirstRead=true;
	/******免费阅读计数************/
	private int freeTime=0;
	public UIControl(MainMIDlet midlet,Display display){
		this.display=display;
		this.midlet=midlet;
		flash=new FlashCanvas(midlet);
		display.setCurrent(flash);
		Consts.cmchannel=Consts.channel+midlet.getChannel();
		System.out.println("channel======"+Consts.cmchannel);
		UIManager.loadSkin();
		new Thread(){
			public void run(){
				try{
//					CMBilling.startReadSoftBill();
					
					initHomePage();
//					Handle.startActivat();
//					Handle.startProcess();
//					Handle.test();
//					initHomePage();
//					Handle.activateRead();
				}catch(Exception e){
					e.printStackTrace();
				}
				
//				initHomePage();
//				Handle.doSubscribeProcedurePV();
//				flash=null;
//				mainForm();
//				Handle.testConnectWap();
//				Handle.execPVToRead(0,null);
			}
		}.start();
		
		
	}
	public static void initUIControl(MainMIDlet midlet,Display display){
		if(ui==null){
			ui=new UIControl(midlet,display);
		}
		
		
	}
	public static UIControl getInstance(){
		return ui;
	}

	/**
	 * 初始首页
	 *
	 */
	public void initHomePage(){
		if(flash!=null){
			flash.stopTimer();
		}
		Vector recent=null;
		if(tabIndex==0){
			
			Vector v=RmsManager.getHistoryBookList();
			if(v!=null){
				recent=new Vector();
				recent.addElement(v.elementAt(v.size()-1));
				if(v.size()>=2)
					recent.addElement(v.elementAt(v.size()-2));
			}
		}
		homePage(recent);
		
	}
	
	public void searchForText(String text){
//		searchKey=Handle.gb2utf(text);
		searchKey=URLEncoder.encode(text);
		System.out.println("searchKey=="+searchKey);
		if(text==null ||text.equals("")){
			this.setMessageBox(2, "搜索条件不能为空",0);
		}else{
			startMessageBox();
			new Thread(){
				public void run(){
					searchList=Handle.getSearchResultByKey(searchKey);
					if(searchList!=null && searchList.size()!=0){
						
						searchResult(searchList);
					}else{
						//没有搜索到内容
//						System.out.println("搜索结果为空");
					setMessageBox(2, "搜索结果为空"/*Consts.test.toString()*/,0);
					}
				}
			}.start();
		}
	}
	/************************进度条更新*******************************/
//	public void updateTooBar(int value){
//		if(display.getCurrent().getClass().isInstance(new CMForm())){
//			((CMForm)display.getCurrent()).getUIManager().getMenuBar().updateProgress(value);
//		}else{
//			((XMLForm)display.getCurrent()).getUIManager().getMenuBar().updateProgress(value);
//		}
//	}
	
	
	/*******************************************************/
	
	/******************MessageBox  操作*****************************************/
	public void setMessageBox(int Style,String msg,int msgStyle){
		if(display.getCurrent()!=null)
		if(display.getCurrent().getClass().isInstance(new XMLForm())){
			if(((XMLForm)display.getCurrent()).getUIManager().getMessageBox()!= null){
				
				((XMLForm)display.getCurrent()).getUIManager().getMessageBox().setMegStyle(msgStyle);
				((XMLForm)display.getCurrent()).getUIManager().getMessageBox().setMessageMode(Style, msg);
			}
		}
	}
	public void startMessageBox(){
		if(display.getCurrent()!=null)
		if(display.getCurrent().getClass().isInstance(new XMLForm())){
			if(((XMLForm)display.getCurrent()).getUIManager().getMessageBox()!= null){
				
				((XMLForm)display.getCurrent()).getUIManager().getMessageBox().startMessgeTimer();
			}
		}
	}
	public void stopMessageBox(){
		if(display.getCurrent()!=null)
		if(display.getCurrent().getClass().isInstance(new XMLForm())){
			if(((XMLForm)display.getCurrent()).getUIManager().getMessageBox()!= null)
			((XMLForm)display.getCurrent()).getUIManager().getMessageBox().stopMessageTimer();
		}
	}
	/***********************************************************/
	private String searchKey=null;
	private Vector searchList=null;
	private XMLForm searchResultForm=null;
	private void searchResult(Vector searchList2){
		searchList=searchList2;
		searchResultForm=new XMLForm("搜索结果",display);
		
		int mx=searchResultForm.frm_Width/7;
		int my=(searchResultForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),searchResultForm.frm_Width-(mx<<1),searchResultForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(searchResultForm.frm_Width, searchResultForm.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				searchResultForm.getUIManager().getMenuBar().leftMenuOpen=false;
				searchResultForm.getUIManager().getMenuBar().rightMenuOpen=false;
				if(searchResultForm.getUIManager().getMessageBox().getSelect()==1){
					if(searchResultForm.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}
				}else if(searchResultForm.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		
		ToolBar bar=new ToolBar(searchResultForm);
		bar.hasTitle=true;
		Menu back=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("打开",Menu.LEFT_ROOT_MENU);	
		bar.setLeftMenu(menu);
		bar.setRightMenu(back);
		DataList dl=new DataList(0,UIManager.top_bg.getHeight(),searchResultForm.frm_Width-10,searchResultForm.frm_Height-UIManager.top_bg.getHeight()*2,5);
		dl.focus=true;
		if(searchList2!=null){
			for(int i=0;i<searchList2.size();i++){				
				dl.addItem2(((SimpleBook)searchList2.elementAt(i)).getBookName(), "");
			}
		}
		searchResultForm.addMenuBar(bar);
		searchResultForm.addMessageBox(msg);
		searchResultForm.add(dl);
		dl.ui.selector.inComboItem=true;
		displayXMLForm(searchResultForm);
//		display.setCurrent(searchResultForm);
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				initHomePage();
				searchList=null;
			}
			
		});
		dl.addItemStateListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((DataList)searchResultForm.getUIManager().container[0]).onShowSelectedItemNum+1;
				String bookid=((SimpleBook)searchList.elementAt(index)).getBookId();
				searchResultForm.setKey(bookid);
				searchResultForm.initBookIntroPage();
			}
			
		});
		menu.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				searchResultForm.getUIManager().getMenuBar().leftMenuOpen=false;
				int index=((DataList)searchResultForm.getUIManager().container[0]).onShowSelectedItemNum+1;
				String bookid=((SimpleBook)searchList.elementAt(index)).getBookId();
				searchResultForm.setKey(bookid);
				searchResultForm.initBookIntroPage();
			}
			
		});
	}
	
	public XMLForm homePage=null;
	private void homePage(Vector recentRead){
		historyForm=null;
		homePage=null;
		chapterDirectory=null;
		bookIntroPage=null;
		viewBookContext=null;
		searchResultForm=null;
		viewBookListOfType=null;
		helpForm=null;
		chapterList=null;
		bookList=null;
		homePage=new XMLForm("欢迎界面",display);
		homePage.isMainTab=true;
		Tab tab=new Tab(0,0,homePage.frm_Width,UIManager.tab_bg.getHeight());
		tab.addItem("主页");
		tab.addItem("推荐");
		tab.addItem("排行");
		tab.setSelectedIndex(tabIndex);
		homePage.add(tab);
		int x=5;
		int y=tab.height+5;
		Label label=new Label(x,y);
		label.setLabel("最近阅读");
		label.setlabelStyle(1);
		label.setBookAndType(AppContext.HISTORYREAD, null);
		label.addLabelAction();
		label.setPass(false);
		homePage.add(label);
		y+=label.height;
		if(recentRead==null){
			label=new Label(x,y);
			label.setLabel("无阅读记录");
//			label.setlabelStyle(1);
			homePage.add(label);
			y+=label.height;
		}else{
			for(int i=0;i<recentRead.size();i++){
				label=new Label(x,y);
				label.setLabel(((HistoryBook)recentRead.elementAt(i)).bookname);
				label.setBookAndType(AppContext.HISTORYREAD, ((HistoryBook)recentRead.elementAt(i)));
				label.addLabelAction();
				label.setPass(false);
				homePage.add(label);
				y+=label.height;
			}
		}
		
		int mx=homePage.frm_Width/7;
		int my=(homePage.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),homePage.frm_Width-(mx<<1),homePage.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(homePage.frm_Width, homePage.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				homePage.getUIManager().getMenuBar().leftMenuOpen=false;
				homePage.getUIManager().getMenuBar().rightMenuOpen=false;
				if(homePage.getUIManager().getMessageBox().getSelect()==1){
					if(homePage.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}else if(homePage.getUIManager().getMessageBox().getMsgStyle()==2){
						//扣费确定
						new Thread(){
							public void run(){
								try{
									
									Handle.doSubscribeProcedurePV();
								}catch(Exception e){
									
								}
							}
						}.start();
					}
				}else if(homePage.getUIManager().getMessageBox().getSelect()==2){
					if(homePage.getUIManager().getMessageBox().getMsgStyle()==2){
						
						setMessageBox(3, "下载激活5元，终身免费", 2);
					}
				}
			}
			
		});
		
		
		
		ToolBar bar=new ToolBar(homePage);
		
		Menu quit=new Menu("退出",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("选项",Menu.LEFT_ROOT_MENU);	
		bar.setLeftMenu(menu);
		MenuItem help=new MenuItem("帮助",Menu.LEFT_ROOT_MENU);
		MenuItem history=new MenuItem("历史",Menu.LEFT_ROOT_MENU);
		MenuItem search=new MenuItem("搜索",Menu.LEFT_ROOT_MENU);
		MenuItem refresh=new MenuItem("刷新",Menu.LEFT_ROOT_MENU);
		menu.addMenuItem(refresh);
		menu.addMenuItem(search);
		menu.addMenuItem(history);
		menu.addMenuItem(help);
		bar.setRightMenu(quit);
//		bar.hasTitle=true;
		homePage.addMenuBar(bar);
		homePage.addMessageBox(msg);
		homePage.setTopY(y);
//		display.setCurrent(homePage);
		msg.startMessgeTimer();
		
		displayXMLForm(homePage);
		switch(tabIndex){
		case 0:
			homePage.initMainForm();
			break;
		case 1:
			initBookType();
			break;
		case 2:
			initBookClassic();
			break;
		}
//		msg.stopMessageTimer();
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				
				setMessageBox(3,AlertMessage.ISQUIT,MessageBox.QUIT);
			}
			
		});
		
		refresh.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((Tab)homePage.getUIManager().container[0]).getSelectedIndex();
				switch(index){
				case 0:
					RmsManager.deleteBookStoreList();
					initHomePage();
					break;
				case 1:
					RmsManager.deleteTypeList();
					initBookType();
					break;
				case 2:
					RmsManager.deleteClassicList();
					initBookClassic();
					break;
				}
				homePage.getUIManager().getMenuBar().leftMenuOpen=false;
			}
			
		});
		
		help.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				helpForm();
//				homePage.getUIManager().getMenuBar().leftMenuOpen=false;
			}
			
		});
		history.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				Vector v=new Vector();
				v=RmsManager.getHistoryBookList();
				historyForm(v);
			}
			
		});
		search.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				homePage.getUIManager().getMenuBar().leftMenuOpen=false;
				for(int i=0;i<homePage.getUIManager().container.length;i++){
					if((homePage.getUIManager().container[i]).getClass().isInstance(new com.caimeng.uilibray.component.SearchButton())){
						homePage.getUIManager().selector.setSelected(i);
						homePage.getUIManager().selector.setFocus(i);
						break;
					}
				}
//				test.getUIManager().selector.setSelected(index)
			}
			
		});
	}
	public void clickLabel(Label label){
		((XMLForm)display.getCurrent()).clickLabel(label);
	}
	private int tabIndex=0;
	private XMLForm chapterDirectory;
	Vector chapterList=null;
	/**
	 * 章节目录 
	 * @param list
	 */
	private void chapterDirectory(Vector list){
		chapterDirectory=new XMLForm("目录",display);
		
		int mx=chapterDirectory.frm_Width/7;
		int my=(chapterDirectory.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),chapterDirectory.frm_Width-(mx<<1),chapterDirectory.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(chapterDirectory.frm_Width, chapterDirectory.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				chapterDirectory.getUIManager().getMenuBar().leftMenuOpen=false;
				chapterDirectory.getUIManager().getMenuBar().rightMenuOpen=false;
				if(chapterDirectory.getUIManager().getMessageBox().getSelect()==1){
					if(chapterDirectory.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}else if(chapterDirectory.getUIManager().getMessageBox().getMsgStyle()==2){//收费
						chargesReadAndMM();
					}
				}else if(chapterDirectory.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		
		
		ToolBar bar=new ToolBar(chapterDirectory);
		bar.hasTitle=true;
		Menu back=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("打开",Menu.LEFT_ROOT_MENU);	
		bar.setLeftMenu(menu);
		bar.setRightMenu(back);
		DataList dl=new DataList(0,UIManager.top_bg.getHeight(),chapterDirectory.frm_Width-10,chapterDirectory.frm_Height-UIManager.top_bg.getHeight()*2,5);
		dl.focus=true;
		if(list!=null){
			for(int i=0;i<list.size();i++){				
				dl.addItem2(((BookChapterAllBean)list.elementAt(i)).getChapterName(), "");
			}
		}
		chapterDirectory.addMenuBar(bar);
		chapterDirectory.add(dl);
		chapterDirectory.addMessageBox(msg);
		dl.ui.selector.inComboItem=true;
		displayXMLForm(chapterDirectory);
//		display.setCurrent(chapterDirectory);
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				openflag=false;
				display.setCurrent(bookIntroPage);
			}
			
		});
		dl.addItemStateListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((DataList)chapterDirectory.getUIManager().container[0]).onShowSelectedItemNum+1;
				chapterCharges(index);
			}
			
		});
		menu.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				chapterDirectory.getUIManager().getMenuBar().leftMenuOpen=false;
				int index=((DataList)chapterDirectory.getUIManager().container[0]).onShowSelectedItemNum+1;
				chapterCharges(index);
			}
			
		});
	}
	private XMLForm helpForm;
	private void helpForm(){
		
		helpForm=new XMLForm("帮助",display);
		Label label=new Label(5,UIManager.top_bg.getHeight());
//		String str="资费提示："+Consts.CHARGES_ALERT_MESSAGE;
//		if(Consts.SYSTEMTYPE==1){
//			str="";
//		}
		label.setLabel(midlet.getHelpStr());
		label.setPass(true);
		label.notBgColor=true;
		ToolBar bar=new ToolBar(helpForm);
		bar.hasTitle=true;
		Menu quit=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("",Menu.LEFT_ROOT_MENU);	
		bar.setLeftMenu(menu);
		bar.setRightMenu(quit);
		helpForm.addMenuBar(bar);
		helpForm.add(label);
//		display.setCurrent(helpForm);
		displayXMLForm(helpForm);
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				
				initHomePage();
//				display.setCurrent(homePage);
			}
			
		});
	}
	private XMLForm historyForm;
	Vector historylist;
	private String bookName;
	/**
	 * 最近阅读
	 * @param historylist2
	 */
	public void historyForm(Vector historylist2){
		this.historylist=historylist2;
		historyForm=new XMLForm("最近阅读",display);
		
		int mx=historyForm.frm_Width/7;
		int my=(historyForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),historyForm.frm_Width-(mx<<1),historyForm.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(historyForm.frm_Width, historyForm.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				historyForm.getUIManager().getMenuBar().leftMenuOpen=false;
				historyForm.getUIManager().getMenuBar().rightMenuOpen=false;
				if(historyForm.getUIManager().getMessageBox().getSelect()==1){
					if(historyForm.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}
				}else if(historyForm.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		
		DataList dl=new DataList(0,UIManager.top_bg.getHeight(),historyForm.frm_Width-10,historyForm.frm_Height-UIManager.top_bg.getHeight()*2,5);
		dl.focus=true;
	
		
		ToolBar bar=new ToolBar(historyForm);
		bar.hasTitle=true;
		Menu quit=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("选项",Menu.LEFT_ROOT_MENU);	
		MenuItem open=new MenuItem("打开",Menu.LEFT_ROOT_MENU);
		MenuItem del=new MenuItem("删除",Menu.LEFT_ROOT_MENU);
		MenuItem clear=new MenuItem("清空",Menu.LEFT_ROOT_MENU);
		bar.setLeftMenu(menu);
		bar.setRightMenu(quit);
		if(historylist==null || historylist.size()==0){
			menu.setLabel("");
			Label label=new Label(5,UIManager.top_bg.getHeight()+5);
			label.setLabel("无阅读记录，请返回。");
			historyForm.add(label);
			
			
//			label=null;
		}else{
			
			menu.addMenuItem(open);
			menu.addMenuItem(del);
			menu.addMenuItem(clear);
			int h=UIManager.top_bg.getHeight()+5;
			for(int i=0;i<historylist.size();i++){
//				Label lable=new Label(5,h);
//				lable.setPass(false);
//				lable.setLabel(((HistoryBook)historylist.elementAt(i)).bookname);
//				historyForm.add(lable);
//				h+=25;
				dl.addItem2(((HistoryBook)historylist.elementAt(i)).bookname, "");
			}
			historyForm.add(dl);
			dl.ui.selector.inComboItem=true;
		}
		historyForm.addMenuBar(bar);
		historyForm.addMessageBox(msg);
//		display.setCurrent(historyForm);
		displayXMLForm(historyForm);
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				initHomePage();
			}
			
		});
		dl.addItemStateListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
//				System.out.println("222222222222");
				int index=((DataList)historyForm.getUIManager().container[0]).onShowSelectedItemNum+1;
				String key=((HistoryBook)historylist.elementAt(index)).bookurl;
				historyForm.setKey(key);
				historyForm.initBookIntroPage();
			}
			
		});
		
		open.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
//				System.out.println("11111111111111");
				int index=((DataList)historyForm.getUIManager().container[0]).onShowSelectedItemNum+1;
				String key=((HistoryBook)historylist.elementAt(index)).bookurl;
				historyForm.setKey(key);
				historyForm.initBookIntroPage();
				historyForm.getUIManager().getMenuBar().leftMenuOpen=false;
			}
			
		});
		del.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((DataList)historyForm.getUIManager().container[0]).onShowSelectedItemNum+1;
				historylist.removeElementAt(index);
				RmsManager.saveHistoryBookList(historylist);
				historyForm(historylist);
			}
			
		});
		clear.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				RmsManager.deleteHistoryBook();
				historyForm(null);
			}
			
		});
	}
	private String getCurrBookName(){
		return this.bookName;
	}
	 private String currentbookid;
	private XMLForm bookIntroPage;
	private boolean openflag;
	/**
	 * 书简介页面
	 * @param bookinfo
	 * @param key
	 */
	public void bookIntroPage(BookInfoAllBean bookinfo,Vector list,Image img,String currbookId){
//		chapterList=Handle.getBookChapterByID(key);
		chapterList=list;
		list=null;
		currentbookid=currbookId;
		homePage=null;
		openflag=false;
		bookName=bookinfo.getBookName();
		bookIntroPage=new XMLForm(bookName,display);
		
		int mx=bookIntroPage.frm_Width/7;
		int my=(bookIntroPage.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),bookIntroPage.frm_Width-(mx<<1),bookIntroPage.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(bookIntroPage.frm_Width, bookIntroPage.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				bookIntroPage.getUIManager().getMenuBar().leftMenuOpen=false;
				bookIntroPage.getUIManager().getMenuBar().rightMenuOpen=false;
				if(bookIntroPage.getUIManager().getMessageBox().getSelect()==1){
					if(bookIntroPage.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}else if(bookIntroPage.getUIManager().getMessageBox().getMsgStyle()==1){
						BookChapterAllBean obj=(BookChapterAllBean)chapterList.elementAt(0);
						String id=obj.getChapterId();
						viewBookContext(id,obj.getPage(),0);						
							if(!Consts.hasChanges){								
								new Thread(){
									public void run(){
										Handle.doSubscribeProcedurePV();
									}
								}.start();
							}
						
					}else if(bookIntroPage.getUIManager().getMessageBox().getMsgStyle()==2){
						chapterDirectory(chapterList);
							if(!Consts.hasChanges){
								new Thread(){
									public void run(){
										Handle.doSubscribeProcedurePV();
									}
								}.start();
							}
					}
				}else if(bookIntroPage.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		
		ToolBar bar=new ToolBar(bookIntroPage);
		bar.hasTitle=true;
		Menu back=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("选项",Menu.LEFT_ROOT_MENU);	
		MenuItem open =new MenuItem("打开",Menu.LEFT_ROOT_MENU);
		MenuItem bookstore=new MenuItem("书城",Menu.LEFT_ROOT_MENU);
//		MenuItem booktype=new MenuItem("分类",Menu.LEFT_ROOT_MENU);
//		MenuItem bookboutequ=new MenuItem("精品",Menu.LEFT_ROOT_MENU);
		MenuItem quit=new MenuItem("退出",Menu.LEFT_ROOT_MENU);
		menu.addMenuItem(open);
		menu.addMenuItem(bookstore);
//		menu.addMenuItem(booktype);
//		menu.addMenuItem(bookboutequ);		
		menu.addMenuItem(quit);
		bar.setLeftMenu(menu);
		bar.setRightMenu(back);
		bookIntroPage.addMessageBox(msg);
		bookIntroPage.addMenuBar(bar);
		int x=5;
		int y=UIManager.top_bg.getHeight()+5;
		ImageLabel il=new ImageLabel(x,y,100,75,img);
		int start=Integer.parseInt(bookinfo.getHot());
		if(start==0){
			start++;
		}
		il.setBookInfo(bookinfo);
		bookIntroPage.add(il);
		y+=il.height;
		
		Label label=new Label(x,y);
		label.setLabel("立即阅读");
		label.setPass(false);		
		bookIntroPage.add(label);
		label.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				if(!openflag){
					if(Handle.checkChargesMode()==1){
						if(Consts.SYSTEMTYPE==3){
							if(!Consts.hasChanges){
								new Thread(){
									public void run(){
										try{
											Handle.doSubscribeProcedurePV();
											if(Consts.isNextRead){
//												BookChapterAllBean obj=(BookChapterAllBean)chapterList.elementAt(0);
//												String id=obj.getChapterId();
//												viewBookContext(id,obj.getPage(),0);
												openChapterItem(0);
												Consts.hasChanges=true;
											}else{
												setMessageBox(2, "阅读取消", 0);
											}
										}catch(Exception e){
											e.printStackTrace();
										}
									}
								}.start();
							}else{
//								BookChapterAllBean obj=(BookChapterAllBean)chapterList.elementAt(0);
//								String id=obj.getChapterId();
//								viewBookContext(id,obj.getPage(),0);
								openChapterItem(0);
							}
						}
					}else{
						if(Consts.SYSTEMTYPE==4){
							if(!Consts.hasChanges){
								
								setMessageBox(3, "5元激活,看书终身免费", 1);
							}else{
//								BookChapterAllBean obj=(BookChapterAllBean)chapterList.elementAt(0);
//								String id=obj.getChapterId();
//								viewBookContext(id,obj.getPage(),0);
								openChapterItem(0);
							}
						}else{
							
//							BookChapterAllBean obj=(BookChapterAllBean)chapterList.elementAt(0);
//							String id=obj.getChapterId();
//							viewBookContext(id,obj.getPage(),0);
							openChapterItem(0);
							if(Consts.SYSTEMTYPE==3){
								if(!Consts.hasChanges){
									
									new Thread(){
										public void run(){
											Handle.doSubscribeProcedurePV();
										}
									}.start();
								}
							}
						}
					}
				}
			}
			
		});
		label=new Label(x+new BaseControl().smallFont.stringWidth("在线阅读")+10,y);
		label.setLabel("查看目录");
		label.setPass(false);
		bookIntroPage.add(label);
		y+=label.height;
		label.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				if(!openflag){
					checkCharges();
					
				
				}
			}
			
		});
		
		label=new Label(x,y);
		String text=bookinfo.getBookIntro();
		if(text.length()>50){
			text=text.substring(0, 50)+".....";
		}
		label.setLabel("简介: "+text);
		bookIntroPage.add(label);
		text=null;
		y+=label.height;
		
		if(Consts.SYSTEMTYPE==3 || Consts.SYSTEMTYPE==4){
			if(!"0154".equals(Consts.VERSION)){
				Consts.CHARGES_ALERT_MESSAGE="下载激活5元，终身免费";
			}
			if(!Consts.hasChanges){
				
				if(!Handle.checkHasUserCode()){
					
					label=new Label(x,y);
					label.setLabel(Consts.CHARGES_ALERT_MESSAGE);
					bookIntroPage.add(label);
					y+=label.height;
				}else{
					Consts.hasChanges=true;
				}
			}
		}
		
		
	
		
//		display.setCurrent(bookIntroPage);
		displayXMLForm(bookIntroPage);
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				if(historyForm!=null){
					display.setCurrent(historyForm);
				}else if(searchResultForm!=null){
					display.setCurrent(searchResultForm);
				}else if(viewBookListOfType!=null){
					display.setCurrent(viewBookListOfType);
				}
				else{
					initHomePage();
				}
				chapterList=null;
			}
			
		});
		open.addItemListener(new ItemStateListener(){

			

			public void itemStateChanged(ItemEvent e) {
				openflag=true;
				checkCharges();
				bookIntroPage.getUIManager().getMenuBar().leftMenuOpen=false;
			}
			
		});
		bookstore.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				chapterList=null;
				tabIndex=0;
				
				initHomePage();
			}
			
		});
		
		quit.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				setMessageBox(3, AlertMessage.ISQUIT, MessageBox.QUIT);
			}
			
		});
		
	}
	private boolean checkfirst=false;
	/**
	 * 阅读和MM收费流程
	 */
	private void chargesReadAndMM(){
		if(!checkfirst){
			checkfirst=true;
			new Thread(){
				public void run(){
					System.out.println("开启测试");
//					try{
						
						Handle.doMMAndRead();
//					}catch(Exception e){
//						e.printStackTrace();
//					}
				}
			}.start();
		}
	}
	/**
	 * 章节 是否是体验检测
	 * index 目录列表对应序号
	 */
	private void chapterCharges(int index){
		if(Consts.SYSTEMTYPE==5 && !checkfirst){
			freeTime++;
			if(Consts.hasChanges || freeTime<2 ){
				//免费体验
				openChapterItem(index);
				if(isFirstRead){
					isFirstRead=false;
					new Thread(){
						public void run(){
							Handle.startActivat();
						}
					}.start();
				}
			}else{
				//扣费
				setMessageBox(3, "10元激活,看书终身免费，由幽默智慧产品代收，咨询：0755-83890120", 2);
			}
		}else{
			openChapterItem(index);
		}
	}
	/**
	 * 打开章节目录选中章节
	 *
	 */
	private void openChapterItem(int index){		
		String chapterid=((BookChapterAllBean)chapterList.elementAt(index)).getChapterId();
		viewBookContext(chapterid,((BookChapterAllBean)chapterList.elementAt(index)).getPage(),index);
		
	}
	/**
	 * 检测是否扣费
	 *
	 */
	private void checkCharges(){
		if(Handle.checkChargesMode()==1){
			if(Consts.SYSTEMTYPE==3){
				if(!Consts.hasChanges){
					
					new Thread(){
						public void run(){
							try{
								
								Handle.doSubscribeProcedurePV();
								if(Consts.isNextRead){
									chapterDirectory(chapterList);
									Consts.hasChanges=true;
								}else{
									setMessageBox(2, "阅读取消", 0);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}.start();
				}else{
					chapterDirectory(chapterList);
				}
			}
		}else{
			if(Consts.SYSTEMTYPE==4){
				if(!Consts.hasChanges){
					
					setMessageBox(3, "5元激活,看书终身免费", 2);
				}else{
					chapterDirectory(chapterList);
				}
			}else{
				
				chapterDirectory(chapterList);
				if(Consts.SYSTEMTYPE==3){
					if(!Consts.hasChanges){
						
						new Thread(){
							public void run(){
								Handle.doSubscribeProcedurePV();
							}
						}.start();
					}
				}
			}
		}
	}
	private XMLForm viewBookListOfType;
	private Vector bookList=null;
	public void viewBookListOfType(String typename,Vector bookList2){
		homePage=null;
		this.bookList=bookList2;
		viewBookListOfType=new XMLForm(typename,display);
		int mx=viewBookListOfType.frm_Width/7;
		int my=(viewBookListOfType.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),viewBookListOfType.frm_Width-(mx<<1),viewBookListOfType.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(viewBookListOfType.frm_Width, viewBookListOfType.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				viewBookListOfType.getUIManager().getMenuBar().leftMenuOpen=false;
				viewBookListOfType.getUIManager().getMenuBar().rightMenuOpen=false;
				if(viewBookListOfType.getUIManager().getMessageBox().getSelect()==1){
					if(viewBookListOfType.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}
				}else if(viewBookListOfType.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		
		DataList dl=new DataList(0,UIManager.top_bg.getHeight(),searchResultForm.frm_Width-10,searchResultForm.frm_Height-UIManager.top_bg.getHeight()*2,5);
		dl.focus=true;
		if(bookList!=null){
			if(bookList.elementAt(0).getClass().isInstance(new Book())){
				for(int i=0;i<bookList.size();i++){				
					dl.addItem2(((Book)bookList.elementAt(i)).getBookName(), "");
				}

			}else{
				
				for(int i=0;i<bookList.size();i++){				
					dl.addItem2(((SimpleBook)bookList.elementAt(i)).getBookName(), "");
				}
			}
		}
		ToolBar bar=new ToolBar(viewBookListOfType);
		bar.hasTitle=true;		
		Menu back=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("选项",Menu.LEFT_ROOT_MENU);	
		MenuItem open=new MenuItem("打开",Menu.LEFT_ROOT_MENU);
		MenuItem exit=new MenuItem("退出",Menu.LEFT_ROOT_MENU);
		MenuItem home=new MenuItem("书城",Menu.LEFT_ROOT_MENU);		
		menu.addMenuItem(open);
		menu.addMenuItem(home);
		menu.addMenuItem(exit);
		bar.setLeftMenu(menu);
		bar.setRightMenu(back);
		viewBookListOfType.add(dl);
		dl.ui.selector.inComboItem=true;
		viewBookListOfType.addMessageBox(msg);
		viewBookListOfType.addMenuBar(bar);
//		display.setCurrent(viewBookListOfType);
		displayXMLForm(viewBookListOfType);
		dl.addItemStateListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((DataList)viewBookListOfType.getUIManager().container[0]).onShowSelectedItemNum+1;
				if(bookList.elementAt(0).getClass().isInstance(new Book())){
					Book book=	((Book)bookList.elementAt(index));
					viewBookListOfType.setKey(book.getBookId());
					viewBookListOfType.initBookIntroPage();
				}else{
					SimpleBook book=	((SimpleBook)bookList.elementAt(index));
					viewBookListOfType.setKey(book.getBookId());
					viewBookListOfType.initBookIntroPage();
				}
			}
			
		});
		exit.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				setMessageBox(3, AlertMessage.ISQUIT, MessageBox.QUIT);
			}
			
		});
		home.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				tabIndex=0;
				initHomePage();
				bookList=null;
			}
			
		});
		
		open.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				int index=((DataList)viewBookListOfType.getUIManager().container[0]).onShowSelectedItemNum+1;
				if(bookList.elementAt(0).getClass().isInstance(new Book())){
					Book book=	((Book)bookList.elementAt(index));
					viewBookListOfType.setKey(book.getBookId());
					viewBookListOfType.initBookIntroPage();
				}else{
					SimpleBook book=	((SimpleBook)bookList.elementAt(index));
					viewBookListOfType.setKey(book.getBookId());
					viewBookListOfType.initBookIntroPage();
				}
				
			}
			
		});
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				viewBookListOfType.getUIManager().getMenuBar().rightMenuOpen=false;
				initHomePage();
				bookList=null;
			}
			
		});
		
	}
	private XMLForm viewBookContext=null;
	/**
	 * currChapterIndex 当前章节顺序
	 * @param chapterid
	 * @param page 如果为null表示 不用分页
	 */
	public void viewBookContext(String chapterid,String page,int currChapterIndex){
		
		viewBookContext=null;
		viewBookContext=new XMLForm(bookName,display,AppContext.BOOKCONTEXT,chapterid);
		viewBookContext.setTotalPage(page,currChapterIndex);
		int mx=viewBookContext.frm_Width/7;
		int my=(viewBookContext.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight())>>2;		
		MessageBox msg=new MessageBox(mx,my+UIManager.top_bg.getWidth(),viewBookContext.frm_Width-(mx<<1),viewBookContext.frm_Height-UIManager.top_bg.getHeight()-UIManager.bottom_bg.getHeight()-2*my,2,0);
		msg.title=AlertMessage.SYSTEMALERTTITLE;
		msg.setMessageStyle(0);
		msg.setGroundSize(viewBookContext.frm_Width, viewBookContext.frm_Height);	
		msg.addActionListener(new ActionListener(){
			public void actionPerformed(ItemEvent e) {
				viewBookContext.getUIManager().getMenuBar().leftMenuOpen=false;
				viewBookContext.getUIManager().getMenuBar().rightMenuOpen=false;
				if(viewBookContext.getUIManager().getMessageBox().getSelect()==1){
					if(viewBookContext.getUIManager().getMessageBox().getMsgStyle()==MessageBox.QUIT){
						midlet.quit();
					}else if(viewBookContext.getUIManager().getMessageBox().getMsgStyle()==2){
						chargesReadAndMM();
					}
				}else if(viewBookContext.getUIManager().getMessageBox().getSelect()==2){
					
				}
			}
			
		});
		ToolBar bar=new ToolBar(viewBookContext);
		bar.hasTitle=true;
		
		
		
		Menu back=new Menu("返回",Menu.RIGHT_ROOT_MENU);
		Menu menu=new Menu("选项",Menu.LEFT_ROOT_MENU);	
//		MenuItem nextpage=new MenuItem("下页",Menu.LEFT_ROOT_MENU);
//		MenuItem uppage=new MenuItem("上页",Menu.LEFT_ROOT_MENU);
		MenuItem nextChapter=new MenuItem("下章",Menu.LEFT_ROOT_MENU);
		MenuItem upChapter=new MenuItem("上章",Menu.LEFT_ROOT_MENU);
		MenuItem dic=new MenuItem("目录",Menu.LEFT_ROOT_MENU);
		MenuItem exit=new MenuItem("退出",Menu.LEFT_ROOT_MENU);
		MenuItem home=new MenuItem("书城",Menu.LEFT_ROOT_MENU);
		
		
		menu.addMenuItem(dic);
//		menu.addMenuItem(uppage);
//		menu.addMenuItem(nextpage);
		menu.addMenuItem(upChapter);
		menu.addMenuItem(nextChapter);
		menu.addMenuItem(home);
		menu.addMenuItem(exit);
		bar.setLeftMenu(menu);
		bar.setRightMenu(back);
		viewBookContext.addMessageBox(msg);
		viewBookContext.addMenuBar(bar);
		viewBookContext.startLoadingContext();
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ItemEvent e) {
				if(chapterDirectory!=null){
					chapterDirectory(chapterList);
//					display.setCurrent(chapterDirectory);
				}else{
//					if(Consts.hasChanges){
//						
//					}else{
					openflag=false;
					display.setCurrent(bookIntroPage);
//						display.setCurrent(bookIntroPage);
//					}
				}
			}
			
		});
		nextChapter.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				String chapterid="";
				String page="";
				int index=viewBookContext.getCurrChapterIndex();
				if(index<chapterList.size()-1){
					index++;
//					chapterid=((BookChapterAllBean)chapterList.elementAt(index)).getChapterId();
//					page=((BookChapterAllBean)chapterList.elementAt(index)).getPage();
//					viewBookContext(chapterid,page,index);
					chapterCharges(index);
				}else{
//					已经是最后一章,提示
					setMessageBox(2, "已经是最后一章", 0);
				}
			}
			
		});
		upChapter.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				
				String chapterid="";
				String page="";
				int index=viewBookContext.getCurrChapterIndex();
				if(index>0){
					index--;
//					chapterid=((BookChapterAllBean)chapterList.elementAt(index)).getChapterId();
//					page=((BookChapterAllBean)chapterList.elementAt(index)).getPage();
//					viewBookContext(chapterid,page,index);
					chapterCharges(index);
				}else{
//					已经是最后一章,提示
					setMessageBox(2, "已经是第一章", 0);
				}
				
			}
			
		});
		/*uppage.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				if(viewBookContext.getPageNum()>1){
					viewBookContext.getUIManager().removeAllItem();
					int num=viewBookContext.getPageNum();
					num--;
					viewBookContext.setPageNum(num);
				
					viewBookContext.startLoadingContext();
				}else{
					setMessageBox(2, "已经是第一页", 0);
				}
			}
			
		});
		nextpage.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				
				if(viewBookContext.getTotalPage()!=null && viewBookContext.getPageNum()<Integer.parseInt(viewBookContext.getTotalPage())){
					viewBookContext.getUIManager().removeAllItem();
					int num=viewBookContext.getPageNum();
					num++;
					viewBookContext.setPageNum(num);
					viewBookContext.startLoadingContext();
				}else{
					setMessageBox(2, "已经是最后一页", 0);
				}
			}
			
		});*/
		dic.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				
					chapterDirectory(chapterList);
				
			}
			
		});
		exit.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				setMessageBox(3, AlertMessage.ISQUIT, MessageBox.QUIT);
			}
			
		});
		home.addItemListener(new ItemStateListener(){

			public void itemStateChanged(ItemEvent e) {
				tabIndex=0;
				initHomePage();
				viewBookContext=null;
			}
			
		});
	}
	public void openPage(int mode){
		if(mode==0){
			if(viewBookContext.getPageNum()>1){
				viewBookContext.getUIManager().removeAllItem();
				int num=viewBookContext.getPageNum();
				num--;
				viewBookContext.setPageNum(num);
			
				viewBookContext.startLoadingContext();
			}else{
				setMessageBox(2, "已经是第一页", 0);
			}
		}else{
			if(viewBookContext.getTotalPage()!=null && viewBookContext.getPageNum()<Integer.parseInt(viewBookContext.getTotalPage())){
				viewBookContext.getUIManager().removeAllItem();
				int num=viewBookContext.getPageNum();
				num++;
				viewBookContext.setPageNum(num);
				viewBookContext.startLoadingContext();
			}else{
				setMessageBox(2, "已经是最后一页", 0);
			}
		}
	}
	
	public void initBookType(){
		homePage.getUIManager().deleteItem();
		homePage.loadBookType();
	}
	
	
	public void initBookClassic(){
		homePage.getUIManager().deleteItem();
		homePage.loadBookClassic();
		
		
	}

	public void displayXMLForm(XMLForm form){
		stopMessageBox();
		display.setCurrent(form);
	}
	public void tabeChangeState(int state){
		this.tabIndex=state;
		switch(state){
		case 0:
			initHomePage();
			break;
		case 1:
			initBookType();
			break;
		case 2:
			initBookClassic();
			break;
		}
	}

}
