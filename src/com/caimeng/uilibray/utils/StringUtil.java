package com.caimeng.uilibray.utils;

import java.util.Vector;

import javax.microedition.lcdui.Font;


/**
 * 提供一些常用字符串处理方法
 * 
 * @author JoyCode workshop
 * 
 */
public class StringUtil {
	public static final String UP_ARROW = "↑";
	public static final String LEFT_ARROW = "↑";

	/**
	 * 判断当前字符串是否为null
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) 
	{
		return (str == null) || str.equals("");
	}
	
	/**
	 * 判断当前字符串是否不为null
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String serviceUrl) 
	{
		return !isBlank(serviceUrl);
	}

	/**
	 * 根据屏幕和字体设置文字的换行处理
	 * 
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @return
	 */
	public static String wrappedTextByScreenWidth(String displayText,
			Font font, int screenWidth) {
		// 为字符串分行，以便于显示
		StringBuffer sb = new StringBuffer();
		int wd = 0;
		int maxLength = screenWidth - font.charWidth('中') * 2;
		char word;
		displayText = displayText.replace('\n', ' ');
		int length = displayText.length();
		for (int i = 0; i < length; i++) {
			word = displayText.charAt(i);
			wd += font.charWidth(word);
			if ((wd > maxLength) && (i != length - 1)) {
				sb.append("\n");
				wd = 0;
			}
			sb.append(word);
		}
		if (sb.charAt(sb.length() - 1) == '\n') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 根据屏幕宽度对字符串进行转换
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @return
	 */
	public static String wrappedTextByScreenWithFirstLineIndendent(
			String displayText, Font font, int screenWidth) {
		// 为字符串分行，以便于显示
		StringBuffer sb = new StringBuffer();
		int wd = font.charWidth('中') * 4;
		int maxLength = screenWidth - font.charWidth('中') * 2;
		char word;
		displayText = displayText.replace('\n', ' ');
		int length = displayText.length();
		for (int i = 0; i < length; i++) {
			word = displayText.charAt(i);
			wd += font.charWidth(word);
			if ((wd > maxLength) && (i != length - 1)) {
				sb.append("\n");
				wd = 0;
			}
			sb.append(word);
		}
		if (sb.charAt(sb.length() - 1) == '\n') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 把字符根据屏幕宽度和换行符号，分成一行一行的记录
	 * 
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @param 系统控制符号
	 * @return
	 */
	public static Vector separateTextByScreenWidth(String displayText,
			Font font, int screenWidth, boolean ignoreSymbol) {
		return separateTextByScreenWidth(0, displayText, font, screenWidth,
				ignoreSymbol);
	}

	/**
	 * 
	 * @param beginWidth
	 *            字符串前的图像的宽度
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @param ignoreSymbol
	 * @return
	 */
	public static Vector separateTextByScreenWidth(int beginWidth,
			String displayText, Font font, int screenWidth, boolean ignoreSymbol) {
		java.util.Vector v = new java.util.Vector();
		int wd = beginWidth;
		int maxLength = screenWidth - 2;
		char word;
		StringBuffer sb = new StringBuffer();
		int textLength = displayText.length();
		for (int i = 0; i < textLength; i++) {
			word = displayText.charAt(i);
			if ((word == '\n') && !ignoreSymbol) {
				wd = addLine(v, sb);
				continue;
			}
			if ((word == '\t') && !ignoreSymbol) {
				word = ' ';
				wd += font.charWidth(' ');
			} else {
				wd += font.charWidth(word);

			}
			if (wd >= maxLength) {
				wd = addLine(v, sb);
			}

			sb.append(word);
		}

		if (sb.length() > 0) {
			v.addElement(sb.toString());
		}
		return v;
	}

	/***************************************************************************
	 * 同前方法，但返回结果是数组，便于运算
	 * 
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @return
	 */
	public static String[] separateTextArrayByWidth(String displayText,
			Font font, int screenWidth, boolean ignoreSymbol) {
		java.util.Vector v = separateTextByScreenWidth(displayText, font,
				screenWidth, ignoreSymbol);
		return convertVector2String(v);
	}

	/**
	 * 根据指定长度拆分字符串，如果字符串前面有图像标记，则指明图像的宽度
	 * 
	 * @param beginWidth
	 * @param displayText
	 * @param font
	 * @param screenWidth
	 * @param ignoreSymbol
	 * @return
	 */
	public static String[] separateTextArrayByWidth(int beginWidth,
			String displayText, Font font, int screenWidth, boolean ignoreSymbol) {
		java.util.Vector v = separateTextByScreenWidth(beginWidth, displayText,
				font, screenWidth, ignoreSymbol);
		return convertVector2String(v);
	}

	
	private static int addLine(java.util.Vector v, StringBuffer sb) {
		v.addElement(sb.toString());
		sb.delete(0, sb.length());
		return 0;
	}

	/**
	 * 根据屏幕宽度，将字符串转换为以行为单位的字符行vector
	 * @param displayText
	 * @return
	 */
	/*public static Vector separateString(String displayText) {

		java.util.Vector v = new java.util.Vector();
		int wd = 0;
		char word;
		StringBuffer sb = new StringBuffer();
		int length = displayText.length();
		int lines = 0;
		int width = Platform.WIDTH - 10;
		Font font = Font.getDefaultFont();
		for (int i = 0; i < length; i++) {
			word = displayText.charAt(i);
			sb.append(word);
			wd += font.charWidth(word);
			if (wd > width || word == '\n') {
				lines++;
				wd = 0;
			}
			if (i == length - 1 || lines > 15) {
				sb.append("\n" + createSpaceLine(font, width));
				lines = addLine(v, sb);
				wd = 0;
			}
		}

		return v;
	}*/

	/**
	 * 创建一个指定宽度的空白行
	 * @param font
	 * @param width
	 * @return
	 */
	public static String createSpaceLine(Font font, int width) {
		return createSpaceLine(font, width, ' ');
	}

	/**
	 * 创建一个指定宽度的空白行
	 * @param font
	 * @param width
	 * @param separator
	 * @return
	 */
	public static String createSpaceLine(Font font, int width, char separator) {
		int wd = font.charWidth(separator);
		StringBuffer sb = new StringBuffer();
		while (wd < width) {
			wd += font.charWidth(separator);
			sb.append(separator);
		}
		return sb.toString();
	}

	/**
	 *  创建一个指定宽度的分割行
	 * @return
	 */
	/*public static String createSeparatorLine() {
		Font font = Font.getDefaultFont();
		int width = Platform.WIDTH - font.charWidth('-') * 2;
		return createSpaceLine(font, width, '-');
	}*/
	
	/**
	 * 计算使用当前缺省字体的n个字符会占用的屏幕宽度
	 * @param n
	 * @return
	 */
	public static int caculateStringWidth(int n){
		Font font = Font.getDefaultFont();
		int width = font.charWidth('日') * n;
		return width;
	}

	/**
	 * 对字符串信息进行调整，去除；，用\n代替
	 * @param content
	 * @return
	 */
	public static String getAdjustDisplayContent(String content) {
		if (!StringUtil.isBlank(content)) {
			content = content.replace(';', '\n');
		}
		return content;
	}

	/**
	 * 对字符串用指定符合进行拆分
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String[] split(String str, char ch) {
		Vector v = new Vector();
		int start = 0;
		int pos = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				String xx = str.substring(start, i);
				if (isNotBlank(xx)) {
					v.addElement(xx);
				}
				start = i + 1;
				pos = i;
			}
		}
		if (pos < str.length()) {
			pos = pos > 0 ? pos + 1 : pos;
			String xx = str.substring(pos);
			if (isNotBlank(xx)) {
				v.addElement(xx);
			}
		}
		String results[] = convertVector2String(v);

		return results;
	}

	
	/**
	 * 将vecotor转换为数组对象  
	 * 
	 * 此方法完全多余
	 * @param temp
	 * @return
	 */
	public static String[] convertVector2String(Vector temp) {
		
		String[] result = null;
		int tsize = temp.size();
		if (tsize > 0) {
			result = new String[tsize];
			temp.copyInto(result);
		}
		return result;
	}


	/**
	 * 将字符数组中的内容用；连接起来
	 * 
	 * @param receiverlist
	 * @return
	 */
	public static String concate(String[] receiverlist) {
		StringBuffer sb = new StringBuffer();
		if (receiverlist.length == 1) {
			return receiverlist[0];
		}
		for (int i = 0; i < receiverlist.length; i++) {
			sb.append(receiverlist[i] + ";");
		}
		return sb.toString();
	}

	/**
	 * 截取指定长度的字符串
	 * 
	 * @param value
	 * @param maxSize
	 * @return
	 */
	/*public static String truncateStr(String value, int maxSize) {
		String temp = KUtil.convert(value);
		if (temp.length() > maxSize) {
			temp = temp.substring(0, maxSize);
		}
		return temp;
	}*/

	/**
	 * 移除当前字符数组的null 值
	 * 
	 * @param fixedUserList
	 * @return
	 */
	public static String[] removeNullValue(String[] fixedUserList) {
		int size = fixedUserList.length;
		Vector v = new Vector();
		for (int i = 0; i < size; i++) {
			if (fixedUserList[i] != null) {
				v.addElement(fixedUserList[i]);
			}
		}
		String temp[] = new String[v.size()];
		v.copyInto(temp);
		v = null;
		return temp;
	}



	/**
	 * 将字符串按换行符
	 * 
	 * @param value
	 * @return
	 */
	public static String[] getLines(String value) {
		if (isBlank(value)) {
			return null;
		}
		int count = 1;
		Vector v = new Vector();
		StringBuffer line = new StringBuffer();
		v.addElement(line);
		for (int i = 0; i < value.length(); i++) {
			if ((value.charAt(i) == '\n') && (i != value.length() - 1)) {
				count++;
				line = null;
				line = new StringBuffer();
				v.addElement(line);
			}
			line.append(value.charAt(i));
		}
		String[] lines = new String[v.size()];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = ((StringBuffer) v.elementAt(i)).toString();
		}
		return lines;
	}

	/**
	 * 返回指定数组中长度最大的一个
	 * 
	 * @param values
	 * @return
	 */
	public static int getMaxLineLength(String[] values) {
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			if ((values[i] != null) && (values[i].length() > max)) {
				max = values[i].length();
			}
		}
		return max;
	}

	
	/**
	 * 返回不带报名的实际类名
	 * @param obj
	 * @return
	 */
	public static String getSimpleClassName(Object obj) {
		String className = obj.getClass().getName();
		int pos = className.lastIndexOf('.');
		if (pos > 0) {
			className = className.substring(pos + 1);
		}
		return className;
	}
	
	
	/**
	 * 对一个数组进行排序，使用普通的冒泡法
	 * @param target
	 * @return
	 */
	public static String[] sort(String[] target) {
		for (int i = 0; i < target.length; i++) {
			for (int j = 1; j < target.length - i; j++) {
				if (target[j].compareTo(target[j-1]) < 0) {
					String temp  = target[j];
					target[j] = target[j-1];
					target[j-1] = temp;
				}
			}
		}
		return target;
	}
	/**
	 * 过滤值，返回true大于6位的数字，false非大于6位的数字
	 * @param number
	 * @return
	 */
	public static boolean filterNumber(Object obj)
	{
		 try{
				 long number=Long.parseLong((String)obj);
			     if(number>=100000)
				 {
					  return true;
				 }
			      
		    }catch(Exception e)
			{   
		    	  return false;
			}
			return false;
	}
	
	/**
	 * 切割str字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String bufferstr, String regex) {

		if (bufferstr == null)
			return null;
		Vector split = new Vector();

		while (true) {
			int index = bufferstr.indexOf(regex);

			if (index == -1) {
				if (bufferstr != null && !bufferstr.equals(""))
					split.addElement(bufferstr);

				break;
			}
			split.addElement(bufferstr.substring(0, index));

			bufferstr = bufferstr.substring(index + 1, bufferstr.length());

		}
		String[] s = new String[split.size()];
		split.copyInto(s);
		split.removeAllElements();
		split = null;

		return s;

	}
	
	/***
	 * 过滤空字符串
	 * @param str
	 * @return
	 */
	public static String filterULLString(String str)
	{   
		if(str!=null)
			return str;
		return "";
	}
	
	
	
}
