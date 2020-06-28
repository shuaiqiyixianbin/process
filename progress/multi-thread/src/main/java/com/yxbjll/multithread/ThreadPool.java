package com.yxbjll.multithread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author yxb
 * @desc 线程池介绍
 */
public class ThreadPool {

	/**
	 * 定时延迟任务--->不推荐
	 */
	private static final ScheduledExecutorService DELAY_PROCESS_EXECUTOR = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("DelayProcessTask", true));

	/**
	 * 自定义普通线程池标准用法
	 */
	private static final ExecutorService COMMON_TASK_EXECUTOR = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2),
			new NamedThreadFactory("DELAY_SCHEDULE_TASK"),new ThreadPoolExecutor.AbortPolicy());
	/**
	 * 自定义定时任务线程池
	 */
	//private static final ExecutorService DELAY_SCHEDULE_TASK = new ScheduledThreadPoolExecutor(1,new NamedThreadFactory("DELAY_SCHEDULE_TASK"),new ThreadPoolExecutor.AbortPolicy());
	private static final ScheduledExecutorService DELAY_SCHEDULE_TASK = new ScheduledThreadPoolExecutor(1,new NamedThreadFactory("DELAY_SCHEDULE_TASK"),new ThreadPoolExecutor.AbortPolicy());

	//延迟队列就一个任务，因此参数可以稍微放宽
	ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());

	@Test
	public void scheduleTask(){
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("hh");
			}
		}, 5, 10, TimeUnit.SECONDS);

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//DELAY_SCHEDULE_TASK.schedule() 同下

		//是否延迟注册
		/*if (shouldDelay()) {
			DELAY_EXPORT_EXECUTOR.schedule(this::doExport, getDelay(), TimeUnit.MILLISECONDS);
		} else {
			doExport();
		}*/
	}


	/**
	 * CompletableFuture 测试
	 */
	@Test
	public void testCompletableFuture(){

	}

}
