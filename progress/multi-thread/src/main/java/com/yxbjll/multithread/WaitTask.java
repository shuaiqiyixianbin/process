package com.yxbjll.multithread;

import static com.yxbjll.multithread.LockUtils.atomicEnterSize;

/**
 * @author yxbjll
 * @desc WaitTask
 * @since 2020/6/8
 */
public class WaitTask implements Runnable {

	public static int count = 0;

	public WaitTask(){}
	@Override
	public void run() {
		try {
			new LockUtils().lock();
			System.out.println("线程"+Thread.currentThread().getId()+":"+Thread.currentThread().getName()+"-----我进来了-----");
			logic();
			System.out.println("-----task 完成，准备出去,另外一个线程准备被唤醒-----");
		}finally {
			boolean success = new LockUtils().unlock();
			String result = success ? "成功" : "失败";
			System.out.println("线程释放锁"+result);
		}

	}



	private void logic(){
		atomicEnterSize.incrementAndGet();
		count++;
	}
}
