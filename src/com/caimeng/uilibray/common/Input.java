package com.caimeng.uilibray.common;

import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

import com.caimeng.uilibray.container.CMForm;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.utils.StringEx;


public class Input extends BaseControl {

	//public int fireCount = 0;

	//public boolean textReady = false;
	/**是否可写模式**/
	public boolean  iswriteStyle=true;
	/****0 普通  1日期*****/
	private int inputMode=0;
	public int length=0;
	public Input(){
		super();
	}

	public Input(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO 自动生成构造函数存根
	}

	public void paint(Graphics g) {
		drawtext (g,corX,corY);
	}
	public  int LETTER=0;
	public int DATETEXT=0;
	public void setInputLimit(String str){
		if(str.equals("n")){//数字
			inputStyle=TextField.NUMERIC;
			isNumber=true;
			isABC=false;
		}else if(str.equals("d")){//日期
			inputStyle=TextField.ANY;
//			inputStyle=TextField.NUMERIC;
			inputMode=1;
			DATETEXT=1;
			length=8;
		}else if(str.equals("l")){//字母
			inputStyle=TextField.ANY;
//			LETTER=1;
//			isABC=true;
//			isNumber=false;
//			ui.xWForm.startTimer();
			
		}else if(str.equals("nd")){//小数点
			inputStyle=TextField.DECIMAL;
		}
	}
	public String getInputMode(){
		return inputMode+"";
	}
	public void setCoordnate(int x,int y){
		this.corX=x;
		this.corY=y;
	}
	public String text = "";
	
	public int corX;
	public int corY;
	
	public Vector content =null;
	
	public int maxHeight=70;
	
	public void setText(String text){
		this.text=text;
		content = new Vector();
		if(this.text!=null)
		content=StringEx.lineCast(this.text, width-10, smallFont);				
		if (resize) {
			if (content.size() * smallFont.getHeight() < maxHeight)
				height = content.size() * smallFont.getHeight() + 9;
			else
				height = maxHeight;
		}
	}
	public void append(String text) {
	    
		this.text = this.text + text;
	}
	
	public boolean resize;
	
	private InputDialog inputDialog=null;
	
	private  int inputStyle=TextField.ANY;
	
	public void setInputStyle(int inputStyle){
		this.inputStyle=inputStyle;
		
	}
	public boolean isNumber=true;//直接输入数字模式

	public boolean isABC=false;//直接输入字母模式
	
	
	public void pointerDragged(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerDragged(x, y);
	}

