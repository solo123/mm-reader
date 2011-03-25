package com.caimeng.software.rms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;

import com.caimeng.software.readerobj.Book;
import com.caimeng.software.readerobj.SimpleBook;
import com.caimeng.software.readerobj.HistoryBook;
import com.caimeng.uilibray.utils.ServiceData;

public class RmsManager {
	final static String USERTYPE="user_type";
	final static String SERVICE="service";
	final static String USERID="user_id";
	final static String HISTORY="history";
	final static String TYPELIST="typelist";
	final static String BOOKSTORE="bookstore";
	final static String CLASSIC="classic";
	final static String CHARGES="charges";//收费章节
	/**
	 * 保存USERid
	 * @param userid
	 */
	public static void saveUserID(String userid){
		DataRMS rms=new DataRMS();
		rms.init(USERID);
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeUTF(userid);
			rms.setData(1, bos.toByteArray());
			
			dos.close();
			bos.close();
			rms.closeRMS();
			
		}catch(Exception e){
			rms.closeRMS();
//			e.printStackTrace();
		}
	}
	/**
	 * 获取userid
	 * @return
	 */
	public static String getUserID(){
		String userid="";
		
		DataRMS rms=new DataRMS();
		rms.init(USERID);
		try{
			if(rms.getIndexSum()==0){
				rms.closeRMS();
				return "";
			}
			
			byte[] data=rms.getData(1);
			ByteArrayInputStream bais=new ByteArrayInputStream(data);
			DataInputStream dis=new DataInputStream(bais);
			userid=dis.readUTF();
			dis.close();
			bais.close();
			rms.closeRMS();
		}catch(Exception e){
			rms.closeRMS();
//			e.printStackTrace();
			return userid;
		}
		return userid;
	}
	
	/**
	 * 
	 * @param code
	 * @param type   1为code  2为type
	 */
	public static void saveUserCodeOrType(String code,int type){
		if(type>0&& type<3){
			
			DataRMS rms=new DataRMS();
			rms.init(USERTYPE);
			try{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				dos.writeUTF(code);
				rms.setData(type, bos.toByteArray());
				
				dos.close();
				bos.close();
				rms.closeRMS();
				
			}catch(Exception e){
				rms.closeRMS();
//				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param type 1为code  2为type
	 * @return
	 */
	public static String getUserCodeOrType(int type){
		String str=null;
		if(type>0&& type<3){
			
			DataRMS rms=new DataRMS();
			rms.init(USERTYPE);
			try{
				if(rms.getIndexSum()<type){
					rms.closeRMS();
					return null;
				}
				
				byte[] data=rms.getData(type);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				str=dis.readUTF();
				dis.close();
				bais.close();
				rms.closeRMS();
			}catch(Exception e){
				rms.closeRMS();
//				e.printStackTrace();
				return str;
			}
		}
	
		
		return str;
	}
	/**
	 * 保存，设置
	 * @param data
	 */
	public synchronized static void saveServiceSetting(ServiceData data){
		DataRMS rms=new DataRMS();
		rms.init(SERVICE);
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeUTF(data.OPERATE);
			dos.writeUTF(data.SERVICE);
			dos.writeUTF(data.FEECODE);
			dos.writeUTF(data.MSG1);
			dos.writeUTF(data.MSG2);
			dos.writeUTF(data.MSG3);
			dos.writeUTF(data.MSG4);
			dos.writeUTF(data.MSG5);
			rms.setData(1, bos.toByteArray());
			dos.close();
			bos.close();
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
		}
		
	}
	/**
	 * 返回设置对象
	 * @return
	 */
	public synchronized  static ServiceData getServiceSetting(){
		DataRMS rms=new DataRMS();
		rms.init(SERVICE);
		ServiceData service=new ServiceData();
		try{
			if(rms.getIndexSum()==0){
				rms.closeRMS();
				return service;
			}
			byte[] data=rms.getData(1);
			ByteArrayInputStream bais=new ByteArrayInputStream(data);
			 DataInputStream dis=new DataInputStream(bais);
			 service.OPERATE=dis.readUTF();
			 service.SERVICE=dis.readUTF();
			 service.FEECODE=dis.readUTF();
			 service.MSG1=dis.readUTF();
			 service.MSG2=dis.readUTF();
			 service.MSG3=dis.readUTF();
			 service.MSG4=dis.readUTF();
			 service.MSG5=dis.readUTF();
		/*	 System.out.println("service.OPERATE1"+service.OPERATE);
			 System.out.println("service.OPERATE2"+service.SERVICE);
			 System.out.println("service.OPERATE3"+service.FEECODE);
			 System.out.println("service.OPERATE4"+service.MSG1);
			 System.out.println("service.OPERATE5"+service.MSG2);
			 System.out.println("service.OPERATE6"+service.MSG3);
			 System.out.println("service.OPERATE7"+service.MSG4);
			 System.out.println("service.OPERATE8"+service.MSG5);*/
			 dis.close();
			 bais.close();
			 rms.closeRMS();
			
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
			return new ServiceData();
			
		}
		return service;
	}
	/**
	 * 添加一条历史记录
	 * @param hb
	 */
	public static void  saveOneHistoryBook(HistoryBook hb){
		DataRMS rms=new DataRMS();
		rms.init(HISTORY);
		try{
			
			if(rms.getIndexSum()>=20){
				//记录前20
				Vector list=getHistoryBookList();
				for(int i=1;i<list.size();i++){//满记录时，删除最早一条记录
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(bos);
					dos.writeUTF(((HistoryBook)list.elementAt(i)).bookname);
					dos.writeUTF(((HistoryBook)list.elementAt(i)).bookurl);
					rms.addData(bos.toByteArray());
					dos.close();
					bos.close();
					
				}
			}
			/*******在末尾添加当前条*********/
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeUTF(hb.bookname);
			dos.writeUTF(hb.bookurl);
			rms.addData(bos.toByteArray());
			dos.close();
			bos.close();
			
			rms.closeRMS();
			
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
		}
	}
	/**
	 *获取历史记录列表
	 * @return
	 */
	public static Vector getHistoryBookList(){
		Vector list=new Vector();
		DataRMS rms=new DataRMS();
		rms.init(HISTORY);
		try{
			int sum=rms.getIndexSum();
			if(sum==0){
				rms.closeRMS();
				return null;
			}
			for(int i=1;i<=sum;i++){				
				byte[] data=rms.getData(i);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				HistoryBook hb=new HistoryBook();
				hb.bookname=dis.readUTF();
				hb.bookurl=dis.readUTF();
				dis.close();
				bais.close();
				list.addElement(hb);
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
			
		}
		return list;
	}
	/**
	 * 保存一个列表，先清空RMS   维持RMS ID连续
	 * @param historylist
	 */
	public static void saveHistoryBookList(Vector historylist){
		if(historylist==null){
			return;
		}
		deleteHistoryBook();
		DataRMS rms=new DataRMS();
		rms.init(HISTORY);
		try{
			for(int i=0;i<historylist.size();i++){//满记录时，删除最早一条记录
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				dos.writeUTF(((HistoryBook)historylist.elementAt(i)).bookname);
				dos.writeUTF(((HistoryBook)historylist.elementAt(i)).bookurl);
				rms.addData(bos.toByteArray());
				dos.close();
				bos.close();
				
			}
			rms.closeRMS();
		}catch(Exception e){
			rms.closeRMS();
			e.printStackTrace();
		}
		
	}
	/**
	 * 清空历史
	 *
	 */
	public static void deleteHistoryBook(){
		DataRMS.deleteRMS(HISTORY);
	}
	/**
	 * 临时保存分类列表 
	 * @param typlelist
	 */
	public static void saveBookTypeList(Vector typlelist){
		DataRMS rms=new DataRMS();
		rms.init(TYPELIST);
		try{
			for(int i=0;i<typlelist.size();i++){
//				if(typlelist.elementAt(i)!=null){
					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(bos);
					dos.writeUTF(((Book)typlelist.elementAt(i)).getBookId());
					dos.writeUTF(((Book)typlelist.elementAt(i)).getBookName());
					dos.writeUTF(((Book)typlelist.elementAt(i)).getBookTypeId());
					dos.writeUTF(((Book)typlelist.elementAt(i)).getBookType());
					rms.addData(bos.toByteArray());
					dos.close();
					bos.close();
//				}
				
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
			
		}
	}
	/**
	 * 删除分类
	 *
	 */
	public static void deleteTypeList(){
		DataRMS.deleteRMS(TYPELIST);
	}
	
	/**
	 * 获取分类临时  
	 * @return
	 */
	public static Vector getTypeList(){
		Vector list=new Vector();
		DataRMS rms=new DataRMS();
		rms.init(TYPELIST);
		try{
			int sum=rms.getIndexSum();
			if(sum==0){
				rms.closeRMS();
				return null;
			}
			for(int i=1;i<=sum;i++){				
				byte[] data=rms.getData(i);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				Book book=new Book();
				book.setBookId(dis.readUTF());
				book.setBookName(dis.readUTF());
				book.setBookTypeId(dis.readUTF());
				book.setBookType(dis.readUTF());
				
				dis.close();
				bais.close();
				list.addElement(book);
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 删除书城所有记录
	 *
	 */
	public static void deleteBookStoreList(){
		DataRMS.deleteRMS(BOOKSTORE);
	}
	/**
	 * 添加一条书城 书信息
	 * @param obj
	 */
	public static void saveBookStoreList(Vector list){
		DataRMS rms=new DataRMS();
		rms.init(BOOKSTORE);
		try{
			for(int i=0;i<list.size();i++	){
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				dos.writeUTF(((Book)list.elementAt(i)).getBookId());
				dos.writeUTF(((Book)list.elementAt(i)).getBookName());
				dos.writeUTF(((Book)list.elementAt(i)).getBookTypeId());
				dos.writeUTF(((Book)list.elementAt(i)).getBookType());
				rms.addData(bos.toByteArray());
				dos.close();
				bos.close();
			}
			rms.closeRMS();
		}catch(Exception e){
			rms.closeRMS();
			e.printStackTrace();
		}
	}

	/**
	 * 返回书城
	 * @return
	 */
	public static Vector getBookStoreList(){
		Vector v=new Vector();
		DataRMS rms=new DataRMS();
		rms.init(BOOKSTORE);
		try{
			int sum=rms.getIndexSum();
			if(sum==0){
				rms.closeRMS();
				return null;
			}
			for(int i=1;i<=sum;i++){
				byte[] data=rms.getData(i);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				Book book=new Book();
				book.setBookId(dis.readUTF());
				book.setBookName(dis.readUTF());
				book.setBookTypeId(dis.readUTF());
				book.setBookType(dis.readUTF());
				dis.close();
				bais.close();
				v.addElement(book);
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
		}
		return v;
	}
	/**
	 * 获取精品列表
	 * @return
	 */
	public static Vector getClassicList(){
		Vector v=new Vector();
		DataRMS rms=new DataRMS();
		rms.init(CLASSIC);
		try{
			int sum=rms.getIndexSum();
			if(sum==0){
				rms.closeRMS();
				return null;
			}
			for(int i=1;i<=sum;i++){
				byte[] data=rms.getData(i);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				Book book=new Book();
				book.setBookId(dis.readUTF());
				book.setBookName(dis.readUTF());
				book.setBookTypeId(dis.readUTF());
				book.setBookType(dis.readUTF());
				dis.close();
				bais.close();
				v.addElement(book);
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
		}
		return v;
	}
	
	/**
	 * 临时保存精品列表 
	 * @param typlelist
	 */
	public static void saveClassicList(Vector classiclist){
		DataRMS rms=new DataRMS();
		rms.init(CLASSIC);
		try{
			for(int i=0;i<classiclist.size();i++){
					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(bos);
					dos.writeUTF(((Book)classiclist.elementAt(i)).getBookId());
					dos.writeUTF(((Book)classiclist.elementAt(i)).getBookName());
					dos.writeUTF(((Book)classiclist.elementAt(i)).getBookTypeId());
					dos.writeUTF(((Book)classiclist.elementAt(i)).getBookType());
					rms.addData(bos.toByteArray());
					dos.close();
					bos.close();
				
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
			
		}
	}
	/**
	 * 删除精品所有记录
	 *
	 */
	public static void deleteClassicList(){
		DataRMS.deleteRMS(CLASSIC);
	}
	/**
	 * 保存内容id  章节id
	 * @param contentid
	 * @param chapterid
	 */
	public static void saveChanges(String contentid,String chapterid){
		DataRMS rms=new DataRMS();
		rms.init(CHARGES);
		try{
					
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeUTF(contentid);
			dos.writeUTF(chapterid);
			rms.addData(bos.toByteArray());
			dos.close();
			bos.close();
				
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
			
		}
	}
	/**
	 * 所有订购内容+章节id  Vecotr 格式 ：
	 * @return
	 */
	public static Vector getAllChanges(){
		Vector v=null;
		DataRMS rms=new DataRMS();
		rms.init(CHARGES);
		try{
			int sum=rms.getIndexSum();
			if(sum==0){
				rms.closeRMS();
				return null;
			}
			v=new Vector();
			for(int i=1;i<=sum;i++){
				byte[] data=rms.getData(i);
				ByteArrayInputStream bais=new ByteArrayInputStream(data);
				DataInputStream dis=new DataInputStream(bais);
				v.addElement(dis.readUTF());
				v.addElement(dis.readUTF());
				dis.close();
				bais.close();
			}
			rms.closeRMS();
		}catch(Exception e){
			e.printStackTrace();
			rms.closeRMS();
		}
		return v;
	}
}
