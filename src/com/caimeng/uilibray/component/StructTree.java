package com.caimeng.uilibray.component;


import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.caimeng.uilibray.common.List;
import com.caimeng.uilibray.info.Key;
import com.caimeng.uilibray.skin.UIManager;
import com.caimeng.uilibray.utils.ArrayList;
import com.caimeng.uilibray.utils.ImageBuilder;
import com.caimeng.uilibray.utils.ImageDiv;
import com.caimeng.uilibray.utils.NodeItem;



public class StructTree extends List {

	private ArrayList lists = new ArrayList();

	private ArrayList root = new ArrayList();

	// private ArrayList newsList = new ArrayList();

	// private ArrayList funsList = new ArrayList();

	// private ArrayList teachList = new ArrayList();

	// ////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////
	// private NodeItem newsNode;
	// private NodeItem funsNode;
	// private NodeItem teachNode;
	// ////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////
	private Image plus;

	private Image subtract;

	private int curIndex;

	private int Yoffset;

	private int offY;

	private int startY;

	private int startX;

	private int count = 0;

	private Vector vList = new Vector();

	private Vector itemName;

	private Vector secondaryItemName;

	private String[] rootName;
	
	//private int [] newsMessage;
	
	private int [] totalMessage;
	
	//private Image[] imgs;

	private int layoutStyle = 1;

	//private boolean pic;

	private final int one_level_text = 1;//style

	private final int two_level_text = 2;

	public StructTree(int x, int y, int width, int height, Vector c1,
			String[] rootname) {
		super(x, y, width, height);
		startX = x;
		startY = y;
		plus = UIManager.plus_icon;
		subtract =UIManager.subtract_icon;
		
		{
			this.initItemName(c1);
			this.initRootName(rootname);
			this.initList(rootname.length);
			
		
			for (int j = 0; j < vList.size(); j++) {
				
				for (int i = 0; i < itemName.size(); i++) {
					if(((String[])itemName.elementAt(j))[i].toString()!=null){
						
						this.addNode((ArrayList) vList.elementAt(j), new NodeItem(
								((String[])itemName.elementAt(j))[i].toString(), "", "", NodeItem.ITEM, false,
								subtract, null,rootName[j],null,(j+1)*i+1));
					}
					
					
				}
			}
			for (int i = 0; i < rootName.length; i++) {
				root.add(new NodeItem(rootName[i], "", null, NodeItem.GROUP,
						false, plus, (ArrayList) vList.elementAt(i),null,null));
			}
//			totalMessage=new int [itemName.size()];
//			for(int i=0;i<itemName.size();i++)
//			{
//				totalMessage[i]=((String[])itemName.elementAt(i)).length;
//			}
			
			/*
			 * for (int i = 0; i < 10; i++) { addNode(newsList, new
			 * NodeItem("新闻结点: " + (i + 1), "url = " + i, NodeItem.ITEM, false,
			 * subtract, null)); }
			 * 
			 * for (int i = 0; i < 10; i++) { addNode(funsList, new
			 * NodeItem("娱乐结点: " + (i + 1), "url = " + i, NodeItem.ITEM, false,
			 * subtract, null)); }
			 * 
			 * for (int i = 0; i < 4; i++) { addNode(teachList, new
			 * NodeItem("科技结点: " + (i + 1), "url = " + i, NodeItem.ITEM, false,
			 * subtract, null)); }
			 * 
			 * root.add(new NodeItem("新闻根结点", null, NodeItem.GROUP, false, plus,
			 * newsList)); root.add(new NodeItem("娱乐根结点", null, NodeItem.GROUP,
			 * false, plus, funsList)); root.add(new NodeItem("科技根结点", null,
			 * NodeItem.GROUP, false, plus, teachList)); root.add(new
			 * NodeItem("其它结点", "", NodeItem.ITEM, false, subtract, teachList));
			 */
		}// 初始化部分
		loadData();
	}

	public StructTree() {
	}

