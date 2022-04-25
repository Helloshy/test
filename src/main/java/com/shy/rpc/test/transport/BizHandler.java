package com.shy.rpc.test.transport;

import com.shy.rpc.test.SerilUtil;
import com.shy.rpc.test.protocal.Shy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BizHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("BizHandler msg:" + msg.toString());
        Shy shy = (Shy) msg;
        byte [] msgBody = SerilUtil.seril(shy.getBody());
        shy.getHeader().setDataLength(msgBody.length);
        byte [] msgHeader = SerilUtil.seril(shy.getHeader());
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        buf.writeBytes(msgHeader);
        buf.writeBytes(msgBody);
        ctx.writeAndFlush(buf);
    }
}
