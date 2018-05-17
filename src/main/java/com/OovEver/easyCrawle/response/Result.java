package com.OovEver.easyCrawle.response;

import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.utils.ElvesUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应结果封装
 * 存储 Item 数据和新添加的 Request 列表
 * @author OovEver
 * 2018/5/17 22:41
 */
@Data
@NoArgsConstructor
public class Result<T> {
    private List<Request> requests = new ArrayList<>();
    private T item;
    public Result(T item) {
        this.item = item;
    }

    /**
     * 添加请求集合
     * @param request 请求消息
     * @return Result封装
     */
    public Result addRequest(Request request) {
        this.requests.add(request);
        if (!ElvesUtils.isEmpty(requests)) {
            this.requests.addAll(requests);
        }
        return this;
    }
}
