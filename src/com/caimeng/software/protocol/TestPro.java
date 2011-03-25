package com.caimeng.software.protocol;

import com.caimeng.software.binaryprotocol.AppException;
import com.caimeng.software.io.FileManager;
import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;

public class TestPro extends Connect  {

	public TestPro(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public TestPro(String url, String action, String methed) {
		super(url, "", methed);
	}
	
	public String testOne(String parameic){
		try {
			byte[] output = this.queryServer(parameic.getBytes());
//			Consts.test.append(url+"\n");
//			Consts.test.append("返回数据:"+output+"\n");
			if(output!=null){
				String backStr=new String(output,"utf-8");
				
				return backStr;
//				Consts.test.append(new String(output,"utf-8")+"\n");
			}
//			FileManager.writeFile(filename, context)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
//		FileManager.writeFile("one", Consts.test.toString());
	}

}
