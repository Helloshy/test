package com.shy.rpc.test;


import com.shy.rpc.test.protocal.RpcProtocal;
import com.shy.rpc.test.protocal.Shy;
import com.shy.rpc.test.register.ProviderInfo;
import com.shy.rpc.test.transport.CallBackManager;
import com.shy.rpc.test.transport.MessageHandler;
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

public class RpcClient {

    private ProviderInfo subcribe;

    private RpcProtocal pt;

    public RpcClient(ProviderInfo subcribe, RpcProtocal pt) {
        String host = subcribe.getIp();
        int port = subcribe.getPort();
        Shy shy = (Shy) pt;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageHandler());
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            NioSocketChannel channel = (NioSocketChannel) f.channel();
            byte [] msgHeader = SerilUtil.seril(shy.getHeader());
            byte [] msgBody = SerilUtil.seril(shy.getBody());
            ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
            buf.writeBytes(msgHeader);
            buf.writeBytes(msgBody);
            CompletableFuture<Shy> callBack = new CompletableFuture<>();
            CallBackManager.addCallBack(shy.getHeader().getRequestId(),callBack);
            channel.writeAndFlush(buf);
            // Wait until the connection is closed.
            callBack.get();
            f.channel().closeFuture().sync();
        } catch (InterruptedException | ExecutionException e) {

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void Start(){

    }

    public Object send() {

        return null;
    }
}
