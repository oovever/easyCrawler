package com.OovEver.easyCrawle.Engine;

import com.OovEver.easyCrawle.Config.Config;
import com.OovEver.easyCrawle.Engine.Elves;
import com.OovEver.easyCrawle.Spider.Spider;
import com.OovEver.easyCrawle.download.Downloader;
import com.OovEver.easyCrawle.event.ElvesEvent;
import com.OovEver.easyCrawle.event.EventManager;
import com.OovEver.easyCrawle.pipeline.Pipeline;
import com.OovEver.easyCrawle.request.Parser;
import com.OovEver.easyCrawle.request.Request;
import com.OovEver.easyCrawle.response.Response;
import com.OovEver.easyCrawle.response.Result;
import com.OovEver.easyCrawle.scheduler.Scheduler;
import com.OovEver.easyCrawle.utils.ElvesUtils;
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
//
        executorService = new ThreadPoolExecutor(config.getParallelThreads(), config.getParallelThreads() + 5, 100, TimeUnit.MILLISECONDS, config.getQueueSize() == 0 ? new SynchronousQueue<>() : (config.getQueueSize() < 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(config.getQueueSize())), new NamedThreadFactory("task"));
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
//            为每个Spider返回请求
            List<Request> requests = spider.getStartUrls().stream()
                    .map(spider::makeRequest).collect(Collectors.toList());
            spider.getRequests().addAll(requests);
            scheduler.addRequests(requests);
//        执行爬虫
            EventManager.fireEvent(ElvesEvent.SPIDER_STARTED, conf);
        });
        // 后台生产
        Thread downloadThread=new Thread(()->{
            while (isRunning) {
                if (!scheduler.hasRequest()) {
                    ElvesUtils.sleep(100);
                    continue;
                }
                Request request = scheduler.nextRequest();
//                线程池中添加Downloader线程
                executorService.submit(new Downloader(scheduler, request));
                ElvesUtils.sleep(request.getSpider().getConfig().getDelay());
            }
        });
//        JVM结束 守护线程结束
        downloadThread.setDaemon(true);
        downloadThread.setName("download-thread");
        downloadThread.start();
        // 消费
        this.complete();
    }

    /**
     * 消费线程
     */
    private void complete() {
        while (isRunning) {
            if (!scheduler.hasResponse()) {
                ElvesUtils.sleep(100);
                continue;
            }
            Response response = scheduler.nextResponse();
            Parser   parser   = response.getRequest().getParser();
            if (null != parser) {
                //解析response响应
                Result<?> result = parser.parse(response);
                List<Request> requests = result.getRequests();
                if (!ElvesUtils.isEmpty(requests)) {
                    requests.forEach(scheduler::addRequest);
                }
                if (null != result.getItem()) {
                    List<Pipeline> pipelines = response.getRequest().getSpider().getPipelines();
                    pipelines.forEach(pipeline -> pipeline.process(result.getItem(), response.getRequest()));
                }
            }
        }
    }
    /**
     * 停止爬虫
     */
    public void stop(){
        isRunning = false;
        scheduler.clearRequests();
        log.info("爬虫已经停止.");
    }
}
