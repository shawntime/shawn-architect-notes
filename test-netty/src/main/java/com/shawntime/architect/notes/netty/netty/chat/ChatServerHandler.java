package com.shawntime.architect.notes.netty.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 聊天服务
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 表示channel处于就绪状态, 提示上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String msg = "【客户端】" + channel.remoteAddress() + "上线了，当前在线人数：" + clients.size();
        clients.writeAndFlush(msg);
        // 将当前channel加入channelGroup中
        clients.add(channel);
        System.out.println(channel.remoteAddress() + "上线了");
    }

    /**
     * channel断开连接后调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clients.remove(channel);
        String msg = "【客户端】" + channel.remoteAddress() + "下线了，当前在线人数：" + clients.size();
        clients.writeAndFlush(msg);
        System.out.println(channel.remoteAddress() + "下线了");
    }

    /**
     * 读取客户端数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    /**
     * 读取完成
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
