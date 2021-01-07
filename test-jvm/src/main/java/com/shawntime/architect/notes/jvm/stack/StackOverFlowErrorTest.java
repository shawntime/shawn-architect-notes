package com.shawntime.architect.notes.jvm.stack;

public class StackOverFlowErrorTest {

    private static int count;

    private static void redo() {
        count++;
        redo();
    }

    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable e) {
            System.out.println("count:" + count);
            e.printStackTrace();
        }
    }
}
