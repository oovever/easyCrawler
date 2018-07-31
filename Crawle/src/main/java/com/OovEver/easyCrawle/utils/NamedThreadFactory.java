package com.OovEver.easyCrawle.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

/**
 * 线程名称工厂 用来创建线程
 * @author OovEver
 * 2018/5/18 0:22
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String prefix;
    private final LongAdder threadNumber = new LongAdder();
    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 新建线程
     * @param r
     * @return 返回创建的线程
     */
    @Override
    public Thread newThread(Runnable r) {
        threadNumber.add(1);
        return new Thread(r,prefix + "@thread-" + threadNumber.intValue());
    }
}
