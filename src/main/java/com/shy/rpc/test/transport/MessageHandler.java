package com.shy.rpc.test.transport;


import com.shy.rpc.test.protocal.Shy;
import com.shy.rpc.test.protocal.ShyHeader;
import com.shy.rpc.test.protocal.ShyPayload;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class MessageHandler extends ByteToMessageDecoder {

    private final int headerLength = 190;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int offset = 0;
        while (in.isReadable() && in.readableBytes() >= headerLength ){
            byte [] bytes = new byte[headerLength];
            in.getBytes(offset,bytes);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            ShyHeader header = (ShyHeader) objectInputStream.readObject();
            System.out.println("request id :" + header.getRequestId());
            if (in.readableBytes() >= headerLength + header.getDataLength()){
                byte [] headBytes = new byte[headerLength];
                byte [] palyloadBytes = new byte[header.getDataLength()];
                in.readBytes(headBytes);
                in.readBytes(palyloadBytes);
                ByteArrayInputStream bai = new ByteArrayInputStream(headBytes);
                ObjectInputStream objin = new ObjectInputStream(bai);
                ShyHeader shyHeader = (ShyHeader) objin.readObject();

                ByteArrayInputStream bai2 = new ByteArrayInputStream(palyloadBytes);
                ObjectInputStream objin2 = new ObjectInputStream(bai2);
                ShyPayload payload = (ShyPayload) objin2.readObject();
                Shy shy = new Shy(shyHeader,payload);
                out.add(shy);
                offset = headerLength + header.getDataLength();
            }
        }

    }
}