	public Vector rootNoItem=new Vector();
	public StructTree(int x, int y, int width, int height,Vector c1,
			String[] rootname, Vector c2,Image img) {
		super(x, y, width, height);
		startX = x;
		startY = y;
		plus = UIManager.plus_icon;
		subtract = UIManager.subtract_icon;
		this.initItemName(c1);
		this.initRootName(rootname);
		this.initList(rootname.length);
	
		this.secondaryItemName = c2;
		Vector v=new Vector();
		for(int i=0;i<itemName.size();i++)
		{
			if(rootName[i].substring(0, 1).equals("<") &&rootName[i].substring(rootName[i].length()-1,rootName[i].length()).equals(">")){
			}else{
				
				v.addElement(((String[])itemName.elementAt(i)).length+"");
			}
		}
		totalMessage=new int [v.size()];
		for(int i=0;i<v.size();i++){
			totalMessage[i]=Integer.parseInt(v.elementAt(i).toString());
		}
		if (secondaryItemName == null)
		this.layoutStyle = this.two_level_text;
		else
			this.layoutStyle = this.two_level_text;
		int num =0;
		for (int j = 0; j < vList.size(); j++) {
			
			int length=((String[])itemName.elementAt(j)).length;
			if(length>0){
//				state[j]=1;
			}else{
				rootNoItem.addElement(rootName[j]);
			}
			for (int i = 0; i < length; i++) {
				num++;
				if(((String[])itemName.elementAt(j))[i].toString()!=null){
					
					String label=((String[])itemName.elementAt(j))[i].toString();
					String name="";
					if(secondaryItemName!=null){
						if(((String[])secondaryItemName.elementAt(j))[i]!=null){
							name=((String[])secondaryItemName.elementAt(j))[i].toString();
						}
					}
					this.addNode((ArrayList) vList.elementAt(j), new NodeItem(label, "",name ,NodeItem.ITEM, false, subtract, null,rootName[j],null,num));
				}
			}
		}
		System.out.println("22");
		for (int i = 0; i < rootName.length; i++) {
			if(rootName[i].substring(0, 1).equals("<") &&rootName[i].substring(rootName[i].length()-1,rootName[i].length()).equals(">")){
				
			}else{
				
				root.add(new NodeItem(rootName[i], "", null, NodeItem.GROUP, false,plus, (ArrayList) vList.elementAt(i),null,img));
			}
		}
		
		loadData();
	}



	/*private Image getImage(String path) {
		try {
			return Image.createImage(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}*/

	public void initList(int num) {
		for (int i = 0; i < num; i++) {
			vList.addElement(new ArrayList());
			// vNodeItem.addElement(new NodeItem());
		}
	}

	public void initItemName(Vector c1) {
		this.itemName = c1;
		/*for(int i=0;i<c1.size();i++)
		{
			for(int j=0;j<((String[])c1.elementAt(i)).length;j++)
			{
				itemName[i][j]=((String[])c1.elementAt(i))[j].toString();
			}
			
		}*/
	}

	public void initRootName(String[] s) {
		this.rootName = s;
	}

	private void init() {
		if (lists.size() > 0) {
			lists.clear();
		}
	}

	private void appendItemNode(NodeItem node) {
		lists.add(node);
		//System.out.println("增加进列表向量的结点标题: " + node.getLabel());
	}

	public NodeItem getCurIndexNode(int index) {
		return (NodeItem) lists.get(index);
	}

	public String getCurIndexTitle(int index) {
		return getCurIndexNode(index).getLabel();
	}

