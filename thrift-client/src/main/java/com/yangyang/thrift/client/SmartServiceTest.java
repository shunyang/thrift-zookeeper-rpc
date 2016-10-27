package com.yangyang.thrift.client;

import com.yangyang.thrift.service.api.HelloService;
import com.yangyang.thrift.service.api.SmartService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class SmartServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context-thrift-client.xml");
        SmartService.Iface smartSerivce = (SmartService.Iface) context.getBean("smartService");
        HelloService.Iface helloService = (HelloService.Iface) context.getBean("helloService");
        try {
            System.out.println(smartSerivce.getUserById(1));
            helloService.sayHello();
            System.out.println("测试成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
