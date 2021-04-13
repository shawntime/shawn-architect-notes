package com.shawntime.architect.notes.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NIOServer {

    private int port;

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private volatile boolean isClosed;

    private List<SocketChannel> socketChannels = new ArrayList<>();

    public NIOServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            // 设置非阻塞
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (!isClosed) {
                System.out.println("等待客户端链接...");
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    handler(selectionKey);
                    keyIterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handler(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            acceptHandler(selectionKey);
        }
        if (selectionKey.isReadable()) {
            readHandler(selectionKey);
        }
    }

    private void acceptHandler(SelectionKey selectionKey) throws IOException {
        System.out.println("有新客户端链接...");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
        socketChannels.add(socketChannel);
        String msg = "欢迎【"
                + socketChannel.getRemoteAddress() + "】进入聊天室！当前聊天室有【"
                + socketChannels.size() + "】人";
        print(msg);
    }

    private void readHandler(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(byteBuffer);
        if (len != -1) {
            String line = "【" + socketChannel.getRemoteAddress() + "】：" + new String(byteBuffer.array(), 0, len);
            System.out.println(line);
            print(line);
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private void print(String line) throws IOException {
        for (SocketChannel channel : socketChannels) {
            ByteBuffer buffer = ByteBuffer.wrap(line.getBytes());
            channel.write(buffer);
        }
    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer(9023);
        server.start();
    }

}
