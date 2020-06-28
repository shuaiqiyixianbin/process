package com.yxbjll.sourcecode.spring.customParseSpring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author yxb
 * @desc 自定义解析器
 */
public class CustomParserNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		//注册 BeanDefinitionParser   <xsd:element name="people">
		/**
		 * registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());就是用来
		 把节点名和解析类联系起来，在配置中引用 people 配 置 项 时 ， 就 会 用
		 PeopleBeanDefinitionParser 来解析配置。
		 */
		registerBeanDefinitionParser("people",new PeopleBeanDefinitionParser());
	}
}
