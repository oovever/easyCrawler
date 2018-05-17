package com.OovEver.easyCrawle;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.Spider.Spider;
import com.OovEver.easyCrawle.event.ElvesEvent;
import com.OovEver.easyCrawle.event.EventManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 爬虫主类
 * @author OovEver
 * 2018/5/17 23:52
 */
@Slf4j
@NoArgsConstructor
public class Elves {
    List<Spider> spiders = new ArrayList<>();
    Config config;
    public static Elves Elves(Spider spider) {
        return Elves(spider, Config.config());
    }
    public static Elves Elves(Spider spider, Config config) {
        Elves elves = new Elves();
        elves.spiders.add(spider);
        elves.config = config;
        return elves;
    }

    /**
     * 启动爬虫
     */
    public void start() {
        new ElvesEngine(this).start();
    }

    public void onStart(Consumer<Config> consumer) {
        EventManager.registerEvent(ElvesEvent.GLOBAL_STARTED, consumer);
    }
}
