package com.shawntime.controller;

import java.util.List;

import javax.annotation.Resource;

import com.shawntime.service.IMallOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mashaohua
 * @date 2022/3/24 14:19
 */
@RestController
@RequestMapping("/mall/order")
public class MallOrderController {

    @Resource
    private IMallOrderService mallOrderService;

    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public Object orderList(Integer userId) {
        return mallOrderService.getMallOrderList(userId);
    }

}
