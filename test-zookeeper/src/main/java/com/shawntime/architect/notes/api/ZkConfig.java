package com.shawntime.architect.notes.api;

/**
 * @author mashaohua
 * @date 2022/2/17 11:23
 */
public class ZkConfig {

    private String ip;

    private int port;

    public ZkConfig(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
