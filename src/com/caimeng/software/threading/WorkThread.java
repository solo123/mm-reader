package com.caimeng.software.threading;


/**
 * 绾跨▼姹犵殑宸ヤ綔绾跨▼
 * 
 * @author minco
 * 
 */
class WorkThread extends Thread {
	private boolean isIdle;
	private WaitCallback callback;
	private Object state;
	private Object lock = new Object();
	private boolean isExit = false; //绾跨▼鏄惁閫�鍑虹殑鏍囪

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
						for (int i = 0; i < size; i++) {  //鏈�楂樼瓑绾ц繍琛屽畬鍚庡洖澶嶆甯哥瓑绾�
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
           // 浣跨嚎绋嬭繘鍏ヤ紤鐪犵姸鎬�
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
	 * 宸ヤ綔绾跨▼鏄惁蹇欑涓�
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

			// 鍞ら啋浼戠湢鐨勭嚎绋�
			synchronized (lock) {
				lock.notify();
			}
		}
	}

	/**
	 * 閫�鍑虹瓑寰呭惊鐜�
	 */
	void exit() {
		this.isExit = true;

		if (this.isIdle) {
			// 濡傛灉绾跨▼绌洪棽锛屽垯鍞ら啋绾跨▼锛屼互渚块��鍑虹瓑寰呭惊鐜�
			synchronized (lock) {
				this.lock.notify();
			}
		}
	}
}
