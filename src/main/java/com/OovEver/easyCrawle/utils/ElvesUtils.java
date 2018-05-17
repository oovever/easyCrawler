package com.OovEver.easyCrawle.utils;

import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Result;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 爬虫工具类
 * @author OovEver
 * 2018/5/17 22:47
 */
@Slf4j
public class ElvesUtils {
    /**
     * 线程调度睡眠时间
     * @param time 要睡眠的时间
     */
    public static void sleep(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            log.error("线程睡眠调度出错[{}]", e);
        }
    }

    /**
     * 判断集合是否为空
     * @param collection 要判断的集合
     * @param <E> 集合类型
     * @return 集合是否为空
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return null == collection || collection.size() == 0;
    }

}
