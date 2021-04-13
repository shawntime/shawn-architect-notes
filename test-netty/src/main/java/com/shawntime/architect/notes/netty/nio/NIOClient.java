package com.shawntime.architect.notes.netty.nio;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class NIOClient {

    private static final ExecutorService EXECUTOR_SERVICE = newFixedThreadPool(1);

    private String ip;

    private int port;

    private volatile boolean isClosed;

    private SocketChannel socketChannel;

    public NIOClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(ip, port));
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            EXECUTOR_SERVICE.submit(new ChatThread());

            while (!isClosed) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handler(selectionKey);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handler(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isConnectable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (socketChannel.isConnectionPending()) {
                socketChannel.finishConnect();
            }
            socketChannel.configureBlocking(false);
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
        }
        if (selectionKey.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len = socketChannel.read(byteBuffer);
            if (len != -1) {
                String line = new String(byteBuffer.array(), 0, len);
                System.out.println(line);
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    private class ChatThread implements Runnable {

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请留言：");
            while (!isClosed) {
                String line = scanner.nextLine();
                ByteBuffer byteBuffer = ByteBuffer.wrap(line.getBytes());
                try {
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        NIOClient client = new NIOClient("127.0.0.1", 9023);
        client.start();
    }
}