	public int indexOf(String itemname){
		int number=0;
		number=itemName.indexOf(itemname);
		return number;
	}
	public int getCurIndexTotalIndex(int index)
	{
		int num=0;
		int list=0;
		boolean flag=false;
		for(int i=0;i<totalMessage.length;i++)
		{
			//num++;
			for(int j=0;j<totalMessage[i];j++)
			{
				
				if(getCurIndexNode(index).getGroupName().equals(rootName[i]))
					{
					//num+=j+i+1;
					for(int k=0;k<totalMessage.length;k++)
					{
						if(((NodeItem)root.get(k)).getSelected())
						{
							if(k>i)
							{
								list+=totalMessage[k];
							    System.out.println("pass"+totalMessage[k]);
							}
						}
							
					}
					num+=(curIndex-list);					
					//list=i;
					flag=true;
					break;
					}
			}
			if(flag)
			{
					break;	
			}
			//if(((NodeItem)root.get(i)).getSelected())
			num+=totalMessage[i];	
		}
		return num;
	}

	
	public String getCurIndexURL(int index) {
		return getCurIndexNode(index).getURL();
	}
	
	public String getCurIndexGroupName(int index)
	{
		return getCurIndexNode(index).getGroupName();
	}

	public void loadData() {
		init();
		for (int i = 0; i < root.size(); i++) {
			NodeItem rootNode = (NodeItem) root.get(i);
			if (rootNode.getType() == NodeItem.GROUP) {
				//System.out.println("树根=================================");
				//System.out.println("title==" + rootNode.getLabel());
				if (rootNode.getSelected() == true) {
					appendItemNode(rootNode);
					ArrayList contentList = (ArrayList) rootNode.getContentList();
					for (int j = 0; j < contentList.size(); j++) {
						NodeItem subNode = (NodeItem) contentList.get(j);
						appendItemNode(subNode);
					}
				} else {
					appendItemNode(rootNode);
				}
			} else if (rootNode.getType() == NodeItem.ITEM) {
				appendItemNode(rootNode);
				//System.out.println("树叶树叶树叶树叶--------------------");
			}
		}

	}
	/**
	 * 打开历史结点
	 * @param itemname
	 * @return
	 */
	public void openHistoryNode(String itemname)
	{
		int selectnum=0;
		for (int i = 0; i < root.size(); i++) {
			NodeItem rootNode = (NodeItem) root.get(i);
			if (rootNode.getType() == NodeItem.GROUP) {
				//appendItemNode(rootNode);
				ArrayList contentList = (ArrayList) rootNode
						.getContentList();
				for (int j = 0; j < contentList.size(); j++) {
					NodeItem subNode = (NodeItem) contentList.get(j);
					if(subNode.getLabel().equals(itemname)){
						rootNode.setSelected(true);
						rootNode.setIcon(subtract);
						selectnum=subNode.getSelectNum();
						this.loadData();
						curIndex=i+j+1;						
						break;
					}						
					//appendItemNode(subNode);
				}
			}/*else if (rootNode.getType() == NodeItem.ITEM) {
				//appendItemNode(rootNode);				
			}*/
		}
		
		
	}
	
	public int queryByAll(String itemname)
	{
		int num=0;
		for (int j = 0; j < vList.size(); j++) {			
				for (int i = 0; i < ((String[])itemName.elementAt(j)).length; i++) {
					num++;
					if(((String[])itemName.elementAt(j))[i].toString().equals(itemname)){
						return num;
					}
				}
			}
			
		return 0;
	}
	public int query(String itemname)
	{
		NodeItem[] nodes = this.getAllNodeItem();
//		System.out.println("....."+nodes.length);
		for(int i=0;i<nodes.length;i++)
		{
			if(nodes[i].getLabel().equals(itemname))
				return nodes[i].getSelectNum();
		}
	   return 0;
	}
	
	

	public void addNode(ArrayList al, NodeItem node) {
		al.add(node);
		loadData();
	}

	public void removeNode(ArrayList al, int index) {
		if ((index > 0) && (index < al.size())) {
			al.remove(index);
		}
		loadData();
	}

	public void removeAllNode(ArrayList al) {
		if (al.size() > 0) {
			al.clear();
		}
		loadData();
	}

	public NodeItem[] getAllNodeItem() {
		NodeItem[] nodes = new NodeItem[lists.size()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = (NodeItem) lists.get(i);
		}
		return nodes;
	}

