package com.OovEver.examples;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.Engine.Elves;
import com.OovEver.easyCrawle.Spider.Spider;
import com.OovEver.easyCrawle.pipeline.Pipeline;
import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.response.Result;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 网易新闻爬虫测试
 *
 * @author OovEver
 * 2018/5/24 20:04
 */
public class News163Example{
    static class News163Spider extends Spider {
        public News163Spider(String name) {
            super(name);
            this.startUrls(
                    "http://news.163.com/special/0001386F/rank_news.html",//新闻
                    "http://news.163.com/special/0001386F/rank_ent.html", // 娱乐
                    "http://news.163.com/special/0001386F/rank_sports.html", // 体育
                    "http://news.163.com/special/0001386F/rank_tech.html", // 科技
                    "http://news.163.com/special/0001386F/game_rank.html", //游戏
                    "http://news.163.com/special/0001386F/rank_book.html"); // 读书
        }

        public void onStart(Config config) {
            //每个request一个pipeline
            this.addPipeline((Pipeline<List<String>>) (item, request) ->{
                    item.forEach(System.out::println);
            });
            this.getRequests().forEach(request -> {
                request.charset("text/html; charset=utf-8");
                request.charset("utf-8");
            });
        }
        @Override
        public <T> Result<T> parse(Response response) {
            List<String> titles = response.body().css("div.areabg1 .area-half.left div.tabContents td a").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());

            return new Result(titles);
        }
    }
    public static void main(String[] args) {
        Elves.Elves(new News163Spider("网易新闻")).start();
    }
}
