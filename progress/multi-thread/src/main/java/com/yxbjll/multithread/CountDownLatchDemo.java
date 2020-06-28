package com.yxbjll.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yxb
 * @desc CountDownLatch 使用
 * @desc 当前版本加上对源码的分析
 * @description: countDownLatch 的
 */
public class CountDownLatchDemo implements Runnable {
	/**
	 * CountDownLatch 内部构造了一个 Sync对象
	 * this.sync = new Sync(count);构造了一个变量： private volatile int state;
	 * 此参数即为传入的 数量，countDownLatch 数量也是通过此变量的加减实现逻辑
	 */
	private static final CountDownLatch end = new CountDownLatch(10);

	private static final CountDownLatchDemo demo = new CountDownLatchDemo();

	private static List<Future> taskList = new ArrayList<>();

	/**
	 * CountDownLatch.countDown() 解析
	 *    public void countDown() {
	 *      sync.releaseShared(1);
	 *    }
	 *
	 *      for (;;) {
		 int c = getState();
		 if (c == 0)
		 return false;
		 int nextc = c-1;
		 if (compareAndSetState(c, nextc))
		 return nextc == 0;
		 }
	 使用for（;;） 死循环的原因：
	 当调用 countDown() 计数器减1的时候，一定是要么 state 为0，直接返回，要么就是减1成功，没有第三种可能
	 因此此处使用 死循环 执行操作

	 *    每次一个线程完成任务，默认减一
	 *
	 */

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("检查完成");
			//进行减1操作
			end.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("线程休眠异常，"+Thread.currentThread().getName());
		}
	}


	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i=0;i<9;i++){
			taskList.add(service.submit(demo));
		}
		taskList.stream().forEach(task -> {
			try {
				task.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		taskList.stream().forEach(task -> task.notifyAll());
		try {
			end.await();
			System.out.println("点火发射");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		service.shutdown();
	}




}
