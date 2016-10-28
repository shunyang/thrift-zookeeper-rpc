package com.yangyang.thrift.server.service;

import com.yangyang.thrift.api.HelloService;
import org.apache.thrift.TException;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class HelloServiceImpl  implements HelloService.Iface{
    public void sayHello() throws TException {
        System.out.println("hello world! 我陈宫了");
    }
}
