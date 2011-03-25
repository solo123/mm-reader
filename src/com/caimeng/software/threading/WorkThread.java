package com.caimeng.software.threading;


/**
 * 线程池的工作线程
 * 
 * @author minco
 * 
 */
class WorkThread extends Thread {
	private boolean isIdle;
	private WaitCallback callback;
	private Object state;
	private Object lock = new Object();
	private boolean isExit = false; //线程是否逄1�7出的标记

	public WorkThread() {
		this.isIdle = true;
	}

	public void run() {

		while (!isExit) {
			if (this.isIdle && this.callback != null) {

				try {
					this.isIdle = false;
					this.callback.execute(this.state);
					if(this.getPriority()==Thread.MAX_PRIORITY)	
				    {
						int size=ThreadPool.pooledWorkThreads.size();
						for (int i = 0; i < size; i++) {  //朄1�7高等级运行完后回复正常等纄1�7
							if (!((WorkThread) ThreadPool.pooledWorkThreads.elementAt(i)).IsIdle()) {
								((WorkThread) ThreadPool.pooledWorkThreads.elementAt(i)).setPriority(WorkThread.NORM_PRIORITY);
							}
						}
				    }
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		         this.isIdle = true;
				//System.out.println(this.callback + " exec complete!");
			}
           // 使线程进入休眠状怄1�7
			synchronized (lock) {
				try {
					lock.wait();
					this.isIdle = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 工作线程是否忙碌丄1�7
	 * 
	 * @return
	 */
	boolean IsIdle() {
		return this.isIdle && !this.isExit;
	}

	void setWorkTaskData(WorkTaskData data) {
		if (data != null) {
			this.callback = data.Callback;
			this.state = data.State;

			// 唤醒休眠的线稄1�7
			synchronized (lock) {
				lock.notify();
			}
		}
	}

	/**
	 * 逄1�7出等待循玄1�7
	 */
	void exit() {
		this.isExit = true;

		if (this.isIdle) {
			// 如果线程空闲，则唤醒线程，以便�1�7�1�7出等待循玄1�7
			synchronized (lock) {
				this.lock.notify();
			}
		}
	}
}
