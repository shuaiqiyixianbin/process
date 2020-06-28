package com.yxbjll.java.设计模式.observerPattern;

/**
 * @author yxbjll
 * @desc ObserverTest
 * @since 2020/4/19
 */
public class ObserverTest {
	public static void main(String[] args) {
		MyObserver firstObserver = new MyObserver("first_Observer");
		MyObserver lastObserver = new MyObserver("last_Observer");
		MyObservable myObservable = new MyObservable();
		myObservable.addObserver(firstObserver);
		myObservable.addObserver(lastObserver);
		String msg = "msg has changed";
		//设置消息状态改变点
		myObservable.setChanged();
		//通知观察者改变的消息
		myObservable.notifyObservers(msg);
	}
}
