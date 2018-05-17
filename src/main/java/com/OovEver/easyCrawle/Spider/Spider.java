package com.OovEver.easyCrawle.Spider;

import lombok.Data;
import sun.security.provider.ConfigFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author OovEver
 * 2018/5/17 23:48
 */
@Data
public abstract class Spider {
//    爬虫事件名称
    private String name;
//    爬虫链接
    private List<String> startUrls = new ArrayList<>();
    public Spider(String name) {
        this.name = name;
    }

    /**
     * 添加爬虫链接
     * @param urls 要爬的链接
     */
    public void startUrls(String... urls) {
        this.startUrls.addAll(Arrays.asList(urls));
    }
}
