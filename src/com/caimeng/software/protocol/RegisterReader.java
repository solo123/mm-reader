package com.caimeng.software.protocol;


import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.network.Connect;

public class RegisterReader extends Connect{

	public RegisterReader(int connect, String ip, int port) {
		super(port, ip, port);
		// TODO 自动生成构造函数存根
	}
	public RegisterReader(String url,String action){
		super(url,action,"POST");
	}
	/**
	 * 注册返回XML
	 * @param requestxml
	 * @return
	 */
	public Object getParser(String requestxml){
		Object obj=null;
		try {
			obj=queryServerForXML(requestxml);
			if(obj==null){
				obj=queryServerForXML(requestxml);
			}
		} catch (AppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
}
