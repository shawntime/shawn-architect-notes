package com.shawntime.controller;

import javax.annotation.Resource;

import com.shawntime.api.order.IMallOrderService;
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

    @Resource(name = "mallOrderImplService")
    private IMallOrderService mallOrderImplService;

    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public Object orderList(Integer userId) {
        return mallOrderImplService.getMallOrderList(userId);
    }

}
