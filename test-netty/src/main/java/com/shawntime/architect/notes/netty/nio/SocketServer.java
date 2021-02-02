package com.shawntime.architect.notes.netty.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;

/**
 * 服务端
 */
public class SocketServer {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待客户端连接");
            Socket socket = serverSocket.accept();
            System.out.println("有新的客户端连接...");
            executor.submit(() -> {
                try {
                    handle(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private static void handle(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        System.out.println("准备read。。");
        byte[] bytes = new byte[1024];
        //接收客户端的数据，阻塞方法，没有数据可读时就阻塞
        int read = inputStream.read(bytes);
        if (read != -1) {
            System.out.println(new String(bytes, Charset.defaultCharset()));
        }
        OutputStream outputStream = socket.getOutputStream();
        IOUtils.write("Hello Client".getBytes(), outputStream);
        outputStream.flush();
    }
}
