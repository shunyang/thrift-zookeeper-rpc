package com.yangyang.thrift.server;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chenshunyang on 2016/10/25.
 */
public class ThriftServiceClientProxy implements InvocationHandler{

    /**
     * thrfit 客户端( 客户端中间件,非具体客户端,具体客户端为该对象的 .getClient() 获取 )
     */
    private TServiceClient client;

    private GenericObjectPool<TServiceClient> pool;

    public ThriftServiceClientProxy(GenericObjectPool<TServiceClient> pool){
        this.pool = pool;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TServiceClient client = pool.borrowObject();//从连接池拿到一个TServiceClient对象
        boolean flag = true;
        try {
            return method.invoke(client, args);
        } catch (Exception e) {
            flag = false;
            throw e;
        } finally {
//            if(flag){
//                pool.returnObject(client);
//            }else{
//                pool.invalidateObject(client);
//            }
        }
    }
}
