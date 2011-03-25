package com.caimeng.software.protocol;

import java.util.Vector;

import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;

public class GameActivatePath extends Connect{
	public GameActivatePath(int connect, String ip, int port) {
		super(connect, ip, port);
	}
	public GameActivatePath(String url,String action,String methed){
		super(url,action,methed);
	}
	int time=0;
	public String getGameActivatePath(String parameic){
		String  path=null;
		String result=null;
		byte[] output=null;
		try{
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output=this.queryServer(parameic.getBytes());
			if(output!=null){
				result=new String(output,"UTF-8");
				//分析地址
				int index = result.indexOf("buySalesPackage");
				
				if (index == -1) {
					index=result.indexOf(Consts.HASBUY);
					if(index!=-1){//确实已经订购						
						return Consts.HASBUY;
					}
				} else {
					time=0;
					String tempURL = "";
					int start=0;
					int end=0;
					for(int i=index;i>0;i--){
						if(result.charAt(i)=='"'){
							start=i;
							break;
						}
					}
					for(int i=index;i<result.length();i++){
						if(result.charAt(i)=='"'){
							end=i;
							break;
						}
					}
					
					// 获取订购地址
					tempURL = result.substring(start+1, end);
					path ="http://" + server + tempURL;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return path;
	}
	public boolean startGameActivate(String parameic){
		byte[] output=null;
		try{
			output=this.queryServer(parameic.getBytes());
		}catch(Exception e){
			
		}
		return output==null?false:true;
		
	}
	
	public String[] getGameActivateAllPath(String parameic) {
		String path = null;
		String result = null;
		byte[] output = null;
		Vector v = new Vector();
		String [] str=null;
		try {
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output = this.queryServer(parameic.getBytes());
			if (output != null) {
				result = new String(output, "UTF-8");
				for (int k = 0; k < 10; k++) {
					// 分析地址
					int index = result.indexOf("jsessionid=");
					if (index == -1) {
					} else {
						String tempURL = "";
						int start = 0;
						int end = 0;
						for (int i = index; i > 0; i--) {
							if (result.charAt(i) == '"') {
								start = i;
								break;
							}
						}
						for (int i = index; i < result.length(); i++) {
							if (result.charAt(i) == '"') {
								end = i;
								break;
							}
						}

						// 获取订购地址
						tempURL = result.substring(start + 1, end);
						path = "http://" + server + tempURL;
						v.addElement(path);
						result=result.substring(end);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(v!=null&&v.size()>0){
			str=new String[v.size()];
			for(int i=0;i<v.size();i++){
				str[i]=(String)v.elementAt(i);
			}
		}
		return str;
	}

}
