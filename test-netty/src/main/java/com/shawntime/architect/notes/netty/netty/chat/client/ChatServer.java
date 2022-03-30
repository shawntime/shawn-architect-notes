package com.shawntime.architect.notes.netty.netty.chat.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author mashaohua
 * @date 2022/3/17 17:36
 */
public class ChatServer {

    private final int serverPort;

    public ChatServer(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * 处理连接
     */
    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

    /**
     * 处理IO读写
     */
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public void connect() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {

                    }
                });
    }

}
