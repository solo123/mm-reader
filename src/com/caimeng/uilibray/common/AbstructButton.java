package com.caimeng.uilibray.common;

import com.caimeng.uilibray.container.ButtonGroup;


public class AbstructButton extends BaseControl {

	public AbstructButton() {
		super();
	}

	public AbstructButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO 自动生成构造函数存根
	}

	//用于menu
	public int level=0;
	
	public String text = "";

	public boolean selected = false;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setLabel(String label) {
		this.text = label;
	}

	public String getLabel() {
		return text;
	}

	
	private ButtonGroup group = null;

	public void setGroup(ButtonGroup group) {
		// TODO 自动生成方法存根
		this.group = group;
	}

	public ButtonGroup getGroup() {
		return group;
	}

	public void keyPressed(int keyCode){
		super.keyPressed(keyCode);
		ButtonGroup group = getGroup();
		if (group != null) {
			group.setSelected(this);
		} else {
			selected = !selected;
		}
	}
	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
		
	}

}
