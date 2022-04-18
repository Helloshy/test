package com.shy.rpc.test.transport;


import com.shy.rpc.test.protocal.ShyHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class MessageHandler extends ByteToMessageDecoder {

    private final int headerLength = 190;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //
        if (in.isReadable() && in.readableBytes() >= headerLength ){
            //
            ByteBuf temp = ctx.alloc().buffer(headerLength);
            in.getBytes(headerLength,temp);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(temp.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            ShyHeader header = (ShyHeader) objectInputStream.readObject();
            if (in.readableBytes() >= headerLength + header.getDataLength()){
                out.add(in.readBytes(headerLength + header.getDataLength()));
            }
        }else {
            //不处理

        }
    }
}
