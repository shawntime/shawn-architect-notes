package com.shawntime.architect.notes.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServer {

    private Charset charset = Charset.forName("utf-8");

    private int port;

    private AsynchronousChannelGroup channelGroup;

    private AsynchronousServerSocketChannel serverSocketChannel;

    private List<AsynchronousSocketChannel> socketChannels;

    private boolean isClosed;

    public AIOServer(int port) {
        this.port = port;
        socketChannels = new ArrayList<>();
    }

    private void start() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
        serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);
        serverSocketChannel.bind(new InetSocketAddress(port));
        System.out.println("启动服务器,监听端口:"+port);
        serverSocketChannel.accept(null, new AcceptHandler());
        //阻塞式调用,防止占用系统资源
        System.in.read();
    }

    private void print(String line) throws IOException {
        for (AsynchronousSocketChannel channel : socketChannels) {
            ByteBuffer buffer = send(line);
            channel.write(buffer);
        }
    }

    private class ClientHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel socketChannel;

        public ClientHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            buffer.flip();
            try {
                String receive = receive(buffer);
                SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                if ("quit".equalsIgnoreCase(receive)) {
                    System.out.println(remoteAddress + "已下线...");
                    socketChannels.remove(socketChannel);
                    String msg = "【"
                            + remoteAddress + "】退出聊天室！当前聊天室有【"
                            + socketChannels.size() + "】人";
                    print(msg);
                    socketChannel.close();
                    return;
                }
                String line = "【" + remoteAddress + "】：" + receive;
                System.out.println(line);
                print(line);
                buffer.clear();
                socketChannel.read(buffer, buffer,this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {

        }
    }

    private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
            if (serverSocketChannel.isOpen()){
                serverSocketChannel.accept(null,this);
            }
            if (socketChannel.isOpen()) {
                socketChannels.add(socketChannel);
                try {
                    String msg = "欢迎【"
                            + socketChannel.getRemoteAddress() + "】进入聊天室！当前聊天室有【"
                            + socketChannels.size() + "】人";
                    print(msg);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    ClientHandler clientHandler = new ClientHandler(socketChannel);
                    socketChannel.read(buffer, buffer, clientHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("error");
        }
    }

    private ByteBuffer send(String msg) {
        return charset.encode(msg);
    }

    private String receive(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return String.valueOf(charBuffer);
    }


    public static void main(String[] args) {
        AIOServer server = new AIOServer(9090);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
