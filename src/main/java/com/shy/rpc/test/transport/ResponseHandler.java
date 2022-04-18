package com.shy.rpc.test.transport;

import com.shy.rpc.test.protocal.Shy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Shy shy = (Shy) msg;
        Future<?> callBack = CallBackManager.getCallBack(shy.getHeader().getRequestId());
        CompletableFuture f = (CompletableFuture)callBack;
        f.complete(shy);
    }
}
