package com.OovEver.easyCrawle.response;

import com.OovEver.easyCrawle.request.Request;
import lombok.Getter;

import java.io.InputStream;

/**
 * 相应对象
 * @author OovEver
 * 2018/5/17 21:01
 */
public class Response {
    @Getter
    private Request request;
//    响应体
    private Body body;

    public Response(Request request, InputStream inputStream) {
        this.request = request;
        this.body = new Body(inputStream, request.charset());
    }
    public Body body() {
        return body;
    }
}
