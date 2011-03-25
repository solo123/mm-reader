package com.caimeng.software.io;

public class FileManager {

	public static void writeFile(String filename,String context){
		FileOperation file=new FileOperation("test",null);
		file.initFile(filename);
		try{
			file.addLine(context.getBytes("utf-8"));
			file.closeFile();
		}catch(Exception e){
			file.closeFile();
			e.printStackTrace();
		}
	}
	public static void writeImg(String filename,byte[]  context){
		FileOperation file=new FileOperation("test",".jpg");
		file.initFile(filename);
		try{
			file.addLine(context);
			file.closeFile();
		}catch(Exception e){
			file.closeFile();
			e.printStackTrace();
		}
	}
	public static void writeJar(String filename,byte[]  context){
		FileOperation file=new FileOperation("test",".jar");
		file.initFile(filename);
		try{
			file.addLine(context);
			file.closeFile();
		}catch(Exception e){
			file.closeFile();
			e.printStackTrace();
		}
	}
}