	// get the tree length
	public int size() {
		return lists.size();
	}

	public void paint(Graphics g) {
		g.setColor(0,0,0);
		g.fillRect(startX, startY, width, height);
		int y = startY + Yoffset-34;
		int offset = 10;
		int fH = 0;
		g.setColor(255,255,255);
		NodeItem[] nodes = this.getAllNodeItem();
		if (this.Yoffset > height) {
			offY = Yoffset - height;
		}
		if (layoutStyle == 1) {
			fH = smallFont.getHeight();
			g.setFont(smallFont);
		}else 
		if (layoutStyle == 2) {
			/*fH = node.getType() == NodeItem.GROUP ? icon.getHeight()+smallFont.getHeight()+10
					: smallFont.getHeight() + smallFont.getHeight()+10;*/
			fH=34;
			//g.setFont(mediumFont);
			g.setFont(smallFont);
		}
		/*
		 * if(y+offY>startY+height-FH) Yoffset-=FH;
		 */
		
		if (layoutStyle == 2) {
			if (startY + Yoffset + offY < startY + fH) {
				y += fH;
			} else if (y + offY > startY + height - fH)
				y -= fH;
		}
		int news=0;
		g.setClip(startX, startY, width, height);
		for (int i = 0; i < nodes.length; i++) {
			NodeItem node = nodes[i];
			String rootLabel = node.getLabel();
			Image icon = node.getIcon();
			
			if (i == curIndex) {
				g.setClip(startX, y, width, fH);
				ImageDiv.drawJiuGong(g, UIManager.connect_bg, startX, y-offY-4, width,smallFont.getHeight()+15+5);
				g.setClip(startX, startY, width, height);
				if (node.getType() == NodeItem.GROUP) {					
					if (layoutStyle==1) {
						System.out.println("rsrsrsrsrsrs");
						if (icon != null)
							g.drawImage(icon, startX - icon.getWidth() - 8, y+ offY+5, 20);
						if(rootLabel!=null)
						g.drawString(rootLabel, startX, y + offY+5, 20);
					}else if(layoutStyle==2)
					{
						boolean found=false;
						for(int j=0;j<rootNoItem.size();j++){
							
							if(node.getLabel().equals(rootNoItem.elementAt(j).toString())){
								found=true;
								break;
							}
						}
						if(found){
							ImageBuilder.drawSmallImage(g, UIManager.file,startX+5, y+offY+5,  27, 24, 27, 0);

						}else{
							
							ImageBuilder.drawSmallImage(g, UIManager.file,startX+5, y+offY+5,  27, 24, 0, 0);
						}
						
						g.setClip(startX, startY, width-25-27, height);
						g.setColor(255, 255, 255);
						if(found){
							g.drawString(rootLabel+"  [0]", startX+10+27/*+logo.getWidth()*/, y+offY+5, 20);

						}else{
							
							g.drawString(rootLabel+"  ["+totalMessage[news]+"]", startX+10+27/*+logo.getWidth()*/, y+offY+5, 20);
						}
						g.setClip(startX, startY, width, height);
						if (icon != null)
						{
//						g.drawString("共"+totalMessage[news]+"条", ui.xWForm.getWidth()-icon.getWidth()-smallFont.stringWidth("共"+totalMessage[news]+"条")-10, y+offY+smallFont.getHeight(), 0);
						g.drawImage(icon, ui.xmlForm.getWidth()-icon.getWidth()-20, y+offY+(fH-icon.getHeight())/2, 0);
						}
					}
					news++;
				} else {
					if(rootLabel!=null){
						ImageBuilder.drawSmallImage(g, UIManager.file,width-25-27, y+offY+5,  27, 24, 27*2, 0);
						
						g.setClip(startX, startY, width-25-27, height);
						g.drawString(rootLabel, startX+10+27, y + offY+6, Graphics.TOP|Graphics.LEFT);
//						g.drawString(rootLabel, (width-60)/2, y + offY+6, Graphics.TOP|Graphics.HCENTER);
						ImageDiv.drawJiuGong(g, UIManager.line, startX, y + offY+ smallFont.getHeight()+5+5, width-startX*2, UIManager.line.getHeight());
						g.setClip(startX, startY, width, height);
						g.drawImage(UIManager.longline	, width/2,  y + offY+ smallFont.getHeight()+15, Graphics.BOTTOM|Graphics.HCENTER);
					}
				}
			} else {
//				g.setColor(0x000000);
				if (node.getType() == NodeItem.GROUP) {
					if (layoutStyle==1) {
						if (icon != null)
							g.drawImage(icon, startX - icon.getWidth() - 8, y+ offY, 20);
						//g.setFont(smallFont);
						if(rootLabel!=null)
						g.drawString(rootLabel, startX, y + offY, 20);
					}else if(layoutStyle==2)
					{
						g.setClip(startX, y, width, fH);
						for(int j=0;j<width/UIManager.browse_title_bg.getWidth()+1;j++)
						{
							g.drawImage(UIManager.browse_title_bg, startX+j*UIManager.browse_title_bg.getWidth(), y, 0);
							
						}
						g.setClip(startX, startY, width, height);
						boolean found=false;
						for(int j=0;j<rootNoItem.size();j++){
							
							if(node.getLabel().equals(rootNoItem.elementAt(j).toString())){
								found=true;
								break;
							}
						}
						if(found){
							ImageBuilder.drawSmallImage(g, UIManager.file,startX+5, y+offY+5,  27, 24, 27, 0);

						}else{
							
							ImageBuilder.drawSmallImage(g, UIManager.file,startX+5, y+offY+5,  27, 24, 0, 0);
						}
						g.setClip(startX, startY, width-25-27, height);
						
						g.setColor(255, 255, 255);
						if(rootLabel!=null){
							if(found){
								g.drawString(rootLabel+"  [0]", startX+10+27/*+logo.getWidth()*/, y+offY+5, 20);

							}else{
								
								g.drawString(rootLabel+"  ["+totalMessage[news]+"]", startX+10+27/*+logo.getWidth()*/, y+offY+5, 20);
							}
						}
						g.setClip(startX, startY, width, height);
						if (icon != null)
						{
//						g.drawString("共"+totalMessage[news]+"条", ui.xWForm.getWidth()-icon.getWidth()-smallFont.stringWidth("共"+totalMessage[news]+"条")-10, y+offY+smallFont.getHeight(), 0);
							g.setClip(startX, startY, width, height);
							g.drawImage(icon, ui.xmlForm.getWidth()-icon.getWidth()-20, y+offY+(fH-icon.getHeight())/2, 0);
						}
					}
					news++;
				} else {
					if(rootLabel!=null){
						ImageBuilder.drawSmallImage(g, UIManager.file,width-25-27, y+offY+5,  27, 24, 27*2, 0);
						g.setClip(startX, startY, width-25-27, height);
						g.drawString(rootLabel, startX+10+27, y + offY+6, Graphics.TOP|Graphics.LEFT);
//						g.drawString(rootLabel,  (width-60)/2, y + offY+6, Graphics.TOP|Graphics.HCENTER);
						g.setClip(startX, startY, width, height);
						g.drawImage(UIManager.longline	, width/2,  y + offY+ smallFont.getHeight()+15, Graphics.BOTTOM|Graphics.HCENTER);
//						ImageDiv.drawJiuGong(g, UIManager.line, startX, y + offY+ smallFont.getHeight()+5+5, width-startX*2, UIManager.line.getHeight());
						
					}
				}
			}
			y += fH;
		}
		int size=lists.size();
		int scrollH=UIManager.top_bg.getHeight();		
		int dh=(height-scrollH)/(size>0?size:size+1);

		if(((height-scrollH)%(size>0?size:size+1))>size/2){
			dh++;//手动四舍五入
		}
		
		int dy=dh*(curIndex);
		if(curIndex==size-1){
			int mistake=height-dy-dh-scrollH;
			if(mistake!=0)
				dy=dy+mistake;
			
		}
		g.setClip(0, 0,ui.xmlForm.frm_Width, ui.xmlForm.frm_Height);
		g.setColor(124,124,124);
		g.drawRect(ui.xmlForm.getWidth()-5,UIManager.top_bg.getHeight(),5,height);
		g.setColor(99, 99, 99);
		g.fillRect( ui.xmlForm.getWidth()-5+1,UIManager.top_bg.getHeight()+1, 4,height-1);
		g.setColor(124,124,124);
		g.drawRect(ui.xmlForm.getWidth()-5,UIManager.top_bg.getHeight()+dy-1, 5, scrollH+dh+1);
		g.setColor(253, 253, 253);
		g.fillRect( ui.xmlForm.getWidth()-4, UIManager.top_bg.getHeight()+ dy, 4, scrollH+dh);
		g.setClip(0, 0, ui.xmlForm.getWidth(), ui.xmlForm.getHeight());
		
	}
	
