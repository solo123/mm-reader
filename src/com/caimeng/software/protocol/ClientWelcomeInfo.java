package com.caimeng.software.protocol;

import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.network.Connect;

public class ClientWelcomeInfo extends Connect{
	public ClientWelcomeInfo(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public ClientWelcomeInfo(String url,String action,String methed){
		super(url,action,methed);
	}
	/**
	 * 欢迎界面返回XML
	 * @param requestxml
	 * @return
	 */
	public Object getClientWelcomeInfo(String requestxml){
		Object obj=null;
		try {
			obj=this.queryServerForXML("");
			if(obj==null){
				obj=this.queryServerForXML("");
			}
		} catch (AppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
