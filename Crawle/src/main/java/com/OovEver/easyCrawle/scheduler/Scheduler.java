package com.OovEver.easyCrawle.scheduler;


import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 爬虫调度器
 * @author OovEver
 * 2018/5/17 20:58
 */
@Slf4j
public class Scheduler {
    private BlockingQueue<Request>  requestBlockingQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<Response> responseBlockingQueue  = new LinkedBlockingQueue<>();

    /**
     * 向调度器中添加request请求
     * @param request request请求
     */
    public void addRequest(Request request) {
        try {
            this.requestBlockingQueue.put(request);
        } catch (InterruptedException e) {
            log.error("向调度器添加 Request 出错", e);
        }
    }

    /**
     * 向response中添加相应
     * @param response response响应
     */
    public void addResponse(Response response) {
        try {
            this.responseBlockingQueue.put(response);
        } catch (InterruptedException e) {
            log.error("向调度器添加 Response 出错", e);
        }
    }

    /**
     * 判断是否还有request请求
     * @return request请求
     */
    public boolean hasRequest() {
        return requestBlockingQueue.size() > 0;
    }

    /**
     * @return 返回下一个request请求
     */
    public Request nextRequest() {
        try {
            return requestBlockingQueue.take();
        } catch (InterruptedException e) {
            log.error("从调度器获取 Request 出错", e);
            return null;
        }
    }

    /**
     * 判断是否有response响应
     * @return response响应
     */
    public boolean hasResponse() {
        return responseBlockingQueue.size() > 0;
    }

    /**
     *
     * @return 返回下一个response响应
     */
    public Response nextResponse() {
        try {
            return responseBlockingQueue.take();
        } catch (InterruptedException e) {
            log.error("从调度器获取 Response 出错", e);
            return null;
        }
    }

    /**
     * 添加请求
     * @param requests 请求集合
     */
    public void addRequests(List<Request> requests) {
        requests.forEach(this::addRequest);
    }

    /**
     * 清除所有请求
     */
    public void clearRequests() {
        requestBlockingQueue.clear();
    }
}
