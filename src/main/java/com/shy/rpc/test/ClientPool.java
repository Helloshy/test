package com.shy.rpc.test;

import com.shy.rpc.test.protocal.RpcProtocal;
import com.shy.rpc.test.register.ProviderInfo;

public class ClientPool {



    public static RpcClient getCilent(ProviderInfo subcribe, RpcProtocal pt) {
        return new RpcClient(subcribe,pt);
    }
}
