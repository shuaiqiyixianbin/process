package com.yxbjll.java.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxbjll
 * @date 2020/2/13 12:18
 * @desc Logic 业务逻辑类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Logic implements LogicInterface  {
	private String traceId;
	private String name;
	private String price;

	public void doProcess() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("业务逻辑处理");
	}
}
