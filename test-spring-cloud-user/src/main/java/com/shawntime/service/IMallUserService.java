package com.shawntime.service;

import com.shawntime.api.user.model.MallUser;
import com.shawntime.api.user.model.MallUserOrder;

/**
 * @author mashaohua
 * @date 2022/3/30 18:14
 */
public interface IMallUserService {

    MallUser getMallUser(int userId);

    int saveMallUser(MallUser mallUser);

    MallUserOrder getMallUserOrder(int userId);
}
