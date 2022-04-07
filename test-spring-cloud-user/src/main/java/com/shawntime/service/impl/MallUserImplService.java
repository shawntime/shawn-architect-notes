package com.shawntime.service.impl;

import java.util.List;
import javax.annotation.Resource;

import com.shawntime.api.order.IMallOrderService;
import com.shawntime.api.order.model.MallOrderOut;
import com.shawntime.api.user.IMallUserService;
import com.shawntime.api.user.model.MallUserIn;
import com.shawntime.api.user.model.MallUserOrderOut;
import com.shawntime.api.user.model.MallUserOut;
import com.shawntime.dao.entity.MallUser;
import com.shawntime.dao.mapper.MallUserMapper;
import org.springframework.stereotype.Service;

/**
 * @author mashaohua
 * @date 2022/3/30 18:06
 */
@Service("mallUserImplService")
public class MallUserImplService implements IMallUserService {

    @Resource
    private IMallOrderService mallOrderService;

    @Resource
    private MallUserMapper mallUserMapper;

    @Override
    public MallUserOut getMallUser(int userId) {
        MallUser mallUser = mallUserMapper.getMallUser(userId);
        if (mallUser == null) {
            return null;
        }
        MallUserOut mallUserOut = new MallUserOut();
        mallUserOut.setUserId(mallUser.getId());
        mallUserOut.setUsername(mallUser.getAge());
        mallUserOut.setAge(mallUser.getAge());
        mallUserOut.setSex(mallUser.getSex());
        return mallUserOut;
    }

    @Override
    public int saveMallUser(MallUserIn mallUserIn) {
        MallUser mallUser = new MallUser();
        mallUser.setUserName(mallUserIn.getUsername());
        mallUser.setAge(mallUserIn.getAge());
        mallUser.setSex(mallUserIn.getSex());
        return mallUserMapper.saveUser(mallUser);
    }

    @Override
    public MallUserOrderOut getMallUserOrder(int userId) {
        List<MallOrderOut> mallOrderList = mallOrderService.getMallOrderList(userId);
        MallUserOut mallUser = getMallUser(userId);
        MallUserOrderOut mallUserOrder = new MallUserOrderOut();
        mallUserOrder.setMallOrders(mallOrderList);
        mallUserOrder.setMallUser(mallUser);
        return mallUserOrder;
    }
}
