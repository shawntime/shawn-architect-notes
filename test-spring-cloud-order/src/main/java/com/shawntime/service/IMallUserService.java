package com.shawntime.service;

import com.shawntime.dao.entity.MallUser;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author mashaohua
 * @date 2022/3/30 17:11
 */
@FeignClient("")
public interface IMallUserService {

    MallUser getMallUser(int orderId);
}
