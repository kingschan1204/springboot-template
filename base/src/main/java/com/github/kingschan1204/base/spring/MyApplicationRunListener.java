package com.github.kingschan1204.base.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.time.LocalDateTime;
/**
 * @author kingschan
 */
@Slf4j
public class MyApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;
//    private final EventPublishingRunListener delegate;

    public MyApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
//        this.delegate = new EventPublishingRunListener(application, args);
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log.info("starting 项目正在启动 {}", LocalDateTime.now());
//        delegate.starting(bootstrapContext);

    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("environmentPrepared 项目已经准备好了环境 {}", LocalDateTime.now());
//        delegate.environmentPrepared(bootstrapContext,environment);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("contextPrepared 项目已经准备好了上下文{}", LocalDateTime.now());
//        delegate.contextPrepared(context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("contextLoaded 项目已经加载好上下文 {}", LocalDateTime.now());
//        delegate.contextLoaded(context);
    }



    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("failed 项目启动失败 {}", LocalDateTime.now());
//        delegate.failed(context, exception);
    }

    @Bean
    public ThreadPoolTaskExecutor getSpringApplicationExecutor() {
        log.info("getSpringApplicationExecutor {}", LocalDateTime.now());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        return executor;
    }

}