	public void pointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		ui.selector.inInputItem=!ui.selector.inInputItem;
		if(ui.selector.inInputItem){
			CMForm xWForm=(CMForm)CMForm.display.getCurrent();
			
			inputDialog=new InputDialog("请输入内容：",text,16384,inputStyle,xWForm);//16k
			if(!ui.getMenuBar().rightMenuOpen){					
			    CMForm.display.setCurrent(inputDialog);
			}
		}
		super.pointerPressed(x, y);
	}

	public void pointerReleased(int x, int y) {
		// TODO Auto-generated method stub
		super.pointerReleased(x, y);
	}

	public void keyPressed(int keyCode) {
		if(!ui.getMenuBar().rightMenuOpen){
		super.keyPressed(keyCode);
		//if(!ui.selector.inInputItem)			
		if(keyCode==Key.FIREKEY(keyCode)){				
			ui.selector.inInputItem=!ui.selector.inInputItem;
			if(ui.selector.inInputItem){
				CMForm xWForm=(CMForm)CMForm.display.getCurrent();
				
				inputDialog=new InputDialog("请输入内容：",text,16384,inputStyle,xWForm);//16k
				if(!ui.getMenuBar().rightMenuOpen){					
				    CMForm.display.setCurrent(inputDialog);
				}
			}
		/*else if(keyCode == Key.UP|keyCode == Key.DOWN)	
		{
			ui.selector.inInputItem=false;
		}*/
			
		}else if(isNumber){	
			if(inputStyle==TextField.NUMERIC || inputStyle==TextField.ANY ||inputStyle==TextField.PHONENUMBER|| inputStyle==TextField.PASSWORD|| inputStyle==TextField.DECIMAL){
				if(iswriteStyle)
				inputKey(keyCode);
			}
		}else if(isABC){
			if(inputStyle==TextField.ANY||inputStyle==TextField.PASSWORD){
				if(iswriteStyle)
				MapkeyPressed(keyCode);
			}
			
		}
		}
	}
	public void inputKey(int keyCode){
		
		if(keyCode>=48 && keyCode<=57){
			if(length!=0)
			if(text.length()>=length){
				return ;
			}
				text=text+(keyCode-48);				
			
		}else if(keyCode==-8 || keyCode==42){
			    if(text.length()>0)
				text=text.substring(0,text.length()-1);
			}else if(keyCode==35){

				if(inputStyle==TextField.ANY ||inputStyle==TextField.PASSWORD){
					
					isNumber=false;
					isABC=true;
					ui.xmlForm.startTimer();
				}
				
			}
		
		
	}
	
	
	public void keyReleased(int keyCode) {
		
		
		super.keyReleased(keyCode);
		MapkeyReleased(keyCode);
	}
	
	
	 boolean[] keyState = new boolean[13];
		public  static byte
		      MASK_NUM1 = 1
		      , MASK_NUM2 = 2
		      , MASK_NUM3 = 3
		      , MASK_NUM4 = 4
		      , MASK_NUM5 = 5
		      , MASK_NUM6 = 6
		      , MASK_NUM7 = 7
		      , MASK_NUM8 = 8
		      , MASK_NUM9 = 9
		      , MASK_NUM0 = 0
		      , MASK_POUND = 10
		      , MASK_STAR = 11
		      ,MASK_DEL=12;
	public void MapkeyPressed(int keyCode){
		switch (keyCode) {
		  case 48:
		        keyState[MASK_NUM0] = true;
		        break;
	      case 49:
	        keyState[MASK_NUM1] = true;
	        break;
	      case 50:
	        keyState[MASK_NUM2] = true;
	        break;
	      case 51:
	        keyState[MASK_NUM3] = true;
	        break;
	      case 52:
	        keyState[MASK_NUM4] = true;
	        break;
	      case 53:
	        keyState[MASK_NUM5] = true;
	        break;
	      case 54:
	        keyState[MASK_NUM6] = true;
	        break;
	      case 55:
	        keyState[MASK_NUM7] = true;
	        break;
	      case 56:
	        keyState[MASK_NUM8] = true;
	        break;
	      case 57:
	        keyState[MASK_NUM9] = true;
	        break;
	      case 42: 
	    	  keyState[MASK_STAR]=true;
	    	  break;
	      case -8:
	    	  keyState[MASK_DEL]=true;
	    	  break;
	      case 35:
	    	  if(inputStyle==TextField.ANY ||inputStyle==TextField.PASSWORD){	    		  
	    		  isNumber=true;
	    		  isABC=false;
	    	  }
	    	
	    	  break;
	      default:
	        break;

	    }
	}
	
	public void MapkeyReleased(int keyCode){
		switch (keyCode) {
		  case 48:
		        keyState[MASK_NUM0] = false;
		        break;
	      case 49:
	        keyState[MASK_NUM1] = false;
	        break;
	      case 50:
	        keyState[MASK_NUM2] = false;
	        break;
	      case 51:
	        keyState[MASK_NUM3] = false;
	        break;
	      case 52:
	        keyState[MASK_NUM4] = false;
	        break;
	      case 53:
	        keyState[MASK_NUM5] = false;
	        break;
	      case 54:
	        keyState[MASK_NUM6] = false;
	        break;
	      case 55:
	        keyState[MASK_NUM7] = false;
	        break;
	      case 56:
	        keyState[MASK_NUM8] = false;
	        break;
	      case 57:
	        keyState[MASK_NUM9] = false;
	        break;
	      case 42: 
	    	  keyState[MASK_STAR]=false;
	    	  break;
	    	  
	      case -8:
	    	  keyState[MASK_DEL]=false;
	    	  break;
	      default:
	        break;

	    }
	}
	
	public boolean isPressed(int mark) {
	    if (keyState[mark]) {
	      return true;
	    }
	    else {
	      return false;
	    }
	  }
//	********************************** 输入系统 ****************************
	   /**
	    *字母在键上的映射
	    */
	   String[] charMap = {
	       ",.1+_)(*&^%$#@",
	       "abc2",
	       "def3",
	       "ghi4",
	       "jkl5",
	       "mno6",
	       "pqrs7",
	       "tuv8",
	       "wxyz9",
	       "0",
	       " ",
	   };
	   //键位映射
	  byte[] keyValueMap = {
	      MASK_NUM1
	      , MASK_NUM2
	      , MASK_NUM3
	      , MASK_NUM4
	      , MASK_NUM5
	      , MASK_NUM6
	      , MASK_NUM7
	      , MASK_NUM8
	      , MASK_NUM9
	      , MASK_NUM0
	      , MASK_POUND
	  };
//	  String text = ""; //输入的字符串
	  long keyInLastTime = 0; //之前最后一次按键的时间
	  long keyInCurTime = 0; //当前时间
	  int charMapIndex = -1; //指向某个键的串
	  int charPointer = 0; //指向某键的第几个字母
	   int DELAY_COMPLETE = 500; //最大延迟时间算一个字母完成
	  int DELAY_CHANGE = 120; //按键切换时间
