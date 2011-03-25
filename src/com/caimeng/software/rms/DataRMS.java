package com.caimeng.software.rms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;


public class DataRMS {

	/** 记录仓库 */
	private RecordStore rs;
	
	/**
	 * 初始化RMS
	 * 
	 * @param size	RMS大小
	 * @param name	RMS名称
	 * @return	初始化成功返回true 否则返回false
	 */
	public boolean init(String name)
	{
		try {
			
			rs = RecordStore.openRecordStore(name, true);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 删除数据
	 * 
	 * @param name
	 * @return	删除成功返回true 否则返回false
	 */
	public static boolean deleteRMS(String name)
	{
		try {
			
			RecordStore.deleteRecordStore(name);
		} catch (RecordStoreException e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean checkRoom()
	{
		//RecordStore.
		try {
			return rs.getSizeAvailable()>0;
		} catch (RecordStoreNotOpenException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 获得符合指定前缀名称的RMS
	 * 
	 * @param rmsPrefix		rms前缀名
	 * @return
	 */
	public static Vector getRMSList(String rmsPrefix)
	{
		String[] name = RecordStore.listRecordStores();
		Vector v = new Vector();
		if(name!=null)
		{
			for(int i=0;i<name.length;i++)
			{
				if(name[i].length()>rmsPrefix.length())
				{
					String s = name[i].substring(0, rmsPrefix.length());
					if(rmsPrefix.equals(s))
					{
						v.addElement(name[i]);
					}
				}
			}
		}
		return v;
	}

	/**
	 * 是否分配了指定的RMS
	 * 
	 * @param name	指定的RMS名
	 * @return	如果有则返回true
	 */
	public static boolean isAllocateRMS(String name)
	{
		String[] str = RecordStore.listRecordStores();
//		for(int i=0;i<str.length;i++)
//		{
//			System.out.println("DataRMS.isAllocateRMS()->"+str[i]+" : "+name);
//		}
		if(str!=null)
		{
			for(int i=0;i<str.length;i++)
			{
				if(str[i].equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}
	//////////////////////////////////////////////////////////////////////////////////
	
	public boolean setData(int id, byte[] data)
	{
		try {
			int num = rs.getNumRecords();
			if(id>num)
			{
				byte[] b1 = {(byte)0};
				for(;num<id;num++)
				{
					rs.addRecord(b1, 0, b1.length);
//					System.out.println("添加。。。。");
				}
			}
			rs.setRecord(id, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	
	}
	
	public boolean addData(byte[] data){
		try{
			rs.addRecord(data, 0, data.length);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int getIndexSum(){
		int sum=0;
		try{
			sum=rs.getNumRecords();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sum;
	}
	
	/**
	 * 获取指定ID的记录
	 * 
	 * @param id	记录id
	 * @return
	 */
	public byte[] getData(int id)
	{
		byte[] data = null;
		try {
			if(id>rs.getNumRecords())
			{
				return null;
			}
			data = rs.getRecord(id);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}
	
	/**
	 * 获取已记录数量
	 * 
	 * @return	如果出错则返回-1
	 */
	public int size()
	{
		try {
			return rs.getNumRecords();
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 关闭记录仓库
	 * @return
	 */
	public boolean closeRMS()
	{
		try {
			if(rs!=null)
			{
				rs.closeRecordStore();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
