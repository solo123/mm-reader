package com.caimeng.software.protocol;

import com.caimeng.software.network.Connect;
/**
 * 免费pv服务 （免费过程pv、免费章节pv）
 */
public class RefreshPv extends Connect{
	
	
	public RefreshPv(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public RefreshPv(String url,String action,String methed){
		super(url,action,methed);
	}
	
	/**
	 * 
	 * @param parameic
	 * @return
	 */
	public Object doRefreshPv(String parameic){
		byte[] output=null;
		try{
			output=this.queryServer(parameic.getBytes());
		}catch(Exception e){
			
		}
		return output;
		
	}

}
