package com.yxbjll.middleware.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author yxbjll
 * @desc zkConfig
 * @since 2020/4/1
 */
public class ZkConfig implements Watcher {

	private static CountDownLatch connectSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	private static Stat stat = new Stat();

	public static void main(String[] args) throws Exception {
		//zookeeper配置存放路径
		String path = "/username";
		//连接zookeeper并且注册一个默认的监听器(这个默认监听器就是当前类对象本身)
		//将自己作为一个watcher注册监听
		zk = new ZooKeeper("ip:2181",5000,new ZkConfig());
		//等待zk连接成功的通知（相当于调用join()阻塞，只不过这种方式稍微优雅点）
		connectSemaphore.await();
		//获取path目录节点的配置数据，相当于向zookeeper注册了 path 这个节点，然后监控的才会监控到这个 path 的改动
		System.out.println(new String(zk.getData(path,true,stat)));
		//当注册多个的时候，下面的 watchEvent 就能监控声明的这些节点配置的改动
		System.out.println(new String(zk.getData("/test",true,stat)));


		//思考：如果全部注册，然后按照配置路径来生成node，然后在配置改动的时候，将配置set到zookeeper中，此时
		//zookeeper会监控到事件的发生，从而通知容器，然后容器重新再Environment中进行配置的修改，就完成了配置中心
		//的配置

		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent watchedEvent) {
		//zk连接成功通知事件(同步连接成功)
		if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
			if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
				connectSemaphore.countDown();
			}else if (Event.EventType.NodeDataChanged == watchedEvent.getType()){
				try {
					System.out.println("配置已修改，新值为："+new String(zk.getData(watchedEvent.getPath(),true,stat)));
				}catch (Exception e){

				}
			}
		}
	}
}
