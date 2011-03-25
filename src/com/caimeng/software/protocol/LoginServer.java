package com.caimeng.software.protocol;

import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.network.Connect;

public class LoginServer extends Connect{
	public LoginServer(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public LoginServer(String url,String action){
		super(url,action,"POST");
	}
	
	
	/**
	 * 登录 是否成功  鉴权
	 * @param requestxml
	 * @return
	 */
	public boolean authenticate(String requestxml){
		boolean success=false;
		Object obj=null;
		try {
			obj=queryServerForXML(requestxml);
			if(obj==null){
				obj=queryServerForXML(requestxml);
			}
			if(obj!=null){
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
