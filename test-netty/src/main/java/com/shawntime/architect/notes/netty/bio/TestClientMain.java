package com.shawntime.architect.notes.netty.bio;

public class TestClientMain {

    public static void main(String[] args) {
        new Client("127.0.0.1", 9981).start();
    }
}
