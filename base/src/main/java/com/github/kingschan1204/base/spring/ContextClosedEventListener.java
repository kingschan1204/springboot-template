package com.github.kingschan1204.base.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听ApplicationContext关闭事件
 * @author kingschan
 */
@Slf4j
@Component
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.warn("ContextClosedEvent 程序关闭事件 : {}",event.getApplicationContext().getId());
    }
}
