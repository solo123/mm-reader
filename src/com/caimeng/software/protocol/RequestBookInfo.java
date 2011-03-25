package com.caimeng.software.protocol;

import java.io.InputStream;
import java.util.Vector;

import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;
import com.caimeng.software.network.HttpChannel;
import com.caimeng.software.readerobj.BookAllTypeBean;
import com.caimeng.software.readerobj.BookChapterAllBean;
import com.caimeng.software.readerobj.BookChapterInfoBean;
import com.caimeng.software.readerobj.BookInfoAllBean;
import com.caimeng.software.readerobj.Book;
import com.caimeng.software.readerobj.SimpleBook;
import com.caimeng.software.xml.KXmlParser;
import com.caimeng.software.xml.XmlPullParser;
import com.caimeng.software.xml.XmlPullParserException;

public class RequestBookInfo extends Connect{

	public RequestBookInfo(String ip, int port) {
		super(ip, port);
	}
	public RequestBookInfo(String url,String action,String methed){
		
		super(url,action,methed);
	}
	/**
	 * 获取章节内容
	 * @return
	 */
	public BookChapterInfoBean getBookChapterContext(){
		BookChapterInfoBean obj=null;
		try{
			byte[] output=this.queryServer("".getBytes());
			if(output!=null){
				obj=new BookChapterInfoBean();
				String text=new String(output,"UTF-8");
				if(text.indexOf("onenterforward")!=-1){
					text=null;
					return getBookChapterContext();
				}else{
					
					obj.setChapterCent(text);
				}
			
		
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 
	 * @param in
	 * @param style 分类0 新书1 排行2  获取类型下的书 3  获取书简介 4  获取每本书所有章节 5 搜索书 6 获取书内容 7
	 * @return
	 * @throws XmlPullParserException 
	 * @throws Exception
	 */
	public Vector getRequestInfo(int style) throws Exception{
			Vector v = null;
			InputStream in=queryServerForStream();	
//			Consts.test.append("返回码"+in);
//			if(style==7){
//				byte[] data=new byte[in.available()];
//				while(int)
//				return v;
//			}
			KXmlParser xmlpull = new KXmlParser();
			xmlpull.setInput(in, "utf-8");
			int eventCode = xmlpull.getEventType();
			if (style == 0) {
				BookAllTypeBean bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new BookAllTypeBean();
						} else if (bean != null) {
							if (("BOOKID".equalsIgnoreCase(xmlpull.getName()))) {
								bean.setId(xmlpull.nextText());
							} else if ("BOOKTYPE".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookType(xmlpull.nextText());
							}
						}
						break;
					}

					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
				/*System.out.println("-------------书的分类---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookAllTypeBean) v.elementAt(k);
					System.out.println(bean.getId() + "   " + bean.getBookType());
				}
				System.out.println();*/
			} else if (style == 1 || style == 2) {
				Book bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new Book();
						} else if (bean != null) {
							if (("BOOKTYPEID".equalsIgnoreCase(xmlpull.getName()))) {
								bean.setBookTypeId(xmlpull.nextText());
							} else if ("BOOKTYPE".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookType(xmlpull.nextText());
							} else if ("BOOKID".equalsIgnoreCase(xmlpull.getName())) {
								bean.setBookId(xmlpull.nextText());
							} else if ("BOOKNAME".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookName(xmlpull.nextText());
							}
						}
						break;
					}

					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())	&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
				/*System.out.println("-------------新书或者阅读最多的书---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookInfoSimpBean) v.elementAt(k);
					System.out.println(bean.getBookTypeId() + "   "
							+ bean.getBookType() + "   " + bean.getBookId() + "   "
							+ bean.getBookName());
				}
				System.out.println();*/
			} else if (style == 3 || style == 6) {
				SimpleBook bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new SimpleBook();
						} else if (bean != null) {
							if ("BOOKID".equalsIgnoreCase(xmlpull.getName())) {
								bean.setBookId(xmlpull.nextText());
							} else if ("BOOKNAME".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookName(xmlpull.nextText());
							}
						}
						break;
					}

					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())	&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
			/*	System.out.println("-------------获取每个类型的书或搜索书---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookNameBean) v.elementAt(k);
					System.out.println(bean.getBookId() + "   "
							+ bean.getBookName());
				}
				System.out.println();*/
			} else if (style == 4) {
				BookInfoAllBean bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new BookInfoAllBean();
						} else if (bean != null) {
							if (("AUTHOR".equalsIgnoreCase(xmlpull.getName()))) {
								bean.setAuthor(xmlpull.nextText());
							} else if ("BOOKINTRO".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookIntro(xmlpull.nextText());
							} else if ("STATUS".equalsIgnoreCase(xmlpull.getName())) {
								bean.setStatus(xmlpull.nextText());
							} else if ("BOOKNAME".equalsIgnoreCase(xmlpull	.getName())) {
								bean.setBookName(xmlpull.nextText());
							} else if ("HOT".equalsIgnoreCase(xmlpull.getName())) {
								bean.setHot(xmlpull.nextText());
							}else if ("PICURL".equalsIgnoreCase(xmlpull.getName())) {
								bean.setPicUrl(xmlpull.nextText());
							}
						}
						break;
					}
					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())	&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
				/*System.out.println("-------------获取每本书的资料---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookInfoAllBean) v.elementAt(k);
					System.out.println(bean.getBookName() + "   "
							+ bean.getAuthor() + "   " + bean.getBookIntro());
				}
				System.out.println();*/
			} else if (style == 5) {
				BookChapterAllBean bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new BookChapterAllBean();
						} else if (bean != null) {
							if (("CHAPTERID".equalsIgnoreCase(xmlpull.getName()))) {
								bean.setChapterId(xmlpull.nextText());
							} else if ("CHAPTERNAME".equalsIgnoreCase(xmlpull.getName())) {
								bean.setChapterName(xmlpull.nextText());
							} else if ("LENGTH".equalsIgnoreCase(xmlpull.getName())) {
								bean.setLength(xmlpull.nextText());
							}else if ("PAGE".equalsIgnoreCase(xmlpull.getName())) {
								bean.setPage(xmlpull.nextText());
							}
						}
						break;
					}

					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())	&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
				/*System.out.println("-------------获取每个书的章节---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookChapterAllBean) v.elementAt(k);
					System.out.println(bean.getChapterId() + "   "
							+ bean.getChapterName() + "   " + bean.getLength());
				}
				System.out.println();*/
			} else if (style == 7) {
				BookChapterInfoBean bean = null;
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
						v = new Vector();
						break;
					}
					case XmlPullParser.START_TAG: {
						if ("BOOK".equalsIgnoreCase(xmlpull.getName())) {
							bean = new BookChapterInfoBean();
						} else if (bean != null) {
							if (("CHAPTERCENT".equalsIgnoreCase(xmlpull.getName()))) {
								bean.setChapterCent(xmlpull.nextText());
							}
						}
						break;
					}

					case XmlPullParser.END_TAG: {
						if ("Book".equalsIgnoreCase(xmlpull.getName())&& bean != null) {
							v.addElement(bean);
							bean = null;
						}
						break;
					}
					}
					eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
				}
				/*System.out.println("-------------书的章节的内容---------------");
				System.out.println("得到数据长度：" + v.size());
				for (int k = 0; k < v.size(); k++) {
					bean = (BookChapterInfoBean) v.elementAt(k);
					System.out.println(bean.getChapterCent().length());
				}
				System.out.println();*/
			}
			this.close();
			xmlpull=null;
			if(v!=null ){
				if(v.size()==0){
					v=null;
				}
			}
			return v;
		
	}

}
