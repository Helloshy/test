package com.shy.rpc.test.register;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleRegisterCenter implements RegisterCenter<ProviderInfo>{

    private static final ConcurrentHashMap<String,ProviderInfo> providerInfos = new ConcurrentHashMap<>();
    @Override
    public void regist(ProviderInfo providerInfo) {
        providerInfos.putIfAbsent(providerInfo.getProviderName(),providerInfo);
    }

    @Override
    public ProviderInfo subcribe(String providerName) {
        return providerInfos.get(providerName);
    }
}
