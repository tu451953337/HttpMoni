package com.xj.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xj.lift.SpringBoy;

public class Test {

    /** 
     * main(这里用一句话描述这个方法的作用) 
     * (这里描述这个方法适用条件 – 可选)     
     * @param args  
     * void 
     * @exception  
    */
    public static void main(String[] args) {
        //ApplicationContext context = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        //SpringBoy boy = (SpringBoy) context.getBean("SpringBoy");
        
        ClassPathXmlApplicationContext conn = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SpringBoy boy = (SpringBoy) conn.getBean("SpringBoy");
        boy.say();
        conn.registerShutdownHook();
        
        String.valueOf(null);
    }

}
