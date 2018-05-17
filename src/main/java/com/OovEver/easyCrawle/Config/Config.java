package com.OovEver.easyCrawle.Config;
import lombok.extern.slf4j.Slf4j;
/**
 * 爬虫配置类
 *
 * @author OovEver
 * 2018/5/17 19:33
 */
@Slf4j
public class Config implements Cloneable {
//   爬虫超时时间
    private int timeout=10_000;
//   下载时间间隔
    private int delay = 1000;
//    下载线程数
    private int parallelThreads = Runtime.getRuntime().availableProcessors() * 2;
//    UA配置
    private String userAgent = UserAgent.CHROME_FOR_MAC;
//   容量大小
    private int queueSize;

    /**
     * 返回Config对象
     * @return Config对象
     */
    public static Config config(){
        return new Config();
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getParallelThreads() {
        return parallelThreads;
    }

    public void setParallelThreads(int parallelThreads) {
        this.parallelThreads = parallelThreads;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    public Config clone(){
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            log.info("克隆失败 [{}]", e);
            e.printStackTrace();
        }
        return null;
    }
}
