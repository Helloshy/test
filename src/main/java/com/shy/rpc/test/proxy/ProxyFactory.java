package com.shy.rpc.test.proxy;

public interface ProxyFactory {

  <T>T getProxy(Class<T> clz);
}
