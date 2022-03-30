package com.shawntime.architect.notes.netty.netty.chat;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/3/17 18:10
 */
@Getter
@Setter
public class ChatMessage {

    /**
     * 消息类型
     * 1：系统消息
     * 2：用户消息
     */
    private int messageType;

    /**
     * 发送类型
     * 1：群发
     * 2：1对1私聊
     * 3：1对N 组
     */
    private int sendType;

    private int senderId;

    private int receiverId;

    private int groupId;

    private String senderName;

    private String message;
}
