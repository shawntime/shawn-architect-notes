package com.shawntime.architect.notes.netty.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;

/**
 * 服务端
 */
public class NettyServer {

    private int port;

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    public NettyServer(int port) {
        this.port = port;
    }

    public void connect() throws InterruptedException {
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(ServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ServerChannelInitializer())
                    .bind(port);
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("服务器启动成功");
                }
            });
            channel = channelFuture.channel();
            channel.close().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
