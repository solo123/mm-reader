package com.caimeng.uilibray.utils;
import javax.microedition.lcdui.Image;


/**
 * 验证码分割，识别类
 * 主要思想:
 *     1、先将验证码图片进行分割，取出数字的最小区域
 *     2、将该区域进行灰度转换，以200作为临界值，小于该值记为1，大于该值记为0
 *     3、对该区域数组横向和纵向的1状态和模板比较，如果1全相同，则返回该模板对应数字
 * @author boy
 *
 * 彩梦科技 sunrg@sinomaster.com
 */
public class ImageCodeAnalyzing {
	static Image source;
//	static Image[] codeImg=new Image[2];
//	public static int[] colorState=null;
	static int[] currStateX=new int[11];//横向宽度为11
	static int[] currStateY=new int[12];//纵向宽度为12
	static int[] currStateX2=new int[11];
	static int[] currStateX3=new int[11];
	/**********0---9横向标准模板  矩阵横中线***************************/
	public static int[][] standardStateX=new int[][]{
		{0,1,1,0,0,0,0,0,1,1,0},//0
		
		{0,0,0,0,0,1,1,0,0,0,0},//1
		
		{0,0,0,0,0,0,1,1,0,0,0},//2
		
		{0,0,0,0,1,1,1,0,0,0,0},//3
		
		{0,0,1,1,0,0,0,1,1,0,0},//4
		
		{0,0,1,1,1,1,1,0,0,0,0},//5
		
		{0,0,1,1,1,1,1,1,1,0,0},//6
		
		{0,0,0,0,0,0,1,1,0,0,0},//7
		
		{0,0,0,1,1,1,1,1,0,0,0},//8
		
		{0,1,1,1,0,0,0,1,1,1,0},//9
		
	};
	/**
	 * 加强判断组2 矩阵上边
	 */
	public static int[][] standardStateX2=new int[][]{
		{0,0,0,0,1,1,1,0,0,0,0},//0
		
		{0,0,0,0,0,1,1,0,0,0,0},//1
		
		{0,0,1,1,1,1,1,0,0,0,0},//2
		
		{0,0,0,1,1,1,1,0,0,0,0},//3
		
		{0,0,0,0,0,0,1,1,1,0,0},//4
		
		{0,0,1,1,1,1,1,1,0,0,0},//5
		
		{0,0,0,0,0,1,1,1,0,0,0},//6
		
		{0,1,1,1,1,1,1,1,1,1,0},//7
		
		{0,0,0,1,1,1,1,1,0,0,0},//8
		
		{0,0,0,1,1,1,1,0,0,0,0},//9
		
	};
	
	/**
	 * 加强判断组 3 矩阵底边
	 */
	public static int[][] standardStateX3=new int[][]{
		{0,0,0,0,1,1,1,0,0,0,0},//0
		
		{0,0,0,1,1,1,1,1,1,0,0},//1
		
		{0,1,1,1,1,1,1,1,1,0,0},//2
		
		{0,0,0,1,1,1,1,0,0,0,0},//3
		
		{0,0,0,0,0,0,0,1,1,0,0},//4
		
		{0,0,0,1,1,1,0,0,0,0,0},//5
		
		{0,0,0,0,0,1,1,1,0,0,0},//6
		
		{0,0,0,1,1,0,0,0,0,0,0},//7
		
		{0,0,0,1,1,1,1,1,0,0,0},//8
		
		{0,0,0,1,1,1,1,0,0,0,0},//9
		
	};
	
	/**********0---9纵向标准模板  矩阵竖中线***************************/
	public static int[][] standardStateY=new int[][]{
		{1,1,0,0,0,0,0,0,0,0,1,1},//0
		
		{1,1,1,1,1,1,1,1,1,1,1,1},//1
		
		{1,1,0,0,0,0,1,0,0,0,1,1},//2
		
		{1,1,0,0,0,1,1,0,0,0,1,1},//3
		
		{0,1,1,1,0,0,0,1,1,0,0,0},//4
		
		{1,1,0,0,1,1,1,0,0,1,1,1},//5
		
		{1,1,0,0,1,1,0,0,0,0,1,1},//6
		
		{1,1,0,0,0,0,1,1,1,1,0,0},//7
		
		{1,1,0,0,0,1,1,0,0,0,1,1},//8
		
		{1,1,0,0,0,0,1,1,0,0,1,1},//9
		
	};
	
	/**
	 * 比较  返回最相似的结果 数字
	 * @return
	 */
	public static int compareToMode(){
		int number=-1;
		for(int i=9;i>=0;i--){
			int[] tempX=standardStateX[i];
			int[] tempY=standardStateY[i];
			boolean isX=isAlikeX(tempX);
			boolean isY=isAlikeY(tempY);
			boolean isX2=isAlikeX2(standardStateX2[i]);
			boolean isX3=isAlikeX3(standardStateX3[i]);
//			System.out.println("i==="+i+ "   isX:"+isX+"   isY:"+isY +"   isX2"+isX2+"  isX3"+isX3);
			if(isX && isY && isX2 && isX3){
				number=i;
				break;
			}
		}
		
//		System.out.println("识别的内容"+number);
		
		return number;
	}
	
	
	/**
	 * 横向是否相似3 底端
	 * @param currX
	 * @return
	 */
	public static boolean isAlikeX3(int[] currX){
		boolean alike=true;
		
		for(int i=0;i<currX.length;i++){
			if(currX[i]==1){
				if(currStateX3[i]!=1){//找到一个不同于模块1状态的，则不是
					alike=false;
					break;
				}
			}
		}
		return alike;
	}
	/**
	 * 横向是否相似2 顶端
	 * @param currX
	 * @return
	 */
	public static boolean isAlikeX2(int[] currX){
		boolean alike=true;
		
		for(int i=0;i<currX.length;i++){
			if(currX[i]==1){
				if(currStateX2[i]!=1){//找到一个不同于模块1状态的，则不是
					alike=false;
					break;
				}
			}
		}
		return alike;
	}
	/**
	 * 横向是否相似 中线
	 * @param currX
	 * @return
	 */
	public static boolean isAlikeX(int[] currX){
		boolean alike=true;
		
		for(int i=0;i<currX.length;i++){
			if(currX[i]==1){
				if(currStateX[i]!=1){//找到一个不同于模块1状态的，则不是
					alike=false;
					break;
				}
			}
		}
		return alike;
	}
	
