package com.shawntime.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shawntime.api.order.IMallOrderService;
import com.shawntime.api.order.model.MallOrderOut;
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

    @HystrixCommand(fallbackMethod = "fallbackOrderList")
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public List<MallOrderOut> orderList(Integer userId) {
        return mallOrderImplService.getMallOrderList(userId);
    }

    private List<MallOrderOut> fallbackOrderList(Integer userId) {
        return new ArrayList<>();
    }

}
