package com.caimeng.software.protocol;

import com.caimeng.software.network.Connect;

public class ColorDreamChannel extends Connect{

	public ColorDreamChannel(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public ColorDreamChannel(String url,String action,String methed){
		super(url,action,methed);
	}
	
	public Object getColorDream(String parameic){
		Object obj=null;
		byte[] output=null;
		try{
			output=this.queryServer(parameic.getBytes());
			if(output!=null){
				obj=new String(output,"UTF-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return obj;
	}
}
