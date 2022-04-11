package com.shy.rpc.test.proxy;

import com.shy.rpc.test.protocal.Shy;
import com.shy.rpc.test.register.ProviderInfo;
import com.shy.rpc.test.register.RegisterCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultProxyFactory implements ProxyFactory{

    private static final ConcurrentHashMap<String,Object> proxyCache = new ConcurrentHashMap<>();

    private RegisterCenter<ProviderInfo> registerCenter;

    public DefaultProxyFactory(RegisterCenter<ProviderInfo> registerCenter) {
        this.registerCenter = registerCenter;
    }

    @Override
    public <T> T getProxy(Class<T> clz) {
        return doCreatProxy(clz);
    }

    private <T> T doCreatProxy(Class<T> clz) {
        Object o = proxyCache.get(clz.getName());
        if (o != null){
            return (T)o;
        }else {
            synchronized (clz){
                if (proxyCache.get(clz.getName()) == null){
                    T o1 = (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clz}, new Invoker(registerCenter));
                    proxyCache.putIfAbsent(clz.getName(),o1);
                }
                return (T)proxyCache.get(clz.getName());
            }
        }
    }


}
