package com.caimeng.uilibray.container;

import java.util.Vector;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import com.caimeng.software.model.AppContext;
import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;
import com.caimeng.software.readerobj.Book;
import com.caimeng.software.readerobj.BookAllTypeBean;
import com.caimeng.software.readerobj.BookChapterAllBean;
import com.caimeng.software.readerobj.BookChapterInfoBean;
import com.caimeng.software.readerobj.BookInfoAllBean;
import com.caimeng.software.readerobj.HistoryBook;
import com.caimeng.software.rms.RmsManager;
import com.caimeng.system.control.Handle;
import com.caimeng.uilibray.component.Label;
import com.caimeng.uilibray.component.SearchButton;
import com.caimeng.uilibray.control.UIControl;
import com.caimeng.uilibray.skin.UIManager;


public class XMLForm extends CMForm{

	private String title;
	int topY=UIManager.top_bg.getHeight();
	int leftX=5;
	int style=0;
	String key;
	String totalpage="";
	int currPageNum=1;
	Vector bookInfo=new Vector();
	public XMLForm(String title,Display display,int style,String key){
		super(title,display,false);
		super.initUI(XMLForm.this);
		this.title=title;
		this.style=style;
		this.key=key;
	}
	public XMLForm(String title,Display display){
		super(title,display,false);
		this.title=title;
		super.initUI(XMLForm.this);
//		this.topY=topY;
	}
	public XMLForm() {
		// TODO 自动生成构造函数存根
	}

	protected   void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
	}
	protected   void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
	}
	
	public void setKey(String key){
		this.key=key;
	}
	public void setTopY(int topY){
		this.topY=topY;
	}
	public void setPageNum(int num){
		this.currPageNum=num;
	}
	public int getPageNum(){
		return currPageNum;
	}
	public String getTotalPage(){
		return this.totalpage;
	}
	public void setTotalPage(String page,int currIndex){
		this.totalpage=page;
		this.currChapterIndex=currIndex;
	}
	public int getCurrChapterIndex(){
		return this.currChapterIndex;
	}
	private int currChapterIndex;
	private String booktypeid;
	private String booktypename;
	/**
	 * 初始化首页面
	 *
	 */
	public void initMainForm(){
//		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					if(AppContext.isFirst){
						
						Handle.test();
						AppContext.isFirst=false;
					}
//					UIControl.getInstance().startMessageBox();
					/**************搜索框***********************/
					
					SearchButton sb=new SearchButton(leftX,topY,120,30);
					XMLForm.this.add(sb);
					topY+=sb.height;
					bookInfo=RmsManager.getBookStoreList();
					if(bookInfo==null){
						bookInfo=Handle.getMainBookStoreList();
//						System.out.println("    "+bookInfo);
						if(bookInfo!=null){
							RmsManager.saveBookStoreList(bookInfo);
						}
					}
					/**************劲爆新书*******************/
					Label label=new Label(leftX,topY);
					label.setLabel("劲爆新书");	
					label.setlabelStyle(1);
					label.setBookAndType(AppContext.NEWBOOK, null);
					label.addLabelAction();
					label.setPass(false);
					XMLForm.this.add(label);
					topY+=label.height;
					int number=bookInfo.size()/3;
					if(bookInfo!=null)
					for(int i=0;i<number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.NEWBOOK, obj);
						label.setPass(false);
						label.addLabelAction();
						XMLForm.this.add(label);
						topY+=label.height;
					}
					
					
					/**************热门推荐*******************/
//					bookInfo=Handle.getTopBook();
					label=new Label(leftX,topY);
					label.setLabel("热门推荐");	
					label.setlabelStyle(1);
					label.setBookAndType(AppContext.HOTBOOK, null);
					label.addLabelAction();
					label.setPass(false);
					XMLForm.this.add(label);
					topY+=label.height;
					if(bookInfo!=null)
					for(int i=number;i<2*number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.HOTBOOK, obj);
						label.addLabelAction();
						label.setPass(false);
						XMLForm.this.add(label);
						topY+=label.height;
					}
					
					/**************畅销排行*******************/
