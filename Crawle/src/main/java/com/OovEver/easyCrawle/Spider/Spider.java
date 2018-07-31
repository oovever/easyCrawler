package com.OovEver.easyCrawle.Spider;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.event.ElvesEvent;
import com.OovEver.easyCrawle.event.EventManager;
import com.OovEver.easyCrawle.pipeline.Pipeline;
import com.OovEver.easyCrawle.request.Parser;
import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.response.Result;
import lombok.Data;
import sun.security.provider.ConfigFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理Response的数据
 * @author OovEver
 * 2018/5/17 23:48
 */
@Data
public abstract class Spider {
//    爬虫事件名称
    protected String name;
//    配置信息
protected Config config;
//    Pipeline处理
protected List<Pipeline> pipelines = new ArrayList<>();
//    请求处理
protected List<Request>  requests  = new ArrayList<>();
//    爬虫链接
protected List<String>     startUrls = new ArrayList<>();
    public Spider(String name) {
        this.name = name;
//        爬虫事件注册
        EventManager.registerEvent(ElvesEvent.SPIDER_STARTED,this::onStart);
    }

    /**
     * 添加爬虫链接
     * @param urls 要爬的链接
     */
    public void startUrls(String... urls) {
        this.startUrls.addAll(Arrays.asList(urls));
    }
    /**
     * 爬虫启动前执行
     * @param config 配置信息
     */
    public void onStart(Config config) {
    }

    /**
     * 添加pipeline功能
     * @param pipeline
     * @param <T> Pipeline类型
     */
    public <T> void addPipeline(Pipeline<T> pipeline) {
        this.pipelines.add(pipeline);
    }
    /**
     * 构建一个Request
     * @param url 请求的url
     * @return 返回请求
     */
    public <T> Request<T> makeRequest(String url) {
        return makeRequest(url, this::parse);
    }
    /**
     * 构建一个Request
     * @param url 请求的url
     * @param parser 解析器
     * @return 返回请求
     */
    public <T> Request<T> makeRequest(String url, Parser<T> parser) {
        return new Request(this, url, parser);
    }
    /**
     * 解析 DOM
     */
    public abstract <T> Result<T> parse(Response response);
}
