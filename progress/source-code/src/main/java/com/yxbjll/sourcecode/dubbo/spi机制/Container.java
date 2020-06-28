
package com.yxbjll.sourcecode.dubbo.spi机制;


/**
 * Container. (SPI, Singleton, ThreadSafe)
 */
//@SPI
public interface Container {

    /**
     * start method to load the container.
     */
    void start();

    /**
     * stop method to unload the container.
     */
    void stop();

}