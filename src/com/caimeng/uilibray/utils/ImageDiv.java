package com.caimeng.uilibray.utils;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ImageDiv {
	
	public static void drawJiuGong(Graphics g,String img_str,int x,int y,int width,int height){
		Image img=null;
		int blockWidth=0,blockHeight=0;
		try {
			img=Image.createImage(img_str);
			blockWidth=img.getWidth()/3;
			blockHeight=img.getHeight()/3;
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] clip=new int[4];
		clip[0]=g.getClipX();
		clip[1]=g.getClipY();
		clip[2]=g.getClipWidth();
		clip[3]=g.getClipHeight();
		//前3宫
		g.setClip(x, y, blockWidth, blockHeight);
		g.drawImage(img, x, y, Graphics.LEFT|Graphics.TOP);
		int length=width/blockWidth-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x+blockWidth*(i+1), y, blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(i+1), y, Graphics.LEFT|Graphics.TOP);
			
		}
		if(width%blockWidth!=0){
			g.setClip(x+blockWidth*(length+1), y, width%blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(length+1), y, Graphics.LEFT|Graphics.TOP);
		}
		g.setClip(x+width-blockWidth, y, blockWidth, blockHeight);
		g.drawImage(img,-blockWidth*2+x+width-blockWidth, y, Graphics.LEFT|Graphics.TOP);
		//中间前n-1排
		length=height/blockHeight-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x, y+blockHeight*(i+1), blockWidth, blockHeight);
			g.drawImage(img, x, -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			int length2=width/blockWidth-2;
			for (int j = 0; j < length2; j++) {
				g.setClip(x+blockWidth*(j+1), y+blockHeight*(i+1), blockWidth, blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(j+1), -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
				
			}
			if(width%blockWidth!=0){
				g.setClip(x+blockWidth*(length2+1), y+blockHeight*(i+1), width%blockWidth, blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(length2+1), -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			}
			g.setClip(x+width-blockWidth, y+blockHeight*(i+1), blockWidth, blockHeight);
			g.drawImage(img,-blockWidth*2+x+width-blockWidth, -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			
		}
		//中间最后一横排
		if(height%blockHeight!=0){
			length++;
			g.setClip(x, y+blockHeight*length, blockWidth, height%blockHeight);
			g.drawImage(img, x, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			int length2=width/blockWidth-2;
			for (int j = 0; j < length2; j++) {
				g.setClip(x+blockWidth*(j+1), y+blockHeight*length, blockWidth, height%blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(j+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
				
			}
			if(width%blockWidth!=0){
				g.setClip(x+blockWidth*(length2+1), y+blockHeight*length, width%blockWidth, height%blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(length2+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			}
			g.setClip(x+width-blockWidth, y+blockHeight*length, blockWidth, height%blockHeight);
			g.drawImage(img,-blockWidth*2+x+width-blockWidth, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			
		}
		//后3
		g.setClip(x, y+height-blockHeight, blockWidth, blockHeight);
		g.drawImage(img, x, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		length=width/blockWidth-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x+blockWidth*(i+1), y+height-blockHeight, blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(i+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			
		}
		if(width%blockWidth!=0){
			g.setClip(x+blockWidth*(length+1), y+height-blockHeight, width%blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(length+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		}
		g.setClip(x+width-blockWidth, y+height-blockHeight, blockWidth, blockHeight);
		g.drawImage(img,-blockWidth*2+x+width-blockWidth, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		g.setClip(clip[0], clip[1], clip[2], clip[3]);
	}
	
	public static void drawJiuGong(Graphics g,Image img,int x,int y,int width,int height){
		int blockWidth=img.getWidth()/3;
		int blockHeight=img.getHeight()/3;
		int[] clip=new int[4];
		clip[0]=g.getClipX();
		clip[1]=g.getClipY();
		clip[2]=g.getClipWidth();
		clip[3]=g.getClipHeight();
		//前3宫
		g.setClip(x, y, blockWidth, blockHeight);
		g.drawImage(img, x, y, Graphics.LEFT|Graphics.TOP);
		int length=width/blockWidth-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x+blockWidth*(i+1), y, blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(i+1), y, Graphics.LEFT|Graphics.TOP);
			
		}
		if(width%blockWidth!=0){
			g.setClip(x+blockWidth*(length+1), y, width%blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(length+1), y, Graphics.LEFT|Graphics.TOP);
		}
		g.setClip(x+width-blockWidth, y, blockWidth, blockHeight);
		g.drawImage(img,-blockWidth*2+x+width-blockWidth, y, Graphics.LEFT|Graphics.TOP);
		//中间前n-1排
		length=height/blockHeight-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x, y+blockHeight*(i+1), blockWidth, blockHeight);
			g.drawImage(img, x, -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			int length2=width/blockWidth-2;
			for (int j = 0; j < length2; j++) {
				g.setClip(x+blockWidth*(j+1), y+blockHeight*(i+1), blockWidth, blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(j+1), -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
				
			}
			if(width%blockWidth!=0){
				g.setClip(x+blockWidth*(length2+1), y+blockHeight*(i+1), width%blockWidth, blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(length2+1), -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			}
			g.setClip(x+width-blockWidth, y+blockHeight*(i+1), blockWidth, blockHeight);
			g.drawImage(img,-blockWidth*2+x+width-blockWidth, -blockHeight+y+blockHeight*(i+1), Graphics.LEFT|Graphics.TOP);
			
		}
		//中间最后一横排
		if(height%blockHeight!=0){
			length++;
			g.setClip(x, y+blockHeight*length, blockWidth, height%blockHeight);
			g.drawImage(img, x, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			int length2=width/blockWidth-2;
			for (int j = 0; j < length2; j++) {
				g.setClip(x+blockWidth*(j+1), y+blockHeight*length, blockWidth, height%blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(j+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
				
			}
			if(width%blockWidth!=0){
				g.setClip(x+blockWidth*(length2+1), y+blockHeight*length, width%blockWidth, height%blockHeight);
				g.drawImage(img,-blockWidth+x+blockWidth*(length2+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			}
			g.setClip(x+width-blockWidth, y+blockHeight*length, blockWidth, height%blockHeight);
			g.drawImage(img,-blockWidth*2+x+width-blockWidth, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			
		}
		//后3
		g.setClip(x, y+height-blockHeight, blockWidth, blockHeight);
		g.drawImage(img, x, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		length=width/blockWidth-2;
		for (int i = 0; i < length; i++) {
			g.setClip(x+blockWidth*(i+1), y+height-blockHeight, blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(i+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
			
		}
		if(width%blockWidth!=0){
			g.setClip(x+blockWidth*(length+1), y+height-blockHeight, width%blockWidth, blockHeight);
			g.drawImage(img,-blockWidth+x+blockWidth*(length+1), -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		}
		g.setClip(x+width-blockWidth, y+height-blockHeight, blockWidth, blockHeight);
		g.drawImage(img,-blockWidth*2+x+width-blockWidth, -2*blockHeight+y+height-blockHeight, Graphics.LEFT|Graphics.TOP);
		g.setClip(clip[0], clip[1], clip[2], clip[3]);
	}
	
	public static void fillScreen(Graphics g,Image img,int x,int y,int width,int heigth){
		int imgW=img.getWidth();
//		int imgH=img.getHeight();
		for(int i=x;i<width;i+=imgW){
			g.drawImage(img, i, y, 0);
		}
	}

}
