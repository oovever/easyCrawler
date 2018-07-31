package com.OovEver.easyCrawle.request;

import com.OovEver.easyCrawle.Spider.Spider;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OovEver
 * 2018/5/17 21:00
 */
@Getter
@Setter
public class Request<T> {
    protected Spider spider;
    protected String url;
    protected String              method  = "GET";
    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, String> cookies = new HashMap<>();
    protected String contentType = "text/html; charset=UTF-8";
    protected String charset = "UTF-8";
    protected Parser<T> parser;
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
