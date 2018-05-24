package com.OovEver.easyCrawle;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.Spider.Spider;
import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.response.Result;

import static org.junit.Assert.*;

/**
 * @author OovEver
 * 2018/5/24 19:56
 */
public class ElvesTest {

    public static void main(String[] args) {
        Elves.Elves(new Spider("测试爬虫") {
            @Override
            public  Result<String> parse(Response response) {
                return new Result<>(response.body().toString());
            }

        }, Config.config()).onStart(config -> System.out.println("测试开始")).start();
    }
}