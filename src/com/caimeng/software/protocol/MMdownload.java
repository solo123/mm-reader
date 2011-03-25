package com.caimeng.software.protocol;

import java.util.Random;

import javax.microedition.lcdui.Image;

import com.caimeng.software.io.FileManager;
import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;
import com.caimeng.system.control.Handle;
import com.caimeng.uilibray.utils.ImageCodeAnalyzing;

public class MMdownload extends Connect {

	public MMdownload(int connect, String ip, int port) {
		super(connect, ip, port);
	}

	public MMdownload(String url, String action, String methed) {
		super(url, "startGameActivate", methed);
	}
	public MMdownload(String url, String action, String methed,int kb) {
		super(url, "startGameActivate", methed,kb);
	}

	int time = 1;

	/**
	 * 第一步（得到“购买”路径）
	 * 
	 * @return
	 */
	public String doStepOne(String parameic) {
		String path = null;
		String result = null;
		byte[] output = null;
		try {
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output = this.queryServer(parameic.getBytes());
			Consts.test.append(" 第一步 \n");
			Consts.test.append(super.url + " \n");
			if (output != null) {
				result = new String(output, "UTF-8");
				Consts.test.append(result + " \n");
				// 分析地址
				int index = result.indexOf("[购买]");
				if (index != -1) {
					int end = 0;
					int start = 0;
					for (int i = index; i > 0; i--) {
						if (end==0&&(result.charAt(i) == '"' || result.charAt(i) == '\'')) {
							end = i;
							continue;
						}
						if (result.charAt(i) == '"' || result.charAt(i) == '\'') {
							start = i;
							break;
						}
					}
					path = result.substring(start + 1, end);
					index=path.indexOf("content.brand");
					if(index!=-1){
						String device="";
						if(Handle.serviceData.MSG2.indexOf("/")!=-1){							
							device=Handle.serviceData.MSG2.substring(0,Handle.serviceData.MSG2.indexOf("/"));
						}
						if(device.equals("")){
							device="NokiaE75";
						}
						//把路径中requestid=content.brand替换为var=2
						index=path.indexOf("requestid=content.brand");
						path=path.substring(0,index)+"var=2"+path.substring(index+"requestid=content.brand".length());
						//把路径中的dps.do改为appc.dl
						index=path.indexOf("dps.do");
						path=path.substring(0,index)+"appc.dl"+path.substring(index+"dps.do".length())+"&device="+device;
						
//						path=path.substring(0,index)+"content.download"+path.substring(index+"content.brand".length())+"&device=NokiaN81";
					}
					path = "http://" + server + "/" + path;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Consts.test.append(" \n");
		System.out.println("path=="+path);
		return path;
	}

	/**
	 * 第二步(得到 “确认下载” 路径)
	 * 
	 * @return
	 */
	public String doStepTwo(String parameic) {
		String path = null;
		String result = null;
		byte[] output = null;
		try {
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output = this.queryServer(parameic.getBytes());
			Consts.test.append("  第二步 \n");
			Consts.test.append(super.url + " \n");
			if (output != null) {
				result = new String(output, "UTF-8");
				Consts.test.append(result + " \n");
				// 分析地址
				int index = result.indexOf("action=\"/appc.dl");
				if(index==-1){
					index = result.indexOf("action=\'/appc.dl");
				}
				if (index != -1) {
					int end = 0;
					int start = 0;
					start=index+"action=".length()+1;
					for (int i = start+1; i < result.length(); i++) {
						if (result.charAt(i) == '"' || result.charAt(i) == '\'') {
							end = i;
							break;
						}
					}
					path = result.substring(start + 1, end);
					while(true){
						index=path.indexOf("amp;");
						if(index==-1){
							break;
						}
						path=path.substring(0,index)+path.substring(index+4);
						
					}
					path = "http://" + server  +"/"+ path;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Consts.test.append(" \n");
		return path;
	}
	/**
	 * 第二步 得到图片验证码
	 * 
	 * @return
	 */
	public String doStepTwo2(String parameic) {
		String result = null;
		byte[] output = null;
		try {
			output = this.queryServer(parameic.getBytes());
			Consts.test.append("  第二步 获取图片验证码    \n");
			Consts.test.append(super.url + " \n");
			if (output != null) {
				result=ImageCodeAnalyzing.getCodeFromImage(Image.createImage(output, 0, output.length));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 第三步
	 * 
	 * @return
	 */
	public String doStepThree(String parameic) {
		String path = null;
		byte[] output = null;
		try {
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output = this.queryServer(parameic.getBytes());
			Consts.test.append("  第三步 \n");
			Consts.test.append(super.url + " \n");
			path=Consts.LOCATION_PATH;
			if (output != null) {
				String result = new String(output, "UTF-8");
				Consts.test.append(result + " \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Consts.test.append(" \n");
		return path;
	}
	/**
	 * 第四步
	 * 
	 * @return
	 */
	public String[] doStepFour(String parameic) {
		String result = null;
		byte[] output = null;
		String[] paths=new String[2];
		try {
			output = this.queryServer(parameic.getBytes());
			Consts.test.append("  第四步 \n");
			Consts.test.append(super.url + " \n");
			if (output != null) {
				result = new String(output, "UTF-8");
				Consts.test.append(result + " \n");
				// 分析地址
				int index0 = result.indexOf("MIDlet-Jar-URL");
				int index1 = result.indexOf("MIDlet-Install-Notify");
				int index=0;
				if (index0 != -1&&index1 != -1){
					if(index0<index1){
						index0=index0+"MIDlet-Jar-URL:".length();
						result=result.substring(index0);
						index=result.indexOf("MIDlet");
						if(index!=-1){
							paths[0]=result.substring(0, index).trim();
						}else{
							paths[0]=result;
						}
						
						index1 = result.indexOf("MIDlet-Install-Notify");
						index1=index1+"MIDlet-Install-Notify:".length();
						result=result.substring(index1);
						index=result.indexOf("MIDlet");
						if(index!=-1){
							paths[1]=result.substring(0, index).trim();
						}else{
							paths[1]=result.trim();
						}
					}else{
						index1=index1+"MIDlet-Install-Notify:".length();
						result=result.substring(index1);
						index=result.indexOf("MIDlet");
						if(index!=-1){
							paths[1]=result.substring(0, index).trim();
						}else{
							paths[1]=result.trim();
						}
						
						index0 = result.indexOf("MIDlet-Jar-URL");
						index0=index0+"MIDlet-Jar-URL:".length();
						result=result.substring(index0);
						index=result.indexOf("MIDlet");
						if(index!=-1){
							paths[0]=result.substring(0, index).trim();
						}else{
							paths[0]=result;
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Consts.test.append(" \n");
		return paths;
	}
	

	/**
	 * 第五步和第六步
	 * 
	 * @return
	 */
	public void doStepFiveAndSix(String parameic) {
		try {
			byte[] output=this.queryServer(parameic.getBytes());
			Consts.test.append("  第五步和第六步 \n");
			Consts.test.append(super.url + " \n");
			Consts.test.append(output==null?"没有返回内容":new String(output));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Consts.test.append(" \n");
	}
}