//					bookInfo=Handle.getTopBook();
					label=new Label(leftX,topY);
					label.setLabel("畅销排行");	
					label.setlabelStyle(1);
					label.setBookAndType(AppContext.MOSTBOOK, null);
					label.addLabelAction();
					label.setPass(false);
					XMLForm.this.add(label);
					topY+=label.height;
					if(bookInfo!=null)
					for(int i=2*number;i<3*number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.HOTBOOK, obj);
						label.addLabelAction();
						label.setPass(false);
						XMLForm.this.add(label);
						topY+=label.height;
					}
					
					UIControl.getInstance().homePage.getUIManager().getMessageBox().stopMessageTimer();
					if(Consts.SYSTEMTYPE==2){
						
						if(!Handle.checkHasUserCode()){
							UIControl.getInstance().homePage.getUIManager().getMessageBox().setMessageMode(3, "下载激活5元，终身免费");
							UIControl.getInstance().homePage.getUIManager().getMessageBox().setMegStyle(2);
						}
					}
					XMLForm.this.repaint();
					
				
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开书城失败", 0);
//					 UIControl.getInstance().stopMessageBox();
					e.printStackTrace();
				}
			}
		}.start();
	}
	/**
	 * 加载精品控件主体
	 *
	 */
	public void loadBookClassic(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			
			public void run(){
				try{
					
					/**************搜索框***********************/
					topY=UIManager.tab_bg.getHeight()+5;
					SearchButton sb=new SearchButton(leftX,topY,120,30);
					XMLForm.this.add(sb);
					topY+=sb.height;
					bookInfo=RmsManager.getClassicList();
					if(bookInfo==null){
						
						bookInfo=Handle.getClassicList();
						if(bookInfo!=null)
						RmsManager.saveClassicList(bookInfo);
					}
					
					/*************重磅好书********************/
					Label label=new Label(leftX,topY);
					label.setLabel("重磅好书");	
					label.addLabelAction();
					label.setBookAndType(AppContext.GOODBOOK, (((Book)bookInfo.elementAt(0))));//
					label.setPass(false);
					label.setlabelStyle(1);
					XMLForm.this.add(label);
					topY+=label.height;
					int number=bookInfo.size()/3;
					for(int i=0;i<number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.HOTBOOK, obj);
						label.addLabelAction();
						label.setPass(false);
						XMLForm.this.add(label);
						topY+=label.height;
					}
					
					
					
					/*************精品回顾********************/
					label=new Label(leftX,topY);
					label.setLabel("精品回顾");	
					label.addLabelAction();
					label.setBookAndType(AppContext.CLASSICBOOK, (((Book)bookInfo.elementAt(2))));//
					label.setPass(false);
					label.setlabelStyle(1);
					XMLForm.this.add(label);
					topY+=label.height;
					
					for(int i=number;i<2*number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.HOTBOOK, obj);
						label.addLabelAction();
						label.setPass(false);
						XMLForm.this.add(label);
						topY+=label.height;
					}
					
					/*************编辑推荐********************/
					label=new Label(leftX,topY);
					label.setLabel("编辑推荐");	
					label.addLabelAction();
					label.setBookAndType(AppContext.TUIJIANCBOOK, (((Book)bookInfo.elementAt(4))));
					label.setPass(false);
					label.setlabelStyle(1);
					XMLForm.this.add(label);
					topY+=label.height;
					
					for(int i=2*number;i<3*number;i++){
						label=new Label(leftX,topY);
						Book obj=(((Book)bookInfo.elementAt(i)));
						label.setLabel(obj.getBookName());
						label.setBookAndType(AppContext.HOTBOOK, obj);
						label.addLabelAction();
						label.setPass(false);
						XMLForm.this.add(label);
						topY+=label.height;
					}
					System.out.println("经典");
					UIControl.getInstance().stopMessageBox();
					UIControl.getInstance().homePage.getUIManager().getMessageBox().stopMessageTimer();
					XMLForm.this.repaint();
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "失败", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 劲爆新书
	 *
	 */
	public void loadNewBookList(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getBestBook();
					if(bookInfo!=null){
//						System.out.println("size===="+bookInfo.size());
						UIControl.getInstance().viewBookListOfType("劲爆新书", bookInfo);
					}else{
						UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
					}
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 热门推荐
	 *
	 */
	public void loadHotBookList(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getTopBook();
					if(bookInfo!=null){
						
						UIControl.getInstance().viewBookListOfType("热门推荐", bookInfo);
					}else{
						UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
					}
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 畅销排行
	 *
	 */
	public void loadMostList(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getMostBook();
					if(bookInfo!=null){
						
						UIControl.getInstance().viewBookListOfType("畅销排行", bookInfo);
					}else{
							UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
						}
					
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 重磅好书
	 *
	 */
	public void loadGoodBook(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getGoodBook();
					if(bookInfo!=null){
						
						UIControl.getInstance().viewBookListOfType("重磅好书", bookInfo);
					}else{
						UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
					}
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 精品回顾
	 *
	 */
	public void loadClassicBook(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getClassicBook();
					if(bookInfo!=null){
						
						UIControl.getInstance().viewBookListOfType("精品回顾", bookInfo);
					}else{
						UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
					}
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 编辑推荐
	 *
	 */
	public void loadTuijianBook(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getTuijianBook();
					if(bookInfo!=null){
						
						UIControl.getInstance().viewBookListOfType("编辑推荐", bookInfo);
					}else{
						UIControl.getInstance().setMessageBox(2, "该列表为空", 0);
					}
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	
	/**
	 * 加载指定类型的书列表
	 *
	 */
	public void loadOneTypeBookList(String booktype2,String name2){
		this.booktypeid=booktype2;
		this.booktypename=name2;
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					bookInfo=Handle.getAllBookByTypeID(booktypeid);
					UIControl.getInstance().viewBookListOfType(booktypename, bookInfo);
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开列表出错", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 加载分类控件主体
	 *
	 */
	public void loadBookType(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			
			public void run(){
				try{
					bookInfo=Handle.getBookType();
//					System.out.println(" size:   "+bookInfo.size());
					int boxWidth=XMLForm.this.frm_Width/3;
					topY=UIManager.tab_bg.getHeight()+5;
					for(int j=0;j<bookInfo.size();j++){
						int index=j;
						Label label=null;
						for(int i=0;i<3;i++){
							if(bookInfo.size()>i+index){
								BookAllTypeBean book=(BookAllTypeBean)bookInfo.elementAt(i+index);
								label=new Label(boxWidth*i+boxWidth/4,topY);
								label.setLabel(book.getBookType());	
								label.setlabelStyle(1);
								label.addLabelAction();
								
								label.setBookAndType(AppContext.BOOKTYPE, book);
								label.setPass(false);
								XMLForm.this.add(label);
								j++;
							}else{		
								break;
							}
							
							
						}
						topY+=label.height;
						
						
					}
					
					
					/**************搜索框***********************/
					
					SearchButton sb=new SearchButton(leftX,topY,120,30);
					XMLForm.this.add(sb);
					topY+=sb.height;
					
					/*************分类********************/
					Vector list=null;
					list=RmsManager.getTypeList();
					if(list==null){
						list=Handle.getTypeList();
						RmsManager.saveBookTypeList(list);
					}
					Label label=null;
					for(int i=0;i<list.size();i+=2){
						
							/***********分类名 *************/
						Book obj=(((Book)list.elementAt(i)));
//							label=new Label(leftX,topY);
							/*label.setLabel(obj.getBookType());	
							label.setlabelStyle(1);
							label.addLabelAction();
							
							label.setBookAndType(AppContext.BOOKTYPEID, obj);
							label.setPass(false);
							XMLForm.this.add(label);
							topY+=label.height;*/
							/************子类1-2**************/
							for(int k=0;k<2;k++){
								
								obj=(((Book)list.elementAt(i+k)));
								label=new Label(leftX,topY);
								label.setLabel(obj.getBookName());
								label.setBookAndType(AppContext.BOOKTYPEID, obj);
								label.addLabelAction();
								label.setPass(false);
								XMLForm.this.add(label);
								topY+=label.height;
							}						
//						}						
						
					}
					list=null;
					bookInfo=null;
					UIControl.getInstance().stopMessageBox();
					System.gc();
					XMLForm.this.repaint();
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "打开分类失败", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 加载书内容 
	 *
	 */
	public void startLoadingContext(){
		UIControl.getInstance().startMessageBox();
		new Thread(){
			public void run(){
				try{
					BookChapterInfoBean contextobj=null;
					if(totalpage==null){
						
						contextobj=Handle.getContentByChapterID(key);
					}else{
						contextobj=Handle.getContentByChapterID(key,currPageNum+"");
					}
					topY=UIManager.top_bg.getHeight();
					XMLForm.this.getUIManager().offsetY=0;
					if(contextobj==null){
						Label label=new Label(leftX,topY);
						label.setLabel("该章节暂无内容");
						XMLForm.this.add(label);
						topY+=label.height;
						label=null;
					}else{
						
							Label label=new Label(leftX,topY);
							label.setLabel(contextobj.getChapterCent());
							label.setBookAndType(AppContext.BOOKCONTEXT, contextobj);
							label.addLabelAction();
							XMLForm.this.add(label);
							topY+=label.height;
							contextobj=null;
							
							label=new Label(leftX,topY);
							label.setLabel("<上页");
							label.setBookAndType(AppContext.UPPAGE, null);
							label.addLabelAction();
							label.setPass(false);
							XMLForm.this.add(label);
							
							label=new Label(leftX+50,topY);
							label.setLabel("下页>");
							label.setBookAndType(AppContext.NEXTPAGE, null);
							label.addLabelAction();
							label.setPass(false);
							XMLForm.this.add(label);
							
//							topY+=label.height;
							label=null;
					}
					
					UIControl.getInstance().displayXMLForm(XMLForm.this);
					XMLForm.this.repaint();
				}catch(Exception e){
//					UIControl.getInstance().setMessageBox(2, "加载内容失败", 0);
					 UIControl.getInstance().stopMessageBox();
				}
			}
		}.start();
	}
	/**
	 * 初始书简介页面
	 *
	 */
	public void initBookIntroPage(){
		UIControl.getInstance().startMessageBox();
		 new Thread(){
			 public void run(){
				 try{
					 bookInfo=Handle.getInfoByBookID(key);
					 if(bookInfo!=null){
						 
						 BookInfoAllBean obj=(((BookInfoAllBean)bookInfo.elementAt(0)));
						 
						 Vector v= RmsManager.getHistoryBookList();
						 boolean found=false;
						 if(v!=null){
							 
							 for(int j=0;j<v.size();j++){
								 if(((HistoryBook)v.elementAt(j)).bookname.equals(obj.getBookName())){
									 found=true;
									 break;
								 }
							 }
						 }
						 if(!found){
							 //没有相同记录，添加进最近阅读
							 HistoryBook hb=new HistoryBook();
							 hb.bookname=obj.getBookName();
							 hb.bookurl=key;
							 RmsManager.saveOneHistoryBook(hb);
							 
						 }
						 Vector list=Handle.getBookChapterByID(key);
						 Image BigImage=null;
						 
							Connect connect=new Connect(Consts.IMG_URL+obj.getPicUrl(),"","GET");
							try {
								byte[] data=connect.queryServer("".getBytes());
								if(data!=null){
									
									BigImage=Image.createImage(data,0,data.length);
								}
								data=null;
							} catch (Exception e) {
								e.printStackTrace();
							}
						 if(obj!=null && list!=null)
							 UIControl.getInstance().bookIntroPage(obj,list,BigImage,key);
						 list=null;
						 v=null;
						 BigImage=null;
					 }else{
//						 UIControl.getInstance().setMessageBox(2, "获取书信息失败", 0); 
						 UIControl.getInstance().stopMessageBox();
					 }
				 }catch(Exception e){
//					 UIControl.getInstance().setMessageBox(2, "加载失败", 0);
					 UIControl.getInstance().stopMessageBox();
					 e.printStackTrace();
				 }
					
			 }
		 }.start();
	}
	public String getKey(){
		return this.key;
	}
	
	/**
	 * 处理label处理事件
	 *
	 */
	public void clickLabel(Label currLabel){
		int type=currLabel.getType();
//		System.out.println("label  "+currLabel.getLabel()+" type=="+type);
		Object obj=currLabel.getBookObj();
		switch(type){
		case AppContext.BOOKTYPE:
			BookAllTypeBean book2=(BookAllTypeBean)obj;
			loadOneTypeBookList(book2.getId(),book2.getBookType());
			break;
		case AppContext.GOODBOOK:
			if(currLabel.getlabelStyle()==1){
				loadGoodBook();
				
			}
			break;
		case AppContext.TUIJIANCBOOK:
			if(currLabel.getlabelStyle()==1){
				loadTuijianBook();
			}
			break;
		case AppContext.CLASSICBOOK:
			if(currLabel.getlabelStyle()==1){
				loadClassicBook();
			}
			break;
			
		case AppContext.MOSTBOOK:
			if(currLabel.getlabelStyle()==1){
				loadMostList();
			}
			break;
		case AppContext.NEWBOOK:
			if(currLabel.getlabelStyle()==0){				
				Book newbook=	(Book)obj;
				this.key=newbook.getBookId();
				System.out.println(".....key....."+key);
				initBookIntroPage();
			}else{
				System.out.println("..........");
				loadNewBookList();
			}
			break;
		case AppContext.HOTBOOK:
			if(currLabel.getlabelStyle()==0){				
				Book hotbook=	(Book)obj;
				this.key=hotbook.getBookId();
				initBookIntroPage();
			}else{
				loadHotBookList();
			}
			break;
		case AppContext.BOOKTYPEID:
			if(currLabel.getlabelStyle()==0){				
				Book book=(Book)obj;
				this.key=book.getBookId();
				initBookIntroPage();
			}else{
				Book book=(Book)obj;
				loadOneTypeBookList(book.getBookTypeId(),book.getBookType());
			}
			break;
		case AppContext.BOOKINTRO:
			
			break;
		case AppContext.BOOKCHAPTER:
			BookChapterAllBean chapter=	(BookChapterAllBean)obj;
//			int lenght=Integer.parseInt(chapter.getLength());
			UIControl.getInstance().viewBookContext(chapter.getChapterId(),chapter.getPage(),currChapterIndex);
			break;
		case AppContext.BOOKSEARCH:
			break;
		case AppContext.BOOKCONTEXT:
			break;
		case AppContext.HISTORYREAD:
			if(currLabel.getlabelStyle()==0){
				
				HistoryBook recent=	(HistoryBook)obj;
				this.key=recent.bookurl;
				initBookIntroPage();
			}else{
				Vector v=null;
				v=RmsManager.getHistoryBookList();
				UIControl.getInstance().historyForm(v);
			}
			break;
		case AppContext.UPPAGE:
			UIControl.getInstance().openPage(0);
			break;
		case AppContext.NEXTPAGE:
			UIControl.getInstance().openPage(1);
			break;
		}
	}
	
	

	
}
