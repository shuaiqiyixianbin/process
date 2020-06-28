package com.yxbjll.java.设计模式.observerPattern;

import java.util.Observable;

/**
 * @author yxbjll
 * @desc MyObservable 被观察者
 * @since 2020/4/19
 */
public class MyObservable extends Observable {

	//被观察者调用了这个方法，观察者就会发现(状态改变了)
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
	}


	//向观察者发送改变的信息
	@Override
	public void notifyObservers(Object arg) {
		super.notifyObservers(arg);
	}
}