//	   byte INPUT_CUT_CHAR = MASK_STAR; // 设删除键

	  /**
	   *供外界调用的一个方法，可以画出正在输入的字符串，此方法每个tick调用一次即可
	   *
	   *，返回用户输入的一个字符串
	   */
	  public String drawtext(Graphics g, int x, int y) {
		x=x+smallFont.stringWidth(text)+10;
	    //先求出这个字符
	    if (charMapIndex < 0) { //上次输入的字母已完毕的情况下（包括第一次输入之前也为-1）

	      for (int i = 0; i < keyValueMap.length; i++) {
	        //如果按了某个键
	        if (isPressed(keyValueMap[i])) {
	          keyInLastTime = System.currentTimeMillis();
	          charMapIndex = i;
	          charPointer = 0;
	          
	          break;
	        }
	      }
	    }
	    else {
	      //曾有一次按键的基础上，则如下处理
	      keyInCurTime = System.currentTimeMillis(); //取得当前时间
	      //如果和上次按的是同一键
	      if (isPressed(keyValueMap[charMapIndex])) {
	        //是切换字母
	        if (keyInCurTime - keyInLastTime > DELAY_CHANGE) {
	          charPointer++;
	          if (charPointer >= charMap[charMapIndex].length()) { //如果超界，则归0
	            charPointer = 0;
	          }
	          keyInLastTime = keyInCurTime;
	        }
	      }
	      else { //此时用户不是切换字母，而是切换了按键则如下
	        for (int i = 0; i < keyValueMap.length; i++) {
	          //如果按了某个键
	          if (isPressed(keyValueMap[i])) {
	            //添加上个字母到字符串
	            text += charMap[charMapIndex].charAt(charPointer);
	            //复位
	            keyInLastTime = System.currentTimeMillis();
	            charMapIndex = i;
	            charPointer = 0;
	            break;
	          }
	        }

	      }
	      if (keyInCurTime - keyInLastTime > DELAY_COMPLETE) { //停顿一段时间后，则表示一个字母输入完成
	        if (charMapIndex >= 0) {
	          text += charMap[charMapIndex].charAt(charPointer); ; //进入完成阶段
	          charPointer = 0;
	          charMapIndex = -1;
	        }
	      }
	    }

	    //如果是删除键，则让字符串减一个
	    if (isPressed(MASK_STAR) || isPressed(MASK_DEL)) {
	      if (text.length() > 0) {
	        text = text.substring(0, text.length() - 1);
	      }
	    }

	    //再画出这个字符及相应的串
	    int charWidth = smallFont.charWidth('W') + 1;
	    int charHeight = smallFont.getHeight() + 1;
	    String drawStr = text;
	    if (charMapIndex >= 0) {
	      String keyStr = charMap[charMapIndex];
	      drawStr += keyStr.charAt(charPointer);
	      //画背景
	      if(x+charWidth*keyStr.length()>ui.xmlForm.frm_Width){//换行
	    	  x=ui.xmlForm.frm_Width-charWidth*keyStr.length()-10;
	    	  y=y+charHeight-6;
	      }
	      g.setColor(255,255,255);
	      g.fillRect(x, y, charWidth*keyStr.length(), charHeight+5);
	      g.setColor(0);
	      //画该键上的字母
	      for (int i = 0; i < charMap[charMapIndex].length(); i++) {
	        //如果是当前字符，作一个标志
	        if (i == charPointer) {
	        	g.setColor(81	, 159, 179);
	          g.drawChar(keyStr.charAt(i), x + charWidth * i, y  + 1,
	                     g.TOP | g.LEFT);
	        }
	        else {
	        	g.setColor(0);
	          g.drawChar(keyStr.charAt(i), x + charWidth * i, y  + 4,
	                     g.TOP | g.LEFT);
	        }
	      }
	      g.setColor(0);
	      g.drawLine(x, y + charHeight  + 2, x + charWidth * keyStr.length(),
	                 y + charHeight  + 2); //画一根底线
//	      g.drawString(" (*键删除)", x + charWidth * keyStr.length(),
//	                   y + charHeight + 4, g.TOP | g.LEFT);
	    }
	    //画出字符串
//	    g.drawString(drawStr, x, y, g.TOP | g.LEFT);

	    return text;
	   
	  }
	  //************************输入系统结束************************/

	  public boolean hasSpecialChar(String str,int type) {
	        if (str == null || str.length() == 0) {
	               
	                return false;
	        }
	        char c;
	        for (int i = 0; i < str.length(); i++) {
	                c = str.charAt(i);
	                switch(type){
	                case 0://只能为数字
	                	if ((c < '0' || c > '9'))                  
	                    return true;
	                	break;
	                case 1://可为字母或数字
	                	if ((c < '0' || c > '9') && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))                  
	                    return true;
	                	break;
	                case 2://可为字母，数字，或汉字
	                	if ((c < '0' || c > '9') && (c < 'a' || c > 'z')
	                			&& (c < 'A' || c > 'Z') && (c < 0x4e00 || c > 0x9fa5))                 		
	                		return true;
	                	
	                	break;
	                case 3:
	                	if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))                  
		                    return true;
		                	break;
	                	
	                }
	        }

	       
	        return false;
	}
	class InputDialog extends TextBox implements CommandListener{

		private Command accept=new Command("确定",Command.OK,1);
		private Command cancel=new Command("取消",Command.CANCEL,1);
		private Alert alert=new Alert("系统提示");
		private CMForm xWForm;
		public InputDialog(String title, String text, int maxSize, int constraints, CMForm xWForm) {
			super(title, text, maxSize, constraints);
			this.xWForm=xWForm;
			addCommand(accept);
			addCommand(cancel);
			setCommandListener(this);

		}
		
		public void commandAction(Command cmd, Displayable display) {
			boolean norm=false;
			String date=null;
			if(cmd.equals(accept)){
				if(length==0){
					norm=true;
				}else
					
				if(getString().length()>length){
					norm=false;
					alert.setString("格式错误，长度不能超过"+length+"个字符");
					alert.setTimeout(1000);
					CMForm.display.setCurrent(alert);
				}else
			
				if(inputStyle==TextField.NUMERIC){
					//只能输入数字
					if(hasSpecialChar(getString(),0)){
					}else{
						norm=true;
					}				
				}else if(DATETEXT==1){//日期
					String str=getString();					
					if(str.length()==6){						
						if(hasSpecialChar(str,0)){
							norm=false;
						}else{							
							if(Integer.parseInt(str.substring(0, 2))>=0){
								date="20"+str.substring(0, 2)+"-";
								if(Integer.parseInt(str.substring(2, 4))>0 && Integer.parseInt(str.substring(2, 4))<12){
									date=date+str.substring(2, 4)+"-";
									if(Integer.parseInt(str.substring(4, 6))>0 && Integer.parseInt(str.substring(4, 6))<31){
										date=date+str.substring(4, 6);
										norm=true;
									}else{
										norm=false;
									}
								}else{
									norm=false;
								}
							}else{
								norm=false;
							}							
						}
						if(!norm){
							alert.setString("日期格式错误");
							alert.setTimeout(1000);
							CMForm.display.setCurrent(alert);
						}
					}else  if(str.length()==8){
						if(hasSpecialChar(str,0)){
							norm=false;
						}else{
							
							if(Integer.parseInt(str.substring(0, 4))>=0){
								date=str.substring(0, 4)+"-";
								if(Integer.parseInt(str.substring(4, 6))>0 && Integer.parseInt(str.substring(4, 6))<12){
									date=date+str.substring(4, 6)+"-";
									if(Integer.parseInt(str.substring(6, 8))>0 && Integer.parseInt(str.substring(6, 8))<31){
										date=date+str.substring(6,8);
										norm=true;
									}else{
										norm=false;
									}
								}else{
									norm=false;
								}
							}else{
								norm=false;
							}							
						}
						if(!norm){
							alert.setString("日期格式错误");
							alert.setTimeout(1000);
							CMForm.display.setCurrent(alert);
						}
					}
					else{
						norm=false;
						alert.setString("日期格式错误");
						alert.setTimeout(1000);
						CMForm.display.setCurrent(alert);
					}
				}else if(LETTER==1){//字母
					
					if(hasSpecialChar(getString(),3)){
						alert.setString("格式错误，只能输入字母");
						alert.setTimeout(1000);
						CMForm.display.setCurrent(alert);
					}else{
						norm=true;
					}
				}else{
					norm=true;
				}
				if(norm){
					if(DATETEXT==1){
						/***************************************/
						if(iswriteStyle)
						setText(date);
						/***************************************/
					}else{
						/***************************************/
						if(iswriteStyle)
						setText(getString());
						/***************************************/
					}
					xWForm.setFullScreenMode(true);
					CMForm.display.setCurrent(xWForm);
					ui.selector.inInputItem=false;

				}		
			}else if(cmd.equals(cancel)){
				xWForm.setFullScreenMode(true);
				CMForm.display.setCurrent(xWForm);
				ui.selector.inInputItem=false;
			}
			
		}
	}
}
