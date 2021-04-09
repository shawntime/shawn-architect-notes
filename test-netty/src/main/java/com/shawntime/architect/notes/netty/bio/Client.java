package com.shawntime.architect.notes.netty.bio;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.IOUtils;

public class Client {

    private static final ExecutorService EXECUTOR_SERVICE = newFixedThreadPool(1);

    private String serverIp;

    private int serverPort;

    private Socket socket;

    private boolean isClosed;

    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            socket = new Socket(serverIp, serverPort);
            EXECUTOR_SERVICE.submit(new ClientThread(socket));
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
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

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ClientThread implements Runnable {

        private Socket socket;

        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请留言：");
            while (!isClosed) {
                String line = scanner.nextLine();
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(socket.getOutputStream(), true);
                    pw.println(line);
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
