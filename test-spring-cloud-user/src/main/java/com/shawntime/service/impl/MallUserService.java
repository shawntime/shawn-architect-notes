package com.shawntime.service.impl;

import java.util.List;
import javax.annotation.Resource;

import com.shawntime.api.order.MallOrderApi;
import com.shawntime.api.order.model.MallOrder;
import com.shawntime.api.user.model.MallUser;
import com.shawntime.api.user.model.MallUserOrder;
import com.shawntime.service.IMallUserService;
import org.springframework.stereotype.Service;

/**
 * @author mashaohua
 * @date 2022/3/30 18:06
 */
@Service
public class MallUserService implements IMallUserService {

    @Resource
    private MallOrderApi mallOrderApi;

    @Override
    public MallUser getMallUser(int userId) {
        return null;
    }

    @Override
    public int saveMallUser(MallUser mallUser) {
        return 0;
    }

    @Override
    public MallUserOrder getMallUserOrder(int userId) {
        List<MallOrder> mallOrderList = mallOrderApi.getMallOrderList(userId);
        MallUser mallUser = getMallUser(userId);
        MallUserOrder mallUserOrder = new MallUserOrder();
        mallUserOrder.setMallOrders(mallOrderList);
        mallUserOrder.setMallUser(mallUser);
        return mallUserOrder;
    }
}
