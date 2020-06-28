package com.yxbjll.java.设计模式.observerPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yxbjll
 * @desc MyObserver 自定义观察者
 * @since 2020/4/19
 */
public class MyObserver implements Observer {

	private String name;

	public MyObserver(String name){
		this.name = name;
	}

	//一旦被观察者有改变，就会调用update 方法
	@Override
	public void update(Observable o, Object arg) {
		System.out.println((o instanceof MyObservable) +""+name+"观察到"+arg.toString());
	}
}
