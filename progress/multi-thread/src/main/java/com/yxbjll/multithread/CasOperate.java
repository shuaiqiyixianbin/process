package com.yxbjll.multithread;

import java.util.concurrent.atomic.AtomicInteger;

public class CasOperate {

	static AtomicInteger i=new AtomicInteger();

	public static class AddThread implements Runnable {
		public void run() {
			for (int k=0;k<10000;k++){
				i.incrementAndGet();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] ts=new Thread[10];
		for (int k=0;k<10;k++){
			ts[k]=new Thread(new AddThread());
		}
		for (int k=0;k<10;k++){ts[k].start();}
		for (int k=0;k<10;k++){
			ts[k].join();//join是等待当前线程执行完之后，主线程才继续执行下去，其它线程和当前线程还是竞争关系
		}
		System.out.println(i);
	}
}
