package com.yangyang.thrift.service.impl;

import com.yangyang.thrift.service.api.SmartService;
import com.yangyang.thrift.service.struct.User;
import org.apache.thrift.TException;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class SmartServiceImpl implements SmartService.Iface{
    public User getUserById(int id) throws TException {
        System.out.println("server start ,id= " + id);
        return new User(1,"name","pass");
    }
}
