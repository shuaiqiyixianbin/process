package com.yxbjll.java.pattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yxbjll
 * @date 2020/2/19 13:50
 * @desc PatternTest 正则表达式
 */
public class PatternTest {



	/**
	 *  + 号代表前面的字符必须至少出现一次（1次或多次）
	 *  如：runoo+b，可以匹配 runoob、runooob、runoooooob 等
	 */
	@Test
	public void plusTest(){
		String regText = "runoo+b";
		Pattern pattern = Pattern.compile(regText);
		List<String> regList = new ArrayList<>();
		regList.add("runoob");
		regList.add("runooob");
		regList.add("runoooooob");
		regList.add("runbb");
		regList.forEach(reg ->{
			Matcher matcher = pattern.matcher(reg);
			while (matcher.find()) {
				String group = matcher.group();
				//sqlTemp = sqlTemp.replace(group, StringUtils.repeat(" ", group.length()));
				System.out.println(String.format("这是matcher%s",group));
			}
		});

	}





}
