package com.OovEver.easyCrawle.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OovEver
 * 2018/5/17 21:00
 */
public class Request<T> {
//    默认字符集
    private String charset = "UTF-8";
    public String charset() {
        return charset;
    }
    public Request charset(String charset) {
        this.charset = charset;
        return this;
    }
}
