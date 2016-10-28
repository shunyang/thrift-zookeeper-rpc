package com.yangyang.thrift.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class BootStrap {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-context-thrift-server.xml");
        ac.start();
        System.out.println("服务已经启动。。。。");
    }
}
