package com.yangyang.thrift.server.service;

import com.yangyang.thrift.api.SmartService;
import com.yangyang.thrift.api.struct.User;
import org.apache.thrift.TException;

/**
 * Created by chenshunyang on 2016/10/21.
 */
public class SmartServiceImpl implements SmartService.Iface{
    public User getUserById(int id) throws TException {
        System.out.println("rpc start ,id= " + id);
        return new User(1,"name","pass");
    }
}
