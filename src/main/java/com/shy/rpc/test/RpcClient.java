package com.shy.rpc.test;


import com.shy.rpc.test.excepton.ServiceException;
import com.shy.rpc.test.protocal.RpcProtocal;
import com.shy.rpc.test.protocal.Shy;
import com.shy.rpc.test.register.ProviderInfo;
import com.shy.rpc.test.transport.CallBackManager;
import com.shy.rpc.test.transport.MessageHandler;
import com.shy.rpc.test.transport.ResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RpcClient {

    private ProviderInfo subcribe;

    private RpcProtocal pt;

    private NioSocketChannel channel;

    public RpcClient(ProviderInfo subcribe, RpcProtocal pt) {
        this.subcribe = subcribe;
        this.pt = pt;
        String host = subcribe.getIp();
        int port = subcribe.getPort();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageHandler())
                            .addLast(new ResponseHandler());
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            channel = (NioSocketChannel) f.channel();
        } catch (InterruptedException e) {
            throw new ServiceException(e.getMessage());
        } finally {
        }
    }

    public void Start(){

    }

    public Object send() {
        Shy shy = (Shy) pt;
        byte [] msgBody = SerilUtil.seril(shy.getBody());
        shy.getHeader().setDataLength(msgBody.length);
        byte [] msgHeader = SerilUtil.seril(shy.getHeader());
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        buf.writeBytes(msgHeader);
        buf.writeBytes(msgBody);
        CompletableFuture<Shy> callBack = new CompletableFuture<>();
        CallBackManager.addCallBack(shy.getHeader().getRequestId(),callBack);
        channel.writeAndFlush(buf);
        try {
            try {
                return callBack.get(30, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                throw new ServiceException("调用超时");
            }
        } catch (InterruptedException e) {
            throw new ServiceException(e.getMessage());
        } catch (ExecutionException e) {
            throw new ServiceException(e.getMessage());
        }

    }
}