	/**
	 * 纵向是否相似
	 * @param currX
	 * @return
	 */
	public static boolean isAlikeY(int[] currY){
		boolean alike=true;
		
		for(int i=0;i<currY.length;i++){
			if(currY[i]==1){
				if(currStateY[i]!=1){//找到一个不同于模块1状态的，则不是
					alike=false;
					break;
				}
			}
		}
		return alike;
	}
	/**
	 * 创建原始大图
	 *
	 */
	public static void loadSource(){
		try{
			source=Image.createImage("/test/100.jpg");			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(source!=null){
			clipImg(source);
		}
		
	}
	static StringBuffer sb=new StringBuffer();
	/**
	 * 测试100张
	 *
	 */
	public static void testSource(){
		
		for(int i=1;i<=100;i++){
			try{
				source=Image.createImage("/test/"+i+".jpg");			
			}catch(Exception e){
				e.printStackTrace();
			}
			if(source!=null){
				long currTime=System.currentTimeMillis();
				String str=clipImg(source);
				long currTime2=System.currentTimeMillis();
				sb.append("第"+i+"张数据为："+str+"   耗时："+(currTime2-currTime));
				sb.append("\r\n");
			}
		}
		System.out.println(sb.toString());
//		new Thread(){
//			public void run(){
//				
//				FileManager.writeFile("log", sb.toString());
//			}
//		}.start();
	}
	/**
	 * 
	 * @param image 验证码图片
	 * @return codeNumber返回识别的数字
	 */
	public static String getCodeFromImage(Image image){
		return clipImg(image);
	}
	
	/**
	 * 切割两数据对应图片区域
	 * @param source
	 */
	public static String clipImg(Image source){		
		Image[] codeImg=new Image[2];
		StringBuffer sBuffer=new StringBuffer();
		int w=24;
		int h=12;//小图高12像素
		int topY=5;
		int leftX=5;
		int imgw=11;//小图宽11像素
		for(int i=0;i<codeImg.length;i++){
			codeImg[i]=Image.createImage(source, leftX+w*i+(i==1?1:0), topY, imgw, h, 0);
			grayRGB(codeImg[i]);
			int number=compareToMode();
			sBuffer.append(number);
//			System.out.println("=================华丽之分割线 =======================");
		}
		return sBuffer.toString();
	}
	/*
	 * 
	 * 
	 * 图像灰度显示的方法1
	 */
	public static Image grayRGB(Image image) {
		 
		int width = image.getWidth();
		int height = image.getHeight();
		// 获得图像的ARGB数据,存储在rawInt里
		int[] raw = null;
		int[] colorState=null;
		try {
			raw = new int[width * height];
//			System.out.println("width=="+width  +"  height"+height);
			if(colorState==null){
				colorState= new int[width * height];
			}
			image.getRGB(raw, 0, width, 0, 0, width, height);
			int len = raw.length;
			// 开始循环，获得图像里每个像素的颜色，然后处理
			for (int i = 0; i < len; i++) {
				// 获得像素的颜色
				int color = raw[i];
				// System.out.println(Integer.toHexString(color));
				// 获得alpha
				int alpha = 0xFF;
				// System.out.println(Integer.toHexString(alpha));
				// 获得红色
				int red = (color & 0x00FF0000) >> 16;
				// System.out.println(Integer.toHexString(red));
				// 获得绿色
				int green = (color & 0x0000FF00) >> 8;
				// System.out.println(Integer.toHexString(green));
				// 获得蓝色
				int blue = (color & 0x000000FF);
				// System.out.println(Integer.toHexString(blue));
				// 将红黄蓝转换成灰度值，网上找到的算法
				int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				// System.out.println(gray);
				red = gray;
				green = gray;
				blue = gray;
				// 生成新颜色
				// System.out.println(Integer.toHexString((alpha << 24)+ (red <<
				// 16)+(green << 8)));
				color = (alpha << 24) + (red << 16) + (green << 8) + blue;
				// System.out.println(Integer.toHexString(color));
				raw[i] = color;
//				if(!test)
				colorState[i]=gray>200?0:1;
				
			}
			int line=0;
			int p=0;
			for(int i=0;i<colorState.length;i++){
//				System.out.print(""+colorState[i]);
				if(line==5){					
					currStateX[p]=colorState[i];
				}
				if(line==0){
					currStateX2[p]=colorState[i];
				}
				if(line==height-1){
					currStateX3[p]=colorState[i];
				}
				if(p==5){
					currStateY[line]=colorState[i];
				}
				p++;
				if((i+1)%(width)==0){
//					System.out.println();
					line++;
					p=0;
				}
			}
//			System.out.println("横");
//			for(int i=0;i<currStateX.length;i++){
//				System.out.print(""+currStateX[i]);
//			}
//			System.out.println("纵");
//			for(int i=0;i<currStateY.length;i++){
//				System.out.print(""+currStateY[i]);
//			}
//			
			return Image.createRGBImage(raw, width, height, true);

		} catch (Error e) {
			// e.printStackTrace();
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return image;
		} finally {
			raw = null;
		}
	}



}
