package com.caimeng.software.protocol;

import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.network.Connect;

public class CatalogInfo extends Connect{
	public CatalogInfo(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public CatalogInfo(String url,String action,String methed){
		super(url,action,methed);
	}
	/**
	 *获取专区 是否成功 
	 * @param requestxml  为空
	 * @return
	 */
	public boolean getCatalogInfo(String requestxml){
		boolean success=false;
		byte[] output=null;
		try {
			System.out.println("获取专区");
			output=this.queryServer(requestxml.getBytes());
			if(output!=null){
				success=true;
			}
		} catch (AppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
}
