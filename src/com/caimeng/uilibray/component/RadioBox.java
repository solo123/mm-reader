package com.caimeng.uilibray.component;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.info.ColorAttribute;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.ImageDiv;



public class RadioBox extends List {

	public RadioBox() {
		super();
	}

	public RadioBox(int x, int y, int width, int height) {
		super(x, y, width, height);
		onShowSelectedItemNum = -1;
		itemHeight = smallFont.getHeight();
//		bgTricolorR = Attribute.text_backR;
//		bgTricolorG = Attribute.text_backG;
//		bgTricolorB = Attribute.text_backB;
//		fgTricolorR = Attribute.text_foreR;
//		fgTricolorG = Attribute.text_foreG;
//		fgTricolorB = Attribute.text_foreB;
	}

	private int offsetY;

	private String title=null;
	
	public Vector itemID = new Vector();
	
	private Vector items = new Vector();
	
	private String ID;
	
	private int selectedNum;
	
	private int w=0;
	
	private static  int Vertical=0;//竖方向
	
	private static int Horizontal=1;//横向

	private int mode=0;//默认为竖向排列
	public void setSelectedItem(int itemNum) {
		selectedNum = itemNum;
		onShowSelectedItemNum=itemNum-1;
	}

	public void addItem(String item) {
		items.addElement(item);
		itemSum = items.size();
		int dx=UIManager.select_block.getWidth()/4+10;
		if(smallFont.stringWidth(item)+dx>width) {
			width=smallFont.stringWidth(item)+dx;
		}
		if(mode==0){
			
			this.height+=smallFont.getHeight();
		}else{
			w=w+13+smallFont.stringWidth(item)+3;
		
		}
		//selectedNum = onShowSelectedItemNum + 1;
	}

	public void setListData(Vector data) {
		items = data;
		itemSum = items.size();
		for(int i=0;i<itemSum;i++)
		{
			if(smallFont.stringWidth(data.elementAt(i).toString())>width) {
				width=smallFont.stringWidth(data.elementAt(i).toString())+10+UIManager.select_block.getWidth()/4;
			}
			if(mode==0){
				
				this.height+=smallFont.getHeight();
			}
		}
	}

	public void setMode(int mode){
		this.mode=mode;
	}
	public void setSelectedNum(int num)
	{
		this.selectedNum=num;
	}
	public int getSelectedNum()
	{
		return selectedNum;
	}
	int posX = 0;
	int posY = 0;	
	public void keyPressed(int keyCode) {
		if(this.isPass()){
			return;
		}
		if(mode==0){		
			if (!ui.selector.checkBox) {
				ui.selector.checkBox = true;
			} else {
				if (keyCode == Key.UPKEY(keyCode)) {
					if (onShowSelectedItemNum == -1) {
						 ui.selector.checkBox = false;
					} else if (onShowSelectedItemNum > -1) {
						onShowSelectedItemNum--;
						/*if(y+ui.offsetY<40){
							ui.offsetY+=20;
						}*/
						
					}
					
				} else if (keyCode == Key.DOWNKEY(keyCode)) {
					if (onShowSelectedItemNum == (itemSum - 2)) {
						 ui.selector.checkBox = false;
						 
					} else if (onShowSelectedItemNum < (itemSum - 2)) {
						onShowSelectedItemNum++;
						/*if(height+y+ui.offsetY>ui.xWForm.frm_Height-40){
							ui.offsetY-=20;
						}*/
					}
					
				} else if ((keyCode == Key.RIGHTKEY(keyCode)) || (keyCode == Key.LEFTKEY(keyCode))) {
					
						ui.selector.button = false;
				} else if (keyCode == Key.FIREKEY(keyCode)) {
					selectedNum = onShowSelectedItemNum + 1;
				}
			}
		}else if(mode==1){
			
		
//				if(!ui.selector.checkBox){
//					ui.selector.checkBox = true;
//				}
			if(!ui.selector.radioBox){
				
				ui.selector.radioBox=true;
			}
				if (keyCode == Key.LEFTKEY(keyCode)) {
					if (onShowSelectedItemNum == -1) {
//						ui.selector.checkBox = false;
					} else if (onShowSelectedItemNum > -1) {
						onShowSelectedItemNum--;
					}
				} else if (keyCode == Key.RIGHTKEY(keyCode)) {
					if (onShowSelectedItemNum == (itemSum - 2)) {
//						ui.selector.checkBox = false;
					}else if (onShowSelectedItemNum < (itemSum - 2)) {
						onShowSelectedItemNum++;
					}
				}else if (keyCode == Key.FIREKEY(keyCode)) {
					selectedNum = onShowSelectedItemNum + 1;
					
				}else if(keyCode==Key.UPKEY(keyCode)|keyCode==Key.DOWNKEY(keyCode)){
//					ui.selector.checkBox=false;
					ui.selector.radioBox=false;
				}
		}
		itemStateChanged(keyCode);
		
	}

