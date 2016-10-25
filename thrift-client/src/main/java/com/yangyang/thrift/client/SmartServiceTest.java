package com.yangyang.thrift.client;

import com.yangyang.thrift.service.api.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class SmartServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context-thrift-client.xml");
//        SmartService.Iface smartSerivce = (SmartService.Iface) context.getBean("smartService");
        HelloService.Iface helloService = (HelloService.Iface) context.getBean("helloService");
        try {
//            System.out.println(smartSerivce.getUserById(1));
            helloService.sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
