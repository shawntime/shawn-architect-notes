package com.shawntime.controller;

import javax.annotation.Resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shawntime.api.user.IMallUserService;
import com.shawntime.api.user.model.MallUserIn;
import com.shawntime.api.user.model.MallUserOrderOut;
import com.shawntime.api.user.model.MallUserOut;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mashaohua
 * @date 2022/3/30 18:09
 */
@RestController
@RequestMapping("/mall/user")
public class MallUserController {

    @Resource(name = "mallUserImplService")
    private IMallUserService mallUserImplService;

    @HystrixCommand(fallbackMethod = "fallbackMallUser")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public MallUserOut getMallUser(int userId) {
        return mallUserImplService.getMallUser(userId);
    }

    private MallUserOut fallbackMallUser(int userId) {
        MallUserOut mallUserOut = new MallUserOut();
        mallUserOut.setUserId(userId);
        return mallUserOut;
    }

    @HystrixCommand(fallbackMethod = "fallbackSaveMallUser")
    @PostMapping("/save")
    public int saveMallUser(@RequestBody MallUserIn mallUser) {
        return mallUserImplService.saveMallUser(mallUser);
    }

    private int fallbackSaveMallUser(MallUserIn mallUser) {
        return -1;
    }

    @HystrixCommand(fallbackMethod = "fallbackOrderInfo")
    @RequestMapping(value = "/orderInfo", method = RequestMethod.GET)
    public MallUserOrderOut getMallUserOrder(int userId) {
        return mallUserImplService.getMallUserOrder(userId);
    }

    private MallUserOrderOut fallbackOrderInfo(int userId) {
        return null;

    }
}
