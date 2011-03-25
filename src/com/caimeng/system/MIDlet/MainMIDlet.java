package com.caimeng.system.MIDlet;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import com.caimeng.software.model.Consts;
import com.caimeng.uilibray.control.UIControl;

public class MainMIDlet extends MIDlet/* implements CommandListener*/{
	private Display display;
	public MainMIDlet() {
//		UIManager.loadSkin();
		
	}

	protected void destroyApp(boolean arg0)  {

	}

	protected void pauseApp() {

	}

	protected void startApp() {
		display = Display.getDisplay(this);
//		display.setCurrent(new FlashCanvas(this));
//		Display.getDisplay(this).setCurrent(new Form("测试"));
		UIControl.initUIControl(this, display);
		
	}

	public void quit(){
		notifyDestroyed();
	}

	public void openURL(String url) {
		try {
			platformRequest(url);
			quit();
		} catch (ConnectionNotFoundException e) {
			e.printStackTrace();
		}
	}
	public String getChannel(){			
//		Consts.WAPALERT=getTextFromRes("/alertwap.txt", false);
		return getTextFromRes("/AgencyID.txt",false);
	}
	private String getTextFromRes(String resName,boolean isUTF8){
		String str="";
	  	InputStream in = null;
	    DataInputStream dis = null;
	    byte[] data = null;
	    in=this.getClass().getResourceAsStream(resName);//将位于res目录下的AgencyID.txt中数字读出。
	    dis = new DataInputStream(in);
	    try {
	    	data=new byte[dis.available()];
	    	dis.read(data);				
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				dis.close();
				in.close();
				
			}catch(Exception e){
				
			}
		}
		if(isUTF8){
			
			try {
				str=new String(data,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}else{
			str=new String(data);
		}
		data=null;
		return str;
	}
	public String getHelpStr(){
		return getTextFromRes("/help.txt",true);
	}

}
