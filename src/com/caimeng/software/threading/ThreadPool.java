package com.caimeng.software.threading;

import java.util.Vector;

/**
 * 线程汄1�7
 * 
 * @author minco
 * 
 */
public class ThreadPool {
	private static final int DEFAULT_MAX_WORKTHREADS =6;//默认启动6条线稄1�7
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
	 * 设置朄1�7大线程数
	 * 如果当前线程池中线程数大于设置数，则将减少线程池中线程，否则增加线程
	 */
	public static void setMaxWorkThreads(int maxWorkThreads) {

		//ensureInit();

		int count = maxWorkThreads - ThreadPool.maxWorkThreads;

		resizeWorkThreads(count);
		ThreadPool.maxWorkThreads = maxWorkThreads;
	}

	/**
	 * 确保线程池已经初始化
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
	 * 返回朄1�7大线程数
	 * 
	 * @return
	 */
	public static int getMaxWorkThreads() {
		return maxWorkThreads;
	}

	/**
	 * 将工作任务提交给线程汄1�7
	 * 
	 * @param waitCallback
	 *            工作任务的回调实侄1�7
	 */
	public static void queueWorkItem(WaitCallback waitCallback) {
		queueWorkItem(waitCallback, null);
	}

	/**
	 * 将工作任务提交给线程汄1�7
	 * 
	 * @param waitCallback
	 *            定义工作任务的回谄1�7
	 * @param state
	 *            要传递给工作任务的参敄1�7
	 */
	public static void queueWorkItem(WaitCallback waitCallback, Object state) {
		WorkTaskData data = new WorkTaskData(waitCallback, state);
		quequeWorkTaskDatas.insertElementAt(data, quequeWorkTaskDatas.size());
		//唤醒调度线程＄1�7
		if(isWating){
			synchronized (lock) {
				lock.notify();
				isWating = false;
			}
		}
     }

	/**
	 * 分派工作
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
					   
						for (int i = 0; i < size; i++) {  //已经启动的线程设置等级为朄1�7低级
							if (!((WorkThread) pooledWorkThreads.elementAt(i))
									.IsIdle()) {
								((WorkThread) pooledWorkThreads.elementAt(i)).setPriority(WorkThread.MIN_PRIORITY);
							}
						}
						wt = (WorkThread) pooledWorkThreads.elementAt(index);
						wt.setPriority(WorkThread.MAX_PRIORITY);//朄1�7后一个启动的设置为高纄1�7
						wt.setWorkTaskData(data);
						if(index<=DEFAULT_MAX_WORKTHREADS&&index+1<pooledWorkThreads.size()) //判断是否又可以回收的条件
						{
							recycleThread();
						}
						break;
					} else {
						addThreadToPooled(); //线程不够用添加新线程
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
	 * 初始化线程池
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
	 * 向线程池添加格外的线稄1�7
	 */
	private  static void addThreadToPooled()
	{
		WorkThread wt = new WorkThread();
		wt.setPriority(Thread.MIN_PRIORITY);
		wt.start();
		pooledWorkThreads.addElement(wt);
		//System.out.println("添加线程");
	}
	/**
	 * 回收格外的添加的线程
	 */
	private  static void recycleThread()
	{
		int idleSize=0;
		for(int i=0;i<DEFAULT_MAX_WORKTHREADS;i++)  
		{
			if(((WorkThread)pooledWorkThreads.elementAt(i)).IsIdle())
			{
				idleSize++;  //基本线程空闲敄1�7
			}
		}
		if(idleSize>=2)//基本线程数空闲超迄1�7�，回收部分线程
		{
			for(int i=DEFAULT_MAX_WORKTHREADS;i<pooledWorkThreads.size();i++)
			{
				if(((WorkThread)pooledWorkThreads.elementAt(i)).IsIdle())//回收
				{  
				   ((WorkThread)pooledWorkThreads.elementAt(i)).exit();//逄1�7凄1�7
				   pooledWorkThreads.removeElementAt(i);
			      // System.out.println("回收线程");
				}
			}
			System.gc();
		}
	}
	
	/**
	 * 棄1�7测线程池中的线程是否都处于空闲状怄1�7
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
	 * 清空线程汄1�7
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
