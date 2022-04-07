package com.shawntime.api.user;

import com.shawntime.api.user.model.MallUserIn;
import com.shawntime.api.user.model.MallUserOut;
import com.shawntime.api.user.model.MallUserOrderOut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/3/30 17:33
 */
@Component
@FeignClient(name = "SERVER-USER")
public interface IMallUserService {

    @GetMapping("/mall/user/detail")
    MallUserOut getMallUser(@RequestParam("userId") int userId);

    @PostMapping("/mall/user/save")
    int saveMallUser(@RequestBody MallUserIn mallUserIn);

    @GetMapping("/mall/user/orderInfo")
    MallUserOrderOut getMallUserOrder(@RequestParam("userId") int userId);
}
