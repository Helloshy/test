package com.shy.rpc.test.transport;

import com.shy.rpc.test.protocal.Shy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BizHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Shy shy = (Shy) msg;
        ctx.writeAndFlush("收到数据:"+msg.toString());
    }
}
