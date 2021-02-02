package com.shawntime.architect.notes.netty.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

/**
 * socket客户端
 */
public class ScoketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        System.out.println("客户端创建链接");
        OutputStream outputStream = socket.getOutputStream();
        IOUtils.write("你好服务端哈哈哈！！！！！！！".getBytes(), outputStream);
        outputStream.flush();

        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        int read = inputStream.read(bytes);
        if (read != -1) {
            System.out.println(new String(bytes, "UTF-8"));
        }
    }
}
