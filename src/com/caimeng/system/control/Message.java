package com.caimeng.system.control;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class Message {
	private  String address="sms://";
	private  MessageConnection smsconn;
	/**
	 * 发送短信
	 * @param port 端口 
	 * @param context内容
	 */
	public  boolean  sendMessage(String port,String context){		  
		   address=address+port;
	        try {
	            smsconn = (MessageConnection)Connector.open(address);
	            TextMessage txtmessage = (TextMessage)smsconn.newMessage(MessageConnection.TEXT_MESSAGE);
	            txtmessage.setAddress(address);
	            txtmessage.setPayloadText(context);
	            smsconn.send(txtmessage);
	            return true;
	        } catch (Throwable t) {
	            t.printStackTrace();
	            return false;
	        }finally{
		        if (smsconn != null) {
		            try {
		                smsconn.close();
		            } catch (IOException ioe) {
		                ioe.printStackTrace();
		            }
		        }
	        }
	}



}
