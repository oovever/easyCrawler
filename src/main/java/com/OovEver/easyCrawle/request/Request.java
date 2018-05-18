package com.OovEver.easyCrawle.request;

import com.OovEver.easyCrawle.Spider.Spider;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OovEver
 * 2018/5/17 21:00
 */
@Getter
public class Request<T> {
    private Spider spider;
    private String url;
    private String              method  = "GET";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private String contentType = "text/html; charset=UTF-8";
    private String charset = "UTF-8";
    private Parser<T> parser;
    public String charset() {
        return charset;
    }
    public void charset(String charset) {
        this.charset = charset;
    }
    public Request(Spider spider, String url, Parser<T> parser) {
        this.spider = spider;
        this.url = url;
        this.parser = parser;
        this.header("User-Agent", spider.getConfig().getUserAgent());
    }
    public void header(String key, String value) {
        this.headers.put(key, value);
    }
}
