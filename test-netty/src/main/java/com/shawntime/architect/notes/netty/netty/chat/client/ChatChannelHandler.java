package com.shawntime.architect.notes.netty.netty.chat.client;

import com.shawntime.architect.notes.netty.netty.chat.ChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * @author mashaohua
 * @date 2022/3/17 17:46
 */
public class ChatChannelHandler extends SimpleChannelInboundHandler<ChatMessage> {

    private ChannelGroup clientChannels = new DefaultChannelGroup(null);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 用户上线
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage chatMessage) throws Exception {

    }
}
