package com.shawntime.architect.notes.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mashaohua
 * @date 2022/2/17 11:25
 */
public class ZkConfigs {

    private List<ZkConfig> zkConfigs = new ArrayList<>();

    public ZkConfigs() {
        super();
    }

    public ZkConfigs(List<ZkConfig> zkConfigs) {
        this.zkConfigs = zkConfigs;
    }

    public ZkConfigs addZkConfig(ZkConfig zkConfig) {
        if (zkConfigs == null) {
            zkConfigs = new ArrayList<>();
        }
        zkConfigs.add(zkConfig);
        return this;
    }

    public String getZkConnectString() {
        return zkConfigs.stream()
                .map(config -> config.getIp() + ":" + config.getPort())
                .collect(Collectors.joining(","));
    }

    public List<ZkConfig> getZkConfigs() {
        return zkConfigs;
    }

    public void setZkConfigs(List<ZkConfig> zkConfigs) {
        this.zkConfigs = zkConfigs;
    }
}
