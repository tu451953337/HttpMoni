package com.xj.lift;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpringBoy implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean{

    private String name;
    
    public SpringBoy(){
        System.out.println("1.SpringBoy构造方法……");
    }
    
    public void setName(String name) {
        System.out.println("2.设置属性值");
        this.name = name;
    }

    private void init(){
        System.out.println("7.调用初始化方法");
    }

    public void destory(){
        System.out.println("10.自定义销毁");
    }

    public void destroy() throws Exception {
        System.out.println("9.销毁方法");
        
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("6.afterProperties执行");
        
    }

    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out.println("4.创建bean的Factory");
        
    }

    public void setBeanName(String arg0) {
        System.out.println("3.设置bean的名字" + arg0);
        
    }

    public void say(){
        System.out.println("逗B！");
    }
}
