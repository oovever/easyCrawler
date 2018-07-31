package com.OovEver.easyCrawle.request;

import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.response.Result;

/**
 * 解析器接口
 *
 *
 * @author OovEver
 * 2018/5/17 22:40
 */
public interface Parser<T> {
    Result<T> parse(Response response);
}
