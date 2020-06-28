package com.yxbjll.multithread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yxb
 * @desc 测试 notify 唤醒线程之后，代码是重新执行 run() 还是在 wait 方法后执行
 */
public class WaitAndNotifyTest implements Runnable {
	private static Object lock = new Object();
	private static boolean needWait = true;


	public static void processLogic(){
		synchronized (lock){
			processBefore();
			while (!needWait) {
				try {
					System.out.println("执行wait");
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			processAfter();
		}
	}


	private static void processBefore(){
		System.out.println("wait 之前执行");
	}

	private static void processAfter(){
		System.out.println("wait 之后执行");
	}


	public static void main(String[] args) {
		Thread waitThread = new Thread(new WaitAndNotifyTest());
		try {
			waitThread.start();
			Thread.sleep(100);
			synchronized (lock){
				System.out.println("准备唤醒");
				lock.notify();
				needWait = false;
			}
			waitThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		processLogic();
	}


/*	public void SegmentTest(){
		Ysegment ysegment = new Ysegment(1f);
		ysegment.lock();
	}

	static class Ysegment<K,V> extends ReentrantLock{
		final float capacity;
		Ysegment(float lf) { this.capacity = lf; }
	}*/


}
