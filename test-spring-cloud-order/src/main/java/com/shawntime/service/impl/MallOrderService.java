package com.shawntime.service.impl;

import java.util.List;
import javax.annotation.Resource;

import com.shawntime.dao.entity.MallOrder;
import com.shawntime.dao.mapper.MallOrderMapper;
import com.shawntime.service.IMallOrderService;
import org.springframework.stereotype.Service;

/**
 * @author mashaohua
 * @date 2022/3/24 14:17
 */
@Service
public class MallOrderService implements IMallOrderService {

    @Resource
    private MallOrderMapper mallOrderMapper;

    @Override
    public List<MallOrder> getMallOrderList(int userId) {
        return mallOrderMapper.getMallOrderList(userId);
    }
}
