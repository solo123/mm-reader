package com.caimeng.uilibray.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

public class FileUtil {
	/*public static void fileRW(String disStr) throws IOException {
		Enumeration e = null;
		String path = null;
		OutputStream ops = null;

		e = FileSystemRegistry.listRoots();
		path = e.nextElement().toString();

		FileConnection fc = (FileConnection) Connector.open("file://localhost/"
				+ path + System.currentTimeMillis() + "log01.txt",
				Connector.READ_WRITE, true);
		if (!fc.exists()) {
			fc.create();
		}
		ops = fc.openOutputStream(fc.fileSize());
		byte[] b = disStr.getBytes();
		ops.write(b);
		ops.flush();
		ops.close();
		fc.close();
	}

	public static void fileRW(InputStream bIn) throws IOException {
		Enumeration e = null;
		String path = null;
		OutputStream ops = null;

		e = FileSystemRegistry.listRoots();
		path = e.nextElement().toString();

		FileConnection fc = (FileConnection) Connector.open("file://localhost/"
				+ path + System.currentTimeMillis() + "log02.txt",
				Connector.READ_WRITE, true);
		if (!fc.exists()) {
			fc.create();
		}
		ops = fc.openOutputStream();
		byte buffer[] = new byte[1024];
		int c;
		while ((c = bIn.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				ops.write(buffer[i]);
		}
		ops.flush();
		ops.close();
		fc.close();
	}
*/
	public static String streamToString(InputStream bIn) throws IOException {
		byte buffer[] = new byte[1024];
		int c=0;
		StringBuffer sb = new StringBuffer();
		while ((c = bIn.read(buffer)) != -1) {
				sb.append(new String(buffer));
		}
		return sb.toString();
	}
	// 在参数xml中查找arg
	public static String getArgValue(String xml,String arg) {
		String v = "";
		try{
			
			if (xml != null && !xml.equals("")) {
				v = xml.substring(xml.indexOf("<"+arg+">")+arg.length()+2, xml.indexOf("</"+arg+">"));
			}
		}catch(Exception e){
			return null;
			
		}
		return v;
	}
}
