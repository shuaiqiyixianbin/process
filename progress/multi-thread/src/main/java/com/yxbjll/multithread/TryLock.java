package com.yxbjll.multithread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yxbjll
 * @desc 测试可重入锁 ReentrantLock 的 tryLock()方法
 */
public class TryLock implements Runnable {
	private ReentrantLock lock = new ReentrantLock();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public void readWriteLockTest(){
		//读写锁使用
		readWriteLock.readLock().lock();
		readWriteLock.readLock().unlock();

		readWriteLock.writeLock().lock();
		readWriteLock.writeLock().unlock();
	}

	@Override
	public void run() {
		try {
			//在5s内尝试获取锁
			lock.lock();
			if (lock.tryLock(5, TimeUnit.SECONDS)){
				//这个thread应该是当前执行的线程
				System.out.println("我获取到锁了");
				Thread.sleep(6000);
				lock.unlock();
			}else {
				System.out.println("get lock failed");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		TryLock tryLock = new TryLock();
		Thread lock1 = new Thread(tryLock);
		Thread lock2 = new Thread(tryLock);
		lock1.start();lock2.start();
	}

}
