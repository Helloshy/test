package com.shy.rpc.test.proxy;

import com.shy.rpc.test.ClientPool;
import com.shy.rpc.test.IOrderService;
import com.shy.rpc.test.RpcClient;
import com.shy.rpc.test.protocal.RpcProtocal;
import com.shy.rpc.test.protocal.Shy;
import com.shy.rpc.test.protocal.ShyHeader;
import com.shy.rpc.test.protocal.ShyPayload;
import com.shy.rpc.test.register.ProviderInfo;
import com.shy.rpc.test.register.RegisterCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Invoker implements InvocationHandler {

    private RegisterCenter<ProviderInfo> registerCenter;

    public Invoker(RegisterCenter<ProviderInfo> registerCenter) {
        this.registerCenter = registerCenter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装调用方法信息,告诉调用放需要执行的方法以及方法参数
        ProviderInfo subcribe = registerCenter.subcribe(IOrderService.class.getName());
        String protocal = subcribe.getProtocal();
        //确定通信协议
        switch (protocal){
            case "shy":
                ShyPayload payload = new ShyPayload();
                payload.setInterfaceName(subcribe.getProviderName());
                payload.setMethodName(method.getName());
                payload.setArgs(args);
                ShyHeader header = new ShyHeader(ShyHeader.REQUEST_FLAG);
                RpcProtocal pt = new Shy(header,payload);
                RpcClient client = ClientPool.getCilent(subcribe,pt);
                Object result = client.send();
                System.out.println(result == null?"get rpc result: "+"null value":"get rpc result: "+result.toString());
                return result;
            default:
                throw new IllegalArgumentException("不支持的通信协议");
        }
        //发送


    }


}
