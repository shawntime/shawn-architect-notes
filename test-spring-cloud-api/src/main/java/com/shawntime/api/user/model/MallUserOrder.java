package com.shawntime.api.user.model;

import java.util.List;

import com.shawntime.api.order.model.MallOrder;

/**
 * @author mashaohua
 * @date 2022/3/30 18:12
 */
public class MallUserOrder {

    private MallUser mallUser;

    private List<MallOrder> mallOrders;

    public MallUser getMallUser() {
        return mallUser;
    }

    public void setMallUser(MallUser mallUser) {
        this.mallUser = mallUser;
    }

    public List<MallOrder> getMallOrders() {
        return mallOrders;
    }

    public void setMallOrders(List<MallOrder> mallOrders) {
        this.mallOrders = mallOrders;
    }
}
