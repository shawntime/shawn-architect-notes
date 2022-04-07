package com.shawntime.api.user.model;

import java.util.List;

import com.shawntime.api.order.model.MallOrderOut;

/**
 * @author mashaohua
 * @date 2022/3/30 18:12
 */
public class MallUserOrderOut {

    private MallUserOut mallUser;

    private List<MallOrderOut> mallOrders;

    public MallUserOut getMallUser() {
        return mallUser;
    }

    public void setMallUser(MallUserOut mallUser) {
        this.mallUser = mallUser;
    }

    public List<MallOrderOut> getMallOrders() {
        return mallOrders;
    }

    public void setMallOrders(List<MallOrderOut> mallOrders) {
        this.mallOrders = mallOrders;
    }
}
