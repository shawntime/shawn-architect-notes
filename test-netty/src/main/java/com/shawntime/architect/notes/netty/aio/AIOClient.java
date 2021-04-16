package com.shawntime.architect.notes.netty.aio;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AIOClient {

    private Charset charset = Charset.forName("utf-8");

    private static final ExecutorService EXECUTOR_SERVICE = newFixedThreadPool(1);

    private String ip;

    private int port;

    private volatile boolean isClosed;

    private AsynchronousSocketChannel socketChannel;

    public AIOClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        try {
            socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(new InetSocketAddress(ip, port)).get();
            EXECUTOR_SERVICE.submit(new ChatThread(Thread.currentThread()));
            while (!isClosed) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int length = socketChannel.read(buffer).get();
                if (length > 0) {
                    buffer.flip();
                    System.out.println(receive(buffer));
                    buffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("线程中断退出...");
            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            EXECUTOR_SERVICE.shutdown();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private class ChatThread implements Runnable {

        private Thread mainThread;

        public ChatThread(Thread mainThread) {
            this.mainThread = mainThread;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请留言：");
            while (!isClosed) {
                String line = scanner.nextLine();
                socketChannel.write(send(line));
                if ("quit".equalsIgnoreCase(line)) {
                    isClosed = true;
                    mainThread.interrupt();
                }
            }
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
        AIOClient client = new AIOClient("127.0.0.1", 9090);
        client.start();

    }
}