	public void keyReleased(int keyCode) {
		itemStateChanged(keyCode);
	}

	public boolean removeItem(int index) {
		try {
			items.removeElementAt(index);
			itemSum = items.size();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeAllItem() {
		try {
			items.removeAllElements();
			itemSum = items.size();
			height=smallFont.getHeight();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getSelectedItem() {
		return items.elementAt(selectedNum).toString();
	}
	public int getIndexof(String itemname){
		return items.indexOf(itemname);
	}

	public void paint(Graphics g) {
		//g.setClip(0, 0, this.ui.xWForm.getWidth(), this.ui.xWForm.getWidth());
//		g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
		g.setColor(0);
			
			posX = x + ui.offsetX;
			posY = y + ui.offsetY;
			/*if(focus){
				if(height<=ui.xWForm.frm_Height-30)
				if(posY+height+itemHeight-10>ui.xWForm.frm_Height-30){
					ui.offsetY=ui.offsetY-(ui.xWForm.frm_Height/2);
					ui.xWForm.repaint();
				}
			}*/
			if(title!=null)
				{
				if(posX+smallHeavryFont.stringWidth(title)>ui.xmlForm.frm_Width){
					x=0;
					y=posY+smallHeavryFont.getHeight()+5;
				}
					if(mode==0){
						posY = y + ui.offsetY+smallHeavryFont.getHeight()+5;
						posX=x+ui.offsetX+10;
					}else if(mode==1){
						posX=x+ui.offsetX+smallFont.stringWidth(title);
						if(title.length()>5){
							
							posX = x + ui.offsetX+smallFont.stringWidth(":")+smallFont.stringWidth("用")*title.length();
//						posX=width-w;
							if(posX+posX>width){
								posX = width-w;
								posY=y+ui.offsetY+smallHeavryFont.getHeight()+5;
							}else{
								posX=width-w;
							}
						}else{
							posX=width-w;
							
						}
					}
					g.setFont(smallHeavryFont);
					
					g.drawString(title + ":",x, y + ui.offsetY, g.TOP|g.LEFT);
				}
			
			g.setColor(ColorAttribute.label_context);
			g.fillRect(posX, posY, width, height-(title!=null?smallFont.getHeight()-5:0));
			g.setFont(smallFont);
			if(mode==0){
				
				if ((onShowSelectedItemNum + 2) * itemHeight > height) {
					offsetY = (onShowSelectedItemNum + 2) * itemHeight - height-(title!=null?smallFont.getHeight():0);
				} else {
					offsetY = 0;
				}
				
				ImageDiv.drawJiuGong(g, UIManager.select_bright, posX - 3, posY - 3, width + 10,height-(title!=null?smallFont.getHeight()-5:0));
			}
			if (onShowSelectedItemNum + 1 >= 0) {
				if(mode==0){
					/*if(focus)
					ImageDiv.drawJiuGong(g, UIManager.connect_bg, posX, posY
							+ (onShowSelectedItemNum + 1) * itemHeight - offsetY,
							width, itemHeight);*/
				}else if(mode==1){
//					ImageDiv.drawJiuGong(g, UIManager.select_bright, posX+items.elementAt(onShowSelectedItemNum+1).toString(), posY
//							,
//							width, itemHeight);
				}
				
			}
			int clipx = g.getClipX();
			int clipy = g.getClipY();
			int clipwidth = g.getClipWidth();
			int clipheight = g.getClipHeight();
//			g.setClip(posX, posY, width, height);
//			g.setColor(fgTricolorR, fgTricolorG, fgTricolorB);
			
			g.setColor(0);
			int length = items.size();
			int tempX=posX;
			for (int i = 0; i < length; i++) {
				if (items.elementAt(i) != null) {
//					g.drawImage(i==selectedNum?UIManager.select_block:UIManager.select_block, posX,posY + i* itemHeight - offsetY + 2,20);	
					if(mode==0){
						
						if(i==selectedNum){																							
							ImageBuilder.drawSmallImage(g, UIManager.select_block, posX,posY + i* itemHeight - offsetY + 2, UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*2, 0);
						}else{
							ImageBuilder.drawSmallImage(g, UIManager.select_block, posX,posY + i* itemHeight - offsetY + 2, UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*3, 0);
						}
						g.setClip(clipx, clipy, clipwidth, clipheight);
//						g.setColor(0,0,0);
						if(i==onShowSelectedItemNum + 1 && focus){
							g.setColor(62, 155, 255);
							if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY>ui.xmlForm.frm_Height-40){
								ui.offsetY-=20;
								ui.xmlForm.repaint();
							}else if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY<20){
								ui.offsetY+=20;
								ui.xmlForm.repaint();
							}
						}else{
							g.setColor(52,52,52);
						}
						g.drawString(items.elementAt(i).toString(),  posX+(UIManager.select_block.getWidth()>>2),posY + i* itemHeight - offsetY + 2, Graphics.LEFT|Graphics.TOP);
					}else if(mode==1){
						if(i==selectedNum){																							
							ImageBuilder.drawSmallImage(g, UIManager.select_block, tempX+2,posY  - offsetY + 2, UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*2, 0);						
						}else{
							ImageBuilder.drawSmallImage(g, UIManager.select_block,tempX+2,posY - offsetY + 2,  UIManager.select_block.getWidth()/4, UIManager.select_block.getHeight(), UIManager.select_block.getWidth()/4*3, 0);
						}
						tempX=tempX+(UIManager.select_block.getWidth()>>2);
						g.setClip(clipx, clipy, clipwidth, clipheight);
//						g.setColor(0,0,0);
						if(i==onShowSelectedItemNum + 1 && focus){
							g.setColor(62, 155, 255);
						}else{
							g.setColor(52,52,52);
						}
						g.drawString(items.elementAt(i).toString(),  tempX+2,posY  - offsetY + 2, Graphics.LEFT|Graphics.TOP);
						tempX=tempX+smallFont.stringWidth(items.elementAt(i).toString())+5;
						
					}
					
					
					
				}
			}						
			/*g.drawImage(UIManager.scroll_btn_right, posX + width - 1, posY
					+ (height * (onShowSelectedItemNum + 1) / length),
					Graphics.RIGHT | Graphics.TOP);*/
			g.setClip(clipx, clipy, clipwidth, clipheight);
			if (focus) {
//				ImageDiv.drawJiuGong(g, UIManager.browse_title_bg, posX - 8, posY - 8,
//						width + 16, height + 16);
//				ImageBuilder.drawSmallImage(g, UIManager.select_block, posX - 8,posY - 8, 13, 13, 13*2, 0);
//				g.setColor(0, 0, 0);
//				g.drawRect(posX, posY, w+7+5, height);
				
			}
			/*if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY>ui.xWForm.frm_Height-40){
				ui.offsetY-=20;
				ui.xWForm.repaint();
			}else if(posY+ (onShowSelectedItemNum + 1)* itemHeight - offsetY<20){
				ui.offsetY+=20;
			}*/
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		System.out.println("title"+smallFont);
		int w=x + smallFont.stringWidth(":")+smallFont.stringWidth("用")*title.length();
		height += smallFont.getHeight() + 5;
		if(w+w>width){
			height+=smallFont.getHeight() + 5;
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

		
}
