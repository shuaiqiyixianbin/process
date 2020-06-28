package com.yxbjll.sourcecode;

import com.yxbjll.sourcecode.dubbo.spi机制.Container;
import com.yxbjll.sourcecode.dubbo.spi机制.SpringContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SourceCodeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testContainer() {
		//Container container = ExtensionLoader.getExtensionLoader(Container.class).getExtension("custom");
		Container container = new SpringContainer();
		container.start();
		//Assertions.assertEquals(SpringContainer.class, container.context.getBean("container").getClass());
		container.stop();
	}

}