	public void clickOK(int keyCode){
		NodeItem curNode = this.getCurIndexNode(curIndex);
		//System.out.println("currentNode title: " + curNode.getLabel());
		if (curNode.getType() == NodeItem.GROUP) {
			if (curNode.getSelected() == false) {
				curNode.setSelected(true);
				curNode.setIcon(subtract);
			} else {
				curNode.setSelected(false);
				curNode.setIcon(plus);
			}

			this.loadData();
		} else {
			/*
			 * MyForm form = new MyForm(dis, this, curNode.getLabel());
			 * form.apendURL(curNode.getURL()); dis.setCurrent(form);
			 */
			// this.stopTimer();
			// 进行界面跳转的处理
			ui.selector.inComboItem = false;
			itemStateChanged(Key.FIREKEY(keyCode));
		}
	}

	public void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (!ui.selector.inComboItem) {
			ui.selector.inComboItem = true;
		} else {
			if (keyCode == Key.UPKEY(keyCode)) {
				if (curIndex > 0) {
					curIndex--;
					int fontH = layoutStyle == 1 ? smallFont.getHeight() : 34;
					if ((Yoffset < 0) && (count > curIndex)) {
						Yoffset += fontH;
						count--;
					}

				}
			} else if (keyCode == Key.DOWNKEY(keyCode)) {
				if (curIndex < this.size() - 1) {
					curIndex++;
					int fH = layoutStyle == 1 ? smallFont.getHeight() : 34;
					if ((curIndex * fH + startY) > height - fH) {
						this.Yoffset -= fH;
						count++;
					}
				}
			} else if (keyCode == Key.FIREKEY(keyCode)) {
				clickOK(keyCode);
			} else if (keyCode == Key.LEFT_SOFT_KEY(keyCode)) {

			} else if (keyCode == Key.RIGHT_SOFT_KEY(keyCode)) {

			} else if (keyCode == Key.LEFTKEY(keyCode) | keyCode == Key.RIGHTKEY(keyCode)) {
				ui.selector.inComboItem = false;
			}
		}
	}

	protected void keyRepeated(int keyCode) {
 
		if (keyCode == Key.UPKEY(keyCode)) {
			if (curIndex > 0) {
				curIndex--;
				int fontH = layoutStyle == 1 ? smallFont.getHeight() : 37;
				if ((Yoffset < 0) && (count > curIndex)) {
					Yoffset += fontH;
					count--;
				}

			}
		} else if (keyCode == Key.DOWNKEY(keyCode)) {
			if (curIndex < this.size() - 1) {
				curIndex++;
				int fH = layoutStyle == 1 ? smallFont.getHeight() : 37;
				if ((curIndex * fH + startY) > height - fH) {
					this.Yoffset -= fH;
					count++;
				}
			}
		}

	}

	public int getCurIndex() {
		return curIndex;
	}

	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}

}
