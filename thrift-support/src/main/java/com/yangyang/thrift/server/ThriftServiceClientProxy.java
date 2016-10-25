//package com.yangyang.thrift.server;
//
//import org.apache.thrift.TServiceClient;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//
///**
// * Created by chenshunyang on 2016/10/25.
// */
//public class ThriftServiceClientProxy implements InvocationHandler{
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        TServiceClient client = pool.borrowObject();
//        boolean flag = true;
//        try {
//            return method.invoke(client, args);
//        } catch (Exception e) {
//            flag = false;
//            throw e;
//        } finally {
//            if(flag){
//                pool.returnObject(client);
//            }else{
//                pool.invalidateObject(client);
//            }
//        }
//    }
//}
