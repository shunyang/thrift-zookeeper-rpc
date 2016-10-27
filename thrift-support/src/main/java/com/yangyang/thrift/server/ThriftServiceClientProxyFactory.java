package com.yangyang.thrift.server;

import com.yangyang.thrift.server.zookeeper.ThriftServerAddressProvider;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;

/**
 * 客户端代理
 * Created by chenshunyang on 2016/10/21.
 */
public class ThriftServiceClientProxyFactory implements FactoryBean, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Integer maxActive = 32;// 最大活跃连接数

    private Integer idleTime = 180000;//单位ms,default 3 min,链接空闲时间,-1,关闭空闲检测

    private ThriftServerAddressProvider serverAddressProvider;//服务提供地址

    private Object proxyClient;
    private Class<?> objectClass;

    private GenericObjectPool<TServiceClient> pool;

    private ThriftClientPoolFactory.PoolOperationCallBack callback = new ThriftClientPoolFactory.PoolOperationCallBack() {
        @Override
        public void destroy(TServiceClient client) {
            logger.info("destroy");
        }

        @Override
        public void make(TServiceClient client) {
            logger.info("create");
        }
    };

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public void setServerAddressProvider(ThriftServerAddressProvider serverAddressProvider) {
        this.serverAddressProvider = serverAddressProvider;
    }

    @Override
    public Object getObject() throws Exception {
        return proxyClient;
    }

    @Override
    public Class<?> getObjectType() {
        return objectClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 加载Iface接口
        objectClass = classLoader.loadClass(serverAddressProvider.getService() + "$Iface");
        // 根据配置的service 加载Client.Factory类
        Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(serverAddressProvider.getService() + "$Client$Factory");
        //获取一个clientFactory对象
        TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();

        ThriftClientPoolFactory clientPoolFactory = new ThriftClientPoolFactory(serverAddressProvider, clientFactory, callback);

        //获取一个通用的连接池
        pool = new GenericObjectPool<TServiceClient>(clientPoolFactory, makePoolConfig());

        ThriftServiceClientProxy handler = new ThriftServiceClientProxy(pool);
        proxyClient = Proxy.newProxyInstance(classLoader , new  Class[]{objectClass},handler);
        logger.info("获取的代理客户端对象为："+proxyClient);
    }

    private GenericObjectPool.Config makePoolConfig() {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = maxActive;//参数maxActive指明能从池中借出的对象的最大数目。如果这个值不是正数，表示没有限制
        poolConfig.maxIdle = 1;
        poolConfig.minIdle = 0;
        poolConfig.minEvictableIdleTimeMillis = idleTime;
        poolConfig.timeBetweenEvictionRunsMillis = idleTime * 2L;//设定间隔每过多少毫秒进行一次后台对象清理的行动。如果这个值不是正数，则实际上不会进行后台对象清理。
        poolConfig.testOnBorrow=true;//设定在借出对象时是否进行有效性检查
        poolConfig.testOnReturn=false;
        poolConfig.testWhileIdle=false;
        return poolConfig;
    }

    public void close() {
        if (serverAddressProvider != null) {
            serverAddressProvider.close();
        }
    }
}
