package com.yxbjll.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yxb
 * @desc 自己实现 blockingQueue
 */
public class BlockingQueue<E> {

	/**
	 * 数组实现数据存储
	 */
	private Object[] elements;

	/**
	 * 设置头和尾的下标
	 */
	private int head = 0,tail = 0;

	/**
	 * 数组长度
	 */
	private int size;

	private ReentrantLock lock = new ReentrantLock();

	private Condition notEmpty = lock.newCondition();

	private Condition notFull = lock.newCondition();

	public BlockingQueue(int capacity) {
		this.elements = new Object[capacity];
	}

	public void put(E e){
		lock.lock();

		try {
			while (size == elements.length) notFull.await();
			elements[tail] = e;
			if (++tail == elements.length){
				tail = 0;
			}
			size++;
			 notEmpty.signal();

		}catch (InterruptedException ex){
			ex.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public E take() {
		lock.lock();
		E e = null;
		try {
			while (size == 0) {
				notEmpty.await();
			}
			e = (E) elements[head];
			elements[head] = null;
			if (++head == elements.length)
				head = 0;
			size--;
			notFull.signal();

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
		}
		return e;
	}

	public int size() {
		lock.lock();
		try {
			return size;
		} finally {
			lock.unlock();
		}
	}

}
