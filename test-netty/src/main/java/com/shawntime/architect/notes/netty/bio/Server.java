package com.shawntime.architect.notes.netty.bio;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Server {

    private static final ExecutorService EXECUTOR_SERVICE = newFixedThreadPool(10);

    private List<Socket> sockets;

    private int port;

    private ServerSocket serverSocket;

    private boolean isClosed = false;

    public Server(int port) {
        this.port = port;
        sockets = new ArrayList<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务启动成功，监听端口:" + port);
            while (!isClosed) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getRemoteSocketAddress() + "连接上线...");
                sockets.add(socket);
                EXECUTOR_SERVICE.submit(new SocketThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Socket socket : sockets) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private class SocketThread implements Runnable {

        private Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String msg = "欢迎【"
                    + socket.getRemoteSocketAddress() + "】进入聊天室！当前聊天室有【"
                    + sockets.size() + "】人";
            sendMsg(msg);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    msg = "【" + socket.getRemoteSocketAddress() + "】：" + line;
                    sendMsg(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sendMsg(String msg) {
            for (Socket socket : sockets) {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(socket.getOutputStream(), true);
                    pw.println(msg);
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
