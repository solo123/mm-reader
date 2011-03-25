package com.caimeng.uilibray.event;


//每次按键松开的时候调用
public interface ActionListener extends EventListener{
	//public BaseControl item=null;
	public void actionPerformed(ItemEvent e);
}
