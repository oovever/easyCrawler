package com.OovEver.easyCrawle.event;

import com.OovEver.easyCrawle.Config.Config;

import java.util.*;
import java.util.function.Consumer;

/**
 * 基于观察者模式
 * 事件管理器
 * @author OovEver
 * 2018/5/17 23:36
 */
public class EventManager {
//    事件模式
    private static final Map<ElvesEvent, List<Consumer<Config>>> elvesEventConsumerMap = new HashMap<>();

    /**
     * 注册事件
     * @param elvesEvent 要注册的事件
     * @param consumer 即接口表示一个接受单个输入参数并且没有返回值的操作。不像其它函数式接口，Consumer接口期望执行带有副作用的操作(Consumer的操作可能会更改输入参数的内部状态)。
     */
    public static void registerEvent(ElvesEvent elvesEvent, Consumer<Config> consumer) {
        List<Consumer<Config>> consumers = elvesEventConsumerMap.get(elvesEvent);
        if (null == consumers) {
            consumers = new ArrayList<>();
        }
        consumers.add(consumer);
        elvesEventConsumerMap.put(elvesEvent, consumers);
    }

    /**
     * 执行事件
     * @param elvesEvent 要执行的事件
     * @param config 配置名称
     */
    public static void fireEvent(ElvesEvent elvesEvent, Config config) {
        Optional.ofNullable(elvesEventConsumerMap.get(elvesEvent)).ifPresent(consumers -> consumers.forEach(consumer -> consumer.accept(config)));
    }
}
