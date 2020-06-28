package com.yxbjll.multithread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author yxbjll
 * @desc LockUtils
 * @since 2020/6/8
 */
public class LockUtils {


	public static AtomicInteger atomicEnterSize = new AtomicInteger(0);
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

	public List<Thread> waitList = new ArrayList();

	//lock num
	private volatile int lockNum;

	private Thread lockThread;

	private int getLockNum(){
		return new LockUtils().lockNum;
	}

	private int increaseLockNum(){
		return new LockUtils().lockNum++;
	}

	private int decreaseLockNum(){
		return new LockUtils().lockNum--;
	}

	/**
	 * 此处线程 unpark() 之后,不会重新进入获取锁的操作，因此此处需要加个for(;;),直到成功为止
	 * @return
	 */
	//重入 +1,当前只允许每次默认+1，不允许一次加多次锁
	public boolean lock(){
		Thread currentThread = Thread.currentThread();
		if (!tryLock()){
			for(;;){
				//if other thread is not get the lock,the add it to clh queue
				if (!waitList.contains(currentThread)){
					waitList.add(currentThread);
				}
				System.out.println("此时 lockNum="+getLockNum()+"并且lockThread="+lockThread);
				System.out.println("线程"+Thread.currentThread().getId()+":"+Thread.currentThread().getName()+"被挂起");
				LockSupport.park(currentThread);
				lock();
			}
			//todo add return statement
		}
		return true;
	}

	private boolean tryLock(){
		Thread currentThread = Thread.currentThread();
		if (getLockNum() == 0){
			System.out.println("线程"+currentThread.getId()+":"+currentThread.getName()+"进入，此时lockNum=0");
			//因为无人持有，谁都可以抢，此时会有并发问题，需要用 cas 进行比较
			System.out.println("内存值为："+unsafe.getInt(LockUtils.class, lockNumOffset));
			if (compareAndSetLockNum(0,1)){
				System.out.println("原子操作后，lockNum数量为："+getLockNum());
				lockThread = currentThread;
				System.out.println("线程"+lockThread.getId()+":"+lockThread.getName()+"是当前被锁定的线程");
				return true;
			}
		} else if (lockThread == currentThread){
			//todo judge lockNum is valid
			increaseLockNum();
			return true;
		}
		return false;
	}


	protected final boolean compareAndSetLockNum(int expect, int update) {
		// See below for intrinsics setup to support this
		return unsafe.compareAndSwapInt(LockUtils.class, lockNumOffset, expect, update);
	}


	/**
	 * 释放锁
	 */
	public boolean unlock(){
		try {
			Thread currentThread = Thread.currentThread();
			//if is not the same thread to enter ,then throw exception
			if (currentThread != lockThread){
				System.out.println(lockThread==null);
				throw new RuntimeException("other thread is enter，lock fail");
			}else {
				decreaseLockNum();
				//完全释放，唤醒其他线程
				if (getLockNum() ==0){
					if (waitList != null && waitList.size() >0){
						LockSupport.unpark(waitList.get(0));
						System.out.println("线程"+waitList.get(0).getId()+":"+waitList.get(0).getName()+"被唤醒");
						waitList.remove(0);
							System.out.println("唤醒当前线程后，重置系统内存后，内存值为："+unsafe.getInt(LockUtils.class, lockNumOffset));
							/*unsafe.compareAndSwapInt(LockUtils.class,lockNumOffset,1,0);
							System.out.println("唤醒当前线程后，再次重置系统内存后，内存值为："+unsafe.getInt(LockUtils.class, lockNumOffset));*/
						lockThread = null;
					}
					return true;
				}else {
					return false;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
