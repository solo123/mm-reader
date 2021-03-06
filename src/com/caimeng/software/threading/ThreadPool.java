package com.caimeng.software.threading;

import java.util.Vector;

/**
 * 绾跨▼姹�
 * 
 * @author minco
 * 
 */
public class ThreadPool {
	private static final int DEFAULT_MAX_WORKTHREADS =6;//榛樿鍚姩6鏉＄嚎绋�
	private static int maxWorkThreads = DEFAULT_MAX_WORKTHREADS;
	private static boolean initialized = false;
	static Vector pooledWorkThreads = null;
	private static WorkThread innerThread = null;
	private static Vector quequeWorkTaskDatas = null;
	private static Object lock = new Object();
	private static boolean isWating = false;

	private ThreadPool() {
		
	}
	/**
	 * 璁剧疆鏈�澶х嚎绋嬫暟
	 * 濡傛灉褰撳墠绾跨▼姹犱腑绾跨▼鏁板ぇ浜庤缃暟锛屽垯灏嗗噺灏戠嚎绋嬫睜涓嚎绋嬶紝鍚﹀垯澧炲姞绾跨▼
	 */
	public static void setMaxWorkThreads(int maxWorkThreads) {

		//ensureInit();

		int count = maxWorkThreads - ThreadPool.maxWorkThreads;

		resizeWorkThreads(count);
		ThreadPool.maxWorkThreads = maxWorkThreads;
	}

	/**
	 * 纭繚绾跨▼姹犲凡缁忓垵濮嬪寲
	 */
	 static {
		if (!initialized) {
			pooledWorkThreads = new Vector();
			quequeWorkTaskDatas = new Vector();

			resizeWorkThreads(DEFAULT_MAX_WORKTHREADS);

			WorkTaskData data = new WorkTaskData(new WaitCallback() {
				public void execute(Object state) {
					dispatchWorkItem();
				}
			}, null);
			innerThread = new WorkThread();
			innerThread.setWorkTaskData(data);
			innerThread.start();

			initialized = true;
		}
	}

	/**
	 * 杩斿洖鏈�澶х嚎绋嬫暟
	 * 
	 * @return
	 */
	public static int getMaxWorkThreads() {
		return maxWorkThreads;
	}

	/**
	 * 灏嗗伐浣滀换鍔℃彁浜ょ粰绾跨▼姹�
	 * 
	 * @param waitCallback
	 *            宸ヤ綔浠诲姟鐨勫洖璋冨疄渚�
	 */
	public static void queueWorkItem(WaitCallback waitCallback) {
		queueWorkItem(waitCallback, null);
	}

	/**
	 * 灏嗗伐浣滀换鍔℃彁浜ょ粰绾跨▼姹�
	 * 
	 * @param waitCallback
	 *            瀹氫箟宸ヤ綔浠诲姟鐨勫洖璋�
	 * @param state
	 *            瑕佷紶閫掔粰宸ヤ綔浠诲姟鐨勫弬鏁�
	 */
	public static void queueWorkItem(WaitCallback waitCallback, Object state) {
		WorkTaskData data = new WorkTaskData(waitCallback, state);
		quequeWorkTaskDatas.insertElementAt(data, quequeWorkTaskDatas.size());
		//鍞ら啋璋冨害绾跨▼锛�
		if(isWating){
			synchronized (lock) {
				lock.notify();
				isWating = false;
			}
		}
     }

