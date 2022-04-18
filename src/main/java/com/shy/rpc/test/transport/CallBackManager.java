package com.shy.rpc.test.transport;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class CallBackManager {

    private static final ConcurrentHashMap<Long, Future<?>> callbackMaps = new ConcurrentHashMap<>();


    public static void addCallBack(Long requestId,Future f){
        callbackMaps.put(requestId,f);
    }

    public static Future<?> getCallBack(Long requestId){
        return callbackMaps.get(requestId);
    }

}
