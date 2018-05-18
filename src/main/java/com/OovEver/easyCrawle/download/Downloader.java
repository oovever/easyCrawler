package com.OovEver.easyCrawle.download;

import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 下载器线程
 * @author OovEver
 * 2018/5/18 22:56
 */
@Slf4j
public class Downloader implements Runnable {
    private final Scheduler scheduler;
    private final Request request;

    public Downloader(Scheduler scheduler, Request request) {
        this.scheduler = scheduler;
        this.request = request;
    }

    @Override
    public void run() {
        log.debug("[{}] 开始请求", request.getUrl());
        io.github.biezhi.request.Request httpReq = null;
        if ("get".equalsIgnoreCase(request.getMethod())) {
            httpReq = io.github.biezhi.request.Request.get(request.getUrl());
        }
        if ("post".equalsIgnoreCase(request.getMethod())) {
            httpReq = io.github.biezhi.request.Request.post(request.getUrl());
        }

        InputStream result = httpReq.contentType(request.getContentType()).headers(request.getHeaders())
                .connectTimeout(request.getSpider().getConfig().getTimeout())
                .readTimeout(request.getSpider().getConfig().getTimeout())
                .stream();
        log.debug("[{}] 下载完毕", request.getUrl());
        Response response = new Response(request, result);
        scheduler.addResponse(response);
    }
}
