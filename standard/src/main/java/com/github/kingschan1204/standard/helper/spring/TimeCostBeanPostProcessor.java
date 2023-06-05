package com.github.kingschan1204.standard.helper.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 监控 Bean 注入耗时
 * @author kingschan
 * 2023-6-5
 */
@Slf4j
@Component
public class TimeCostBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Long> costMap = new LinkedHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        costMap.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (costMap.containsKey(beanName)) {
            Long start = costMap.get(beanName);
            long cost = System.currentTimeMillis() - start;
            if (cost > 0) {
                costMap.put(beanName, cost);
                log.info("耗时分析 bean: {} time: {} ms",beanName,cost);
            }
        }
        return bean;
    }
}
