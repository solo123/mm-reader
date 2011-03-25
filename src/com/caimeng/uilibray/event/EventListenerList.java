package com.caimeng.uilibray.event;
//List中每种Action只能注册一次
public class EventListenerList {

	ActionListener a = null;

	ItemStateListener i = null;
	
	MessageBoxListener m=null;
	
	ComboBoxListener c=null;
	public EventListenerList() {

	}

	public ComboBoxListener getComboBoxListener(){
		return c;
	}
	
	public void add(ComboBoxListener c){
		this.c=c;
	}
	public void add(ActionListener a) {
		this.a = a;
	}

	public void add(ItemStateListener i) {
		this.i = i;
	}

	public ActionListener getActionListener() {
		return a;
	}

	public ItemStateListener getItemStateListener() {
		return i;
	}
	
	public void add(MessageBoxListener m)
	{
		this.m=m;
	}
	
	public MessageBoxListener getMessageBoxListener ()
	{
	  return m;	
	}
	
	
}
