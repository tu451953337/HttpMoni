package com.xj.lift;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 监控bean的创建(在bean初始化前或后做一些操作)
 */
public class Processor implements BeanPostProcessor{

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("8.bean处理器：bean创建后也可以称为后预处理？" + beanName);
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("5.bean处理器：bean创建之前" + beanName);
        return bean;
    }

}
