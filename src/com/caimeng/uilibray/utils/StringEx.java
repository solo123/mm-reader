package com.caimeng.uilibray.utils;

import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class StringEx {

	public static Vector lineCast(String text,int width,Font font){
		Vector line=new Vector();
		int lineWidth=0,charsSum=text.length(),from=0,to=0;
		for(int i=0;i<charsSum;i++){
			char c=text.charAt(i);		
			boolean check=false;
			while((byte)c==13 &&( i+1<text.length()?(byte)text.charAt(i+1)==10:(byte)c==10)){
				to=i;
				line.addElement(text.substring(from,to));							
				from=i+2;
				i+=2;
				lineWidth=0;
				check=true;
			}
			if(c=='\n'){
				to=i;
				line.addElement(text.substring(from,to));							
				from=i+1;
				i+=1;
				lineWidth=0;
				check=true;
			}
			if(!check){				
				lineWidth=lineWidth+font.charWidth(c);
				if(lineWidth>width){
					to=i-1;
					line.addElement(text.substring(from,to));
					from=to;
					lineWidth=font.charWidth(c);
				}
			}
		}
		line.addElement(text.substring(from));
		return line;
	}
	
	public static Vector tokenCast(String text,int width,Font font){
		Vector line=new Vector();
		int lineWidth=0,charsSum=text.length(),from=0,to=0,lastSpaceIndex=0;
		for(int i=0;i<charsSum;i++){
			lineWidth=lineWidth+font.charWidth(text.charAt(i));
			if(text.charAt(i)==' '){
				lastSpaceIndex=i;
			}
			if(lineWidth>width){
				if(lastSpaceIndex>to){
					to=lastSpaceIndex;
					i=lastSpaceIndex+1;
					lastSpaceIndex=0;
				}else{
					to=i-1;
				}
				for(int w=from;w<to;w++){
					if(text.charAt(w)=='\n'){
						line.addElement(text.substring(from,w));
						from=w;
					}
				}
				line.addElement(text.substring(from,to));
				from=to;
				if(i<charsSum) {
					lineWidth=font.charWidth(text.charAt(i));
				}
			}
		}
		for(int w=from;w<charsSum;w++){
			if(text.charAt(w)=='\n'){
				line.addElement(text.substring(from,to));
				from=w;
			}
		}
		line.addElement(text.substring(from));
		return line;
	}
	
	public static void drawVerticalString(Graphics g, String str, int x, int y) {
		int len = str.length();
		char ch;
		Font font = g.getFont();

		int width = font.charWidth('(');
		int height = font.getHeight();
		int offset = width / 2;
		int color = ~g.getColor();// ~取反
		Image bracket = Image.createImage(width, height);
		Graphics graphics = bracket.getGraphics();
		graphics.setColor(color);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(g.getColor());
		graphics.drawChar('(', 0, 0, 20);
		graphics = null;
		int[] rgb = new int[width * height];
		bracket.getRGB(rgb, 0, width, 0, 0, width, height);
		bracket = null;
		int size = width * height;
		for (int i = 0; i < size; i++) {
			if (rgb[i] == color) {
				rgb[i] = 0xffffffff;
			}
		}

		bracket = Image.createRGBImage(rgb, width, height, false);

		for (int i = 0; i < len; i++) {
			ch = str.charAt(i);
			if (ch == '(') {
				g.drawRegion(bracket, 0, 0, width, height, Sprite.TRANS_ROT90,
						x - offset, y, 20);
				y += width;
			} else if (ch == ')') {
				g.drawRegion(bracket, 0, 0, width, height, Sprite.TRANS_ROT270,
						x - offset, y, 20);
				y += width;
			} else {
				g.drawChar(ch, x, y, 20);
				y += font.getHeight();
			}
		}
	}
	/**
	 * 不区分大小写匹配字符串是否相等
	 * @param bigStr
	 * @param smallStr
	 * @return
	 */
	public static boolean isStrInString(String bigStr,String smallStr){
		 
		if(bigStr.toUpperCase().equals(smallStr.toUpperCase()))
		   return true;
		  
		  return false;
	 }
}
