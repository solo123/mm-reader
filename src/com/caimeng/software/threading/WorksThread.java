package com.caimeng.software.threading;


public class WorksThread extends Thread  {

	private WaitCallback callback;
	private Object state;
	public WorksThread(WaitCallback callback, Object state){
		this.callback = callback;
		this.state = state;
	}
	public void run() {		
		if(this.callback != null){
			this.callback.execute(this.state);
		}
	}


	
}
