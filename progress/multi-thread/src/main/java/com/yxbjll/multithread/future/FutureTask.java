package com.yxbjll.multithread.future;

import com.yxbjll.multithread.ExecutorUtil;
import com.yxbjll.multithread.executor.CustomExecutorUtil;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author yxbjll
 * @desc FutureTask 所有关于 future 任务汇总
 * @since 2020/4/19
 */
public class FutureTask {

	@Test
	public void futureCodeTest(){
		Future future = CustomExecutorUtil.CUSTOM_EXECUTOR.submit(new Callable<Integer>() {
			int j = 0;
			@Override
			public Integer call() throws Exception {
				for (int i=0;i<1000;i++){
					j+=i;
				}
				System.out.println(j);
				Thread.sleep(2000);
				return j;
			}
		});
		ExecutorUtil.gracefulShutdown(CustomExecutorUtil.CUSTOM_EXECUTOR,3000);
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			future.cancel(true);
			e.printStackTrace();
		} catch (ExecutionException e) {
			future.cancel(true);
			e.printStackTrace();
		}
	}


	@Test
	public void completFutureTest(){
		CompletableFuture completableFuture = new CompletableFuture();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("completableFuture 可以监控这个任务的执行");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				completableFuture.complete("任务返回结果");
			}
		}).start();
		try {
			System.out.println(completableFuture.get());
			System.out.println("hehe");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
