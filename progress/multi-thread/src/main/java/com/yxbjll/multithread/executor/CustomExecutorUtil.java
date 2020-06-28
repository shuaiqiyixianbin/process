package com.yxbjll.multithread.executor;

import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * @author yxbjll
 * @desc CustomExecutorUtil 自定义线程池工具类,并进行分析
 * @since 2020/4/19
 */
public class CustomExecutorUtil {


	public static final ExecutorService CUSTOM_EXECUTOR = new ThreadPoolExecutor(2,2,0, TimeUnit.SECONDS,new LinkedBlockingDeque<>(20),
			new NamedThreadFactory("CUSTOM_EXECUTOR"),new ThreadPoolExecutor.AbortPolicy());



	/***************************************** 分析 ********************************/

	/**
	 * 1.线程池提交 Runnable 任务，会被封装为 Callable，返回 Future，可以获取返回值，也可以基于此捕获异常
	 *
	 *
	 *
	 *
	 *
	 */
	public void runTask() throws ExecutionException, InterruptedException {
		Future future = CUSTOM_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("run task");
			}
		});
		future.get();
	}

	/**
	 * 第一步：将Runnable 包装成 Callable，同时返回 FutureTask
	 * 注意：Runnable 是 FutureTask 等的父类。FutureTask 是个具体类，而 Future 是个 接口，不能比较
	 */
	/*public FutureTask(Runnable runnable, V result) {
		this.callable = Executors.callable(runnable, result);
		this.state = NEW;       // ensure visibility of callable
	}*/

	//第二步
	//此方法将 Runnable 包装成 Callable,同时根据 传入的 result 直接返回（默认为null）
	static final class RunnableAdapter<T> implements Callable<T> {
		final Runnable task;
		final T result;
		RunnableAdapter(Runnable task, T result) {
			this.task = task;
			this.result = result;
		}
		public T call() {
			task.run();  // Runnable.run() 是什么意思
			return result;
		}
	}







}
