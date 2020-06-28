package com.yxbjll.multithread;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static com.yxbjll.multithread.LockUtils.atomicEnterSize;
import static com.yxbjll.multithread.WaitTask.count;

/**
 * @author yxbjll
 * @desc CustomLockQueue
 * @since 2020/6/7
 */
public class CustomLockQueue extends AbstractQueuedSynchronizer implements Serializable {

	public static Unsafe unsafe = null;

	private static long lockNumOffset;

	static {
		try {
			Field f = null;
			try {
				f = Unsafe.class.getDeclaredField("theUnsafe");
				f.setAccessible(true);
				try {
					unsafe = (Unsafe) f.get(null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}

			lockNumOffset = unsafe.objectFieldOffset
					(LockUtils.class.getDeclaredField("lockNum"));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}


	/**
	 * park and unpark
	 *
	 * @param args
	 */


	public static void main(String[] args) throws IllegalAccessException {

		for (int i = 0; i < 10; i++) {
			new Thread(new WaitTask()).start();
		}

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(count + "=count");
		System.out.println(atomicEnterSize + "=元子类size");
//		//thread park
//		Thread parkThead = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("thread is ready to park");
//				LockSupport.park();
//				System.out.println("thread is unParked");
//			}
//		});
//		parkThead.start();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("is ready to unPark old thread");
//		LockSupport.unpark(parkThead);

	}

}
