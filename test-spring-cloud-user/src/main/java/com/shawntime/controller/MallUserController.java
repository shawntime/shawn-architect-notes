package com.shawntime.controller;

import javax.annotation.Resource;

import com.shawntime.api.user.MallUserApi;
import com.shawntime.api.user.model.MallUser;
import com.shawntime.api.user.model.MallUserOrder;
import com.shawntime.service.IMallUserService;
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
public class MallUserController implements MallUserApi {

    @Resource
    private IMallUserService mallUserService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @Override
    public MallUser getMallUser(int userId) {
        return mallUserService.getMallUser(userId);
    }

    @PostMapping("/save")
    @Override
    public int saveMallUser(@RequestBody MallUser mallUser) {
        return mallUserService.saveMallUser(mallUser);
    }

    @RequestMapping(value = "/orderInfo", method = RequestMethod.GET)
    @Override
    public MallUserOrder getMallUserOrder(int userId) {
        return mallUserService.getMallUserOrder(userId);
    }
}
