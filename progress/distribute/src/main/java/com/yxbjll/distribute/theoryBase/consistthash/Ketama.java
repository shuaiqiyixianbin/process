package com.yxbjll.distribute.theoryBase.consistthash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The Ketama implementation
 * @param <T> the generic type
 *
 *           注：一致性hash实现
 */
public class Ketama<T> {

	/**
	 * 一个真实节点对应的虚拟节点个数
	 */
    private final Integer virtual;

	/**
	 * 有序map,根据 value的值会进行默认排序：如果是 integer 按照从小到大排序，如果是 String 根据首字母进行排序
	 */
	private final SortedMap<Long, T> circle = new TreeMap<>();

	/**
	 * 读写锁：使用锁分离，提高性能
	 */
	private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock rLock = rwLock.readLock();

    private final Lock wLock = rwLock.writeLock();

    private MessageDigest md5Algorithm;

	/**
	 * 定义虚拟节点个数，相当于每个真实节点新增了400个
	 */
	public Ketama() {
        this(400, null);
    }

    public Ketama(Integer virtual) {
        this(virtual, null);
    }

    public Ketama(List<T> nodes) {
        this(400, nodes);
    }

    public Ketama(int virtual, List<T> nodes) {
        this.virtual = virtual;
        try {
            md5Algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 isn't available.");
        }
        add(nodes);
    }

    public void add(T node) {
        wLock.lock();
        try {
            addNode(node);
        } finally {
            wLock.unlock();
        }
    }

    public void add(List<T> nodes) {
        if (nodes == null || nodes.isEmpty()){
            return;
        }
        wLock.lock();
        try {
            for (T node : nodes) {
                addNode(node);
            }
        } finally {
            wLock.unlock();
        }
    }

	/**
	 * 此处通过key相加 i ，但是 value的值一直都是传入进来的实际 node，因此直接通过get即可获取到负载到的 server
	 * @param node
	 */
	private void addNode(T node) {
        for (int i = 0; i < virtual / 4; i++) {
            byte[] digest = md5(node.toString() + i);
            for (int h = 0; h < 4; h++) {
	            System.out.println("hash值："+hash(digest,h));
	            circle.put(hash(digest, h), node);
            }
        }
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hash(key.toString());
	    System.out.println("client key:"+hash);
	    rLock.lock();
        try {
            if (!circle.containsKey(hash)) {
            	//获取比当前节点hash值大的map集合
                SortedMap<Long, T> tailMap = circle.tailMap(hash);
	            System.out.println("tailMap="+tailMap.isEmpty());
	            System.out.println("circle的长度="+circle.size());
	            System.out.println("tailMap的长度="+tailMap.size());
	            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
            }
	        System.out.println("hash置为="+hash);
	        return circle.get(hash);
        } finally {
            rLock.unlock();
        }
    }

    public void remove(List<T> nodes) {
        wLock.lock();
        try {
            for (T node : nodes) {
                removeNode(node);
            }
        } finally {
            wLock.unlock();
        }
    }

    public void remove(T node) {
        wLock.lock();
        try {
            removeNode(node);
        } finally {
            wLock.unlock();
        }
    }

    private void removeNode(T node) {
        for (int i = 0; i < virtual / 4; i++) {
            byte[] digest = md5(node.toString() + i);
            for (int h = 0; h < 4; h++) {
                circle.remove(hash(digest, h));
            }
        }
    }

    private long hash(final String k) {
        byte[] digest = md5(k);
        return hash(digest, 0) & 0xffffffffL;
    }

    private long hash(byte[] digest, int h) {
        return ((long) (digest[3 + h * 4] & 0xFF) << 24) | ((long) (digest[2 + h * 4] & 0xFF) << 16) | ((long) (digest[1 + h * 4] & 0xFF) << 8) | (digest[h * 4] & 0xFF);
    }

    private byte[] md5(String text) {
        md5Algorithm.update(text.getBytes());
        return md5Algorithm.digest();
    }

    public Map<Long, T> getCircle(){
        rLock.lock();
        try {
            return circle;
        } finally {
            rLock.unlock();
        }
    }


	public static void main(String[] args) {
		Ketama<String> ketama = new Ketama();
		List<String> nodeList = new ArrayList<>();
		nodeList.add("172.17.29.128:8028");
		nodeList.add("172.17.29.129:8029");
		nodeList.add("172.17.29.130:8030");
		nodeList.add("172.17.29.131:8031");
		ketama.add(nodeList);

		String currentClient = "192.168.145.199:8080";
		String currentServer = ketama.get(currentClient);
		System.out.println("请求转发到："+currentServer);
	}
}