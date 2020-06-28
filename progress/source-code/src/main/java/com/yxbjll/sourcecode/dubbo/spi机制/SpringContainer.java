
package com.yxbjll.sourcecode.dubbo.spi机制;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;


/**
 * SpringContainer. (SPI, Singleton, ThreadSafe)
 *
 * The container class implementation for Spring
 */
public class SpringContainer implements Container {

    public static final String SPRING_CONFIG = "dubbo.spring.config";
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";
    //private static final Logger logger = LoggerFactory.getLogger(SpringContainer.class);
    static ClassPathXmlApplicationContext context;

    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

    /**
     * 根据指定的配置启动spring容器，如不指定配置，就是spring容器默认的初始状态
     */
    @Override
    public void start() {
        //String configPath = ConfigUtils.getProperty(SPRING_CONFIG);
        String configPath = "dubbo.spring.config";
        if (StringUtils.isEmpty(configPath)) {
            configPath = DEFAULT_SPRING_CONFIG;
        }
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"), false);
        context.refresh();
        context.start();
    }

    @Override
    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
           // logger.error(e.getMessage(), e);
        }
    }

}
