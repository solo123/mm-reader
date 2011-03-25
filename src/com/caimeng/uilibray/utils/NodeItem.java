package com.caimeng.uilibray.utils;

import javax.microedition.lcdui.Image;

public class NodeItem
{

    public static byte ITEM = 2;
	private String label; 
    private String url;
    private byte type; 
    private String context;
    private boolean selected;
    private Object contentList;  
    public static  byte GROUP = 1;  
    private Image icon;
    private String groupName;
    private Image companyLogo;
    private int selectNum=0;
    public NodeItem()
    {
        
    }
    public NodeItem(String label, String url,String context, byte type, boolean selected, Image icon, Object contentList,String groupName,Image companyLogo,int selectNum)
    {
        this.label = label;
        this.url = url;
        this.context=context;
        this.type = type;
        this.selected = selected;
        this.icon = icon;
        this.contentList = contentList;
        this.groupName=groupName;
        this.companyLogo=companyLogo;
        this.selectNum=selectNum;
    }
    public NodeItem(String label, String url,String context, byte type, boolean selected, Image icon, Object contentList,String groupName,Image companyLogo)
    {
        this.label = label;
        this.url = url;
        this.context=context;
        this.type = type;
        this.selected = selected;
        this.icon = icon;
        this.contentList = contentList;
        this.groupName=groupName;
        this.companyLogo=companyLogo;
       // this.selectNum=selectNum;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    public void setURL(String url)
    {
        this.url = url;
    }

    public String getURL()
    {
        return url;
    }

    public void setType(byte type)
    {
        this.type = type;
    }

    public byte getType()
    {
        return type;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setIcon(Image icon)
    {
        this.icon = icon;
    }

    public Image getIcon()
    {
        return icon;
    }

    public void setContentList(Object contentList)
    {
        this.contentList = contentList;
    }

    public Object getContentList()
    {
        return contentList;
    }
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Image getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(Image companyLogo) {
		this.companyLogo = companyLogo;
	}
	public int getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(int selectNum) {
		this.selectNum = selectNum;
	}
}
