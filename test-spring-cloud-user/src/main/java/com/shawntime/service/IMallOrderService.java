package com.shawntime.service;

import java.util.List;

import com.shawntime.entity.MallOrder;

/**
 * @author mashaohua
 * @date 2022/3/24 15:32
 */
public interface IMallOrderService {

    List<MallOrder> getMallOrderList(int userId);
}
