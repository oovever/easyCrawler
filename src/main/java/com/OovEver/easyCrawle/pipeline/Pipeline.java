package com.OovEver.easyCrawle.pipeline;

import com.OovEver.easyCrawle.request.Request;

/**
 * 数据处理接口
 * @author OovEver
 * 2018/5/17 23:34
 */
public interface Pipeline<T> {
    void process(T item, Request request);
}
