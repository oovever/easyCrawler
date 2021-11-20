package com.OovEver.easyCrawle.download;

import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.scheduler.Scheduler;
import com.Oovever.easyHttp.util.HttpUtil;
import com.Oovever.esayTool.io.file.FileWriter;
import com.sun.deploy.net.HttpUtils;
import io.github.biezhi.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.*;

/**
 * 下载器线程
 * @author OovEver
 * 2018/5/18 22:56
 */
@Slf4j
public class Downloader implements Runnable {
    private final Scheduler scheduler;
    private final com.OovEver.easyCrawle.request.Request request;

    public Downloader(Scheduler scheduler, com.OovEver.easyCrawle.request.Request request) {
        this.scheduler = scheduler;
        this.request = request;
    }

    @Override
    public void run() {
        log.debug("[{}] 开始请求", request.getUrl());
//        自己的http工具
        InputStream crawleRelult = null;
        io.github.biezhi.request.Request httpReq = null;
        if ("get".equalsIgnoreCase(request.getMethod())) {
            httpReq = Request.get(request.getUrl());
            crawleRelult = HttpUtil.get(request.getUrl()).execute().getInputStream();
        }
        if ("post".equalsIgnoreCase(request.getMethod())) {
            httpReq = io.github.biezhi.request.Request.post(request.getUrl());
            crawleRelult = HttpUtil.post(request.getUrl()).execute().getInputStream();
        }
//        使用别人的http工具
        InputStream result = httpReq.contentType(request.getContentType()).headers(request.getHeaders())
                .connectTimeout(request.getSpider().getConfig().getTimeout())
                .readTimeout(request.getSpider().getConfig().getTimeout())
                .stream();
        log.debug("[{}] 下载完毕", request.getUrl());
        Response response = new Response(request, result);
        scheduler.addResponse(response);
    }
}