	/**
	 * 鍒嗘淳宸ヤ綔
	 * 
	 * @param state
	 */
	private static void dispatchWorkItem() {
		WorkThread wt = null;
		while (true) {
			WorkTaskData data = null;
			if (quequeWorkTaskDatas.size() > 0) {
                   data = (WorkTaskData) quequeWorkTaskDatas.firstElement();
				   quequeWorkTaskDatas.removeElement(quequeWorkTaskDatas.firstElement());
					while (true) {
					int index = -1;
					int size=pooledWorkThreads.size();
					for (int i = 0; i < size; i++) {
						if (((WorkThread) pooledWorkThreads.elementAt(i))
								.IsIdle()) {
							index = i;
							break;
						}
					}
					if (index > -1) {
					   
						for (int i = 0; i < size; i++) {  //宸茬粡鍚姩鐨勭嚎绋嬭缃瓑绾т负鏈�浣庣骇
							if (!((WorkThread) pooledWorkThreads.elementAt(i))
									.IsIdle()) {
								((WorkThread) pooledWorkThreads.elementAt(i)).setPriority(WorkThread.MIN_PRIORITY);
							}
						}
						wt = (WorkThread) pooledWorkThreads.elementAt(index);
						wt.setPriority(WorkThread.MAX_PRIORITY);//鏈�鍚庝竴涓惎鍔ㄧ殑璁剧疆涓洪珮绾�
						wt.setWorkTaskData(data);
						if(index<=DEFAULT_MAX_WORKTHREADS&&index+1<pooledWorkThreads.size()) //鍒ゆ柇鏄惁鍙堝彲浠ュ洖鏀剁殑鏉′欢
						{
							recycleThread();
						}
						break;
					} else {
						addThreadToPooled(); //绾跨▼涓嶅鐢ㄦ坊鍔犳柊绾跨▼
					}
			       }
				  try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				    synchronized (lock) {
					try {
						
						if(quequeWorkTaskDatas.size()==0)
						{
							isWating = true;
							lock.wait();
						}					
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			  }
			}
	}
	/***
	 * 鍒濆鍖栫嚎绋嬫睜
	 * @param threads
	 */
	private static void resizeWorkThreads(int threads) {
		// System.out.println("threads:"+threads);
		if (threads > 0) {
			for (int i = 0; i < threads; i++) {
				WorkThread wt = new WorkThread();
				wt.setPriority(Thread.MIN_PRIORITY);
				wt.start();
				pooledWorkThreads.addElement(wt);
			}
		}else{
			for (int i = 0; i < 0-threads; i++) {
				int count = pooledWorkThreads.size();
				if(count > 0){
					WorkThread wt = (WorkThread)pooledWorkThreads.elementAt(count-1);
					pooledWorkThreads.removeElementAt(count-1);
					while(!wt.IsIdle()){
						try {
							Thread.sleep(0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					}
					wt.exit();
				}				
			}
		}
	}
	/**
	 * 鍚戠嚎绋嬫睜娣诲姞鏍煎鐨勭嚎绋�
	 */
	private  static void addThreadToPooled()
	{
		WorkThread wt = new WorkThread();
		wt.setPriority(Thread.MIN_PRIORITY);
		wt.start();
		pooledWorkThreads.addElement(wt);
		//System.out.println("娣诲姞绾跨▼");
	}
	/**
	 * 鍥炴敹鏍煎鐨勬坊鍔犵殑绾跨▼
	 */
	private  static void recycleThread()
	{
		int idleSize=0;
		for(int i=0;i<DEFAULT_MAX_WORKTHREADS;i++)  
		{
			if(((WorkThread)pooledWorkThreads.elementAt(i)).IsIdle())
			{
				idleSize++;  //鍩烘湰绾跨▼绌洪棽鏁�
			}
		}
		if(idleSize>=2)//鍩烘湰绾跨▼鏁扮┖闂茶秴杩�★紝鍥炴敹閮ㄥ垎绾跨▼
		{
			for(int i=DEFAULT_MAX_WORKTHREADS;i<pooledWorkThreads.size();i++)
			{
				if(((WorkThread)pooledWorkThreads.elementAt(i)).IsIdle())//鍥炴敹
				{  
				   ((WorkThread)pooledWorkThreads.elementAt(i)).exit();//閫�鍑�
				   pooledWorkThreads.removeElementAt(i);
			      // System.out.println("鍥炴敹绾跨▼");
				}
			}
			System.gc();
		}
	}
	
	/**
	 * 妫�娴嬬嚎绋嬫睜涓殑绾跨▼鏄惁閮藉浜庣┖闂茬姸鎬�
	 * 
	 * @return
	 */
	private static boolean isIdle() {
		if (quequeWorkTaskDatas.size() > 0)
			return false;
		
		for (int i = 0; i < maxWorkThreads; i++) {
			WorkThread wt = (WorkThread) pooledWorkThreads.elementAt(i);

			if (!wt.IsIdle())
				return false;
		}

		return true;
	}

	/**
	 * 娓呯┖绾跨▼姹�
	 */
	public static void Destroy() {
		while (!isIdle()){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < maxWorkThreads; i++) {
			WorkThread wt = (WorkThread) pooledWorkThreads.elementAt(i);
			wt.exit();
		}
		pooledWorkThreads.removeAllElements();
	}

}
