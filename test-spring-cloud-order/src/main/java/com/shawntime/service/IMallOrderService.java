package com.shawntime.service;

import java.util.List;

import com.shawntime.dao.entity.MallOrder;

/**
 * @author mashaohua
 * @date 2022/3/24 14:17
 */
public interface IMallOrderService {

    List<MallOrder> getMallOrderList(int userId);
}
