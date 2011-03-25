package com.caimeng.software.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

public class FileOperation {

	private String filename=null;
	private   String rootName=null;
	private FileConnection fc=null;
	private String suffix=".txt";
	private String softname="CM/";
	public int startPosition;
	public boolean insertdata=false;
	private String secondarydir="test";
	/**
	 * 二级目录名
	 * @param dir
	 */
	public FileOperation(String dir,String suffix){
		this.secondarydir=dir;
		if(suffix!=null)
			this.suffix=suffix;
	}
	
	public FileOperation(){
		
	}
	
	/**
	 * 创建打开文件
	 * @param name
	 */
	public boolean initFile(String name){
		if(rootName==null){
			rootName=getRootName();
		}
		softname=softname+secondarydir+"/";
		if(rootName!=null){			
			filename="file:///"+rootName+softname+name+suffix;
			//System.out.println("filename======="+filename);
			try{
				fc=(FileConnection)Connector.open("file:///"+rootName+"CM/");
				if(!fc.exists()){
					fc.mkdir();//新建文件夹  
				}
				fc.close();
				fc=(FileConnection)Connector.open("file:///"+rootName+softname);
				if(!fc.exists()){
					fc.mkdir();//新建文件夹 
				}
				if(name!=null){
					if(name.length()>0){						
						fc=(FileConnection)Connector.open(filename);
						if(!fc.exists()){
							fc.create();//文件不存在则新建
						}
					}
				}
				
			}catch(Exception e){
				//
				e.printStackTrace();
				System.out.println("创建文件失败");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 写文件测试，成功则存储卡存在
	 * @return
	 */
	public boolean checkTest(){
		
		return initFile("");
	}
	/**
	 * 返回盘符
	 * @return
	 */
	/*public static String getRootName(){
		
		return rootName;
	}*/
	
	/**
	 * 检测是否支持存储卡
	 * @return
	 */
	public  String getRootName(){
		try{
			/*String s=System.getProperty("microedition.io.file.FileConnection.version");
			if(s==null){
				return false;
			}*/
			
			Enumeration enums=FileSystemRegistry.listRoots();
			rootName=null;
//			MainController.getInstance().test.append("系统支持的盘符：");
			while(enums.hasMoreElements()){//获取除系统盘符外的第一个存储卡根目录
				rootName=(String)enums.nextElement();
//				MainController.getInstance().test.append(rootName+"    ");
//				System.out.println("设备根目录："+rootName);
				String r=rootName.substring(0, 1);
				if(r.equals("c")){
					
				}else if(rootName.indexOf("root")!=-1){
					
				}else if(rootName.indexOf("SD")!=-1){
					if(rootName.equals("SDRingtones")){
						break;
					}else if(rootName.equals("SDOthers")){
						break;
					}
				}
				else if(r.equals("e")){
					break;
				}
			}
			if(rootName.substring(0, 1).equals("c")){
				//如果遍历后，返回为系统盘，则没有插入内存卡
				return null;
			}else if(rootName.indexOf("root")!=-1){
				return null;
			}else{
//				MainController.getInstance().test.append("系统得到的	:"+rootName);
				if(checkTest()){
					
					return rootName;
				}else{
					return null;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取文件夹下所有文件名
	 * @return
	 */
	
	public String[] getFileList(){
		String [] name=null;
		Vector v=new Vector();
		
		try{
			
			if(fc.isDirectory()){
				Enumeration enums=null;
				enums=fc.list();
				while(enums.hasMoreElements()){
					v.addElement(enums.nextElement());
				}
				int size=v.size();
				if(size==0){
					return null;
				}
				name=new String[size];
				for(int i=0;i<size;i++){
					String str=(String)v.elementAt(i);
					name[i]=str.substring(0, str.length()-4);
				}
			}
			v=null;
		}catch(Exception e){
			System.out.println("获取列表错误");
			e.printStackTrace();
			return null;
		}
		return name;
	}
	
	/**
	 * 添加一行记录,一条记录大小固定为1K
	 * @param data
	 */
	public  boolean addLine(byte[] data){
		try{
		
			//System.out.println("bbbbbbbbbbb"+data.length);			
			OutputStream dos=fc.openOutputStream(); 
			dos.write(data);				
			dos.flush();
			dos.close();
		}catch(Exception e){
			System.out.println("写存储卡错误");
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	public  void closeFile(){
		try{
			if(fc!=null){
				if(fc.isOpen())
					fc.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 返回当前文件流
	 * @return
	 */
	public  byte[] getData(){
		byte[] data=null;
		try{
			
			data=new byte[(int)fc.fileSize()];
			DataInputStream dis=fc.openDataInputStream();
			dis.read(data, 0, data.length);
			dis.close();
			fc.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 删除文件
	 */
	public  boolean deleteFile(){
		try{
			
			if(fc!=null)
			fc.delete();
			
			
		}catch(IOException e){
//			e.printStackTrace();
			System.out.println("删除文件IO异常");
			
			return false;
		}
		return true;
	}
	/**
	 * 判断文件是否存在
	 * @param name
	 * @return
	 */
	public  boolean exist(String name)
	{   
	  try{  softname=softname+secondarydir+"/";
			filename="file:///e:/"+softname+name+suffix;
			fc=(FileConnection)Connector.open(filename);
			if(fc.exists()){
				return true;
			}
		}catch(Exception e)
		{
			
		}
	    return false;
	}
	
	
	public static void deleteAllFile(){
		delDir("file:///e:/CM");
	}
	
	 public static void delDir(String path){
		 try{
//			 System.out.println("name==="+path);
			 FileConnection fc=(FileConnection)Connector.open(path);
			 if(fc.isDirectory()){
				 
				 Enumeration enums=null;
				 enums=fc.list();
				 while(enums.hasMoreElements()){
					 String name=fc.getURL()+enums.nextElement()+"/";
					name=name.substring(0, name.length()-1);
					delDir(name );
				 }
			 }else{
				 fc.delete();
//				 System.out.println("删除文件");
			 }
		 }catch(Exception e){
			 
		 }
	    }
}
