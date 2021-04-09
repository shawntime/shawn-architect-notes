package com.shawntime.architect.notes.netty.bio;

public class TestServerMain {

    public static void main(String[] args) {
        int port = 9981;
        Server server = new Server(port);
        server.start();
    }

}
