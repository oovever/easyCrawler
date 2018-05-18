package com.OovEver.easyCrawle;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.Spider.Spider;
import com.OovEver.easyCrawle.event.ElvesEvent;
import com.OovEver.easyCrawle.event.EventManager;
import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.scheduler.Scheduler;
import com.OovEver.easyCrawle.utils.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 爬虫执行引擎
 * @author OovEver
 * 2018/5/18 0:10
 */
@Slf4j
public class ElvesEngine {
    private List<Spider>    spiders;
    private Config          config;
    private boolean         isRunning;
    private Scheduler       scheduler;
    private ExecutorService executorService;

    /**
     * 初始化爬虫引擎
     * @param elves 爬虫主类对象
     */
    ElvesEngine(Elves elves) {
        this.scheduler = new Scheduler();
        this.spiders = elves.spiders;
        this.config = elves.config;
//        新建线程池
        this.executorService = new ThreadPoolExecutor(config.getParallelThreads(), config.getParallelThreads() + 5, 100, TimeUnit.MILLISECONDS, config.getQueueSize() == 0 ? new SynchronousQueue<>() : (config.getQueueSize() < 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(config.getQueueSize())), new NamedThreadFactory("task"));
    }
    /**
     * 启动爬虫
     */
    public void start() {
        if (isRunning) {
            throw new RuntimeException("Elves 已经启动");
        }
        isRunning = true;
        // 全局启动事件
        EventManager.fireEvent(ElvesEvent.GLOBAL_STARTED, config);
        spiders.forEach(spider -> {
            Config conf = config.clone();
            log.info("Spider [{}] 启动...", spider.getName());
            log.info("Spider [{}] 配置 [{}]", spider.getName(), conf);
            spider.setConfig(conf);
//            返回请求
            List<Request> requests = spider.getStartUrls().stream()
                    .map(spider::makeRequest).collect(Collectors.toList());
            spider.getRequests().addAll(requests);
            scheduler.addRequests(requests);
//        执行事件
            EventManager.fireEvent(ElvesEvent.SPIDER_STARTED, conf);
        });
    }
}
