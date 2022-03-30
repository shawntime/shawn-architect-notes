package com.shawntime.api.user;

import java.util.List;

import com.shawntime.api.order.model.MallOrder;
import com.shawntime.api.user.model.MallUser;
import com.shawntime.api.user.model.MallUserOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/3/30 17:33
 */
@FeignClient("SERVER-USER")
public interface MallUserApi {

    @GetMapping("/mall/user/detail")
    MallUser getMallUser(@RequestParam("userId") int userId);

    @PostMapping("/mall/user/save")
    int saveMallUser(@RequestBody MallUser mallUser);

    @GetMapping("/mall/user/orderInfo")
    MallUserOrder getMallUserOrder(@RequestParam("userId") int userId);
}
