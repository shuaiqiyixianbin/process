package com.yxbjll.sourcecode.dubbo;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yxbjll
 * @desc dubbo源码记录
 * @since 2020/4/6
 */
public class DubboCode {

	/**
	 * 1.1 shutdownHook使用
	 */
	@Test
	public void testShutDownHook(){
		//正常使用
		Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Do something in Shutdown Hook")));
		//dubbo中使用
		/*private Thread shutdownHook;
		public void registerShutdownHook() {
			if (this.shutdownHook == null) {
				// No shutdown hook registered yet.
				this.shutdownHook = new Thread() {
					@Override
					public void run() {
						synchronized (startupShutdownMonitor) {
							doClose();
						}
					}
				};
				Runtime.getRuntime().addShutdownHook(this.shutdownHook);
			}
		}*/
	}


	/**
	 * 定时延迟任务--->不推荐
	 */
	private static final ScheduledExecutorService DELAY_PROCESS_EXECUTOR = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("DelayProcessTask", true));

	/**
	 * 自定义普通线程池标准用法
	 */
	private static final ExecutorService COMMON_TASK_EXECUTOR = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS,new LinkedBlockingDeque<>(2),
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
	 * for循环优化
	 * 在返回值中尽量定义与初始化集合，避免使用 List list = null;这种，因为使用这种方式返回，还需要在返回参数
	 * 后面进行非空判断，否则会报错，如果初始化一个 List list = new ArrayList();此时即使值为空，亦无所谓
	 */
	@Test
	public void forEachTest(){
		List<String> list = new ArrayList<>();
		for (String str : list){
			System.out.println(str);
		}

	}





}
