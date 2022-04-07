package com.shawntime.api.order;

import java.util.List;

import com.shawntime.api.order.model.MallOrderIn;
import com.shawntime.api.order.model.MallOrderOut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/3/30 17:25
 */
@Component
@FeignClient(name = "SERVER-ORDER", path = "/mall/order")
public interface IMallOrderService {

    @GetMapping("/orderList")
    List<MallOrderOut> getMallOrderList(@RequestParam("userId") int userId);

    @PostMapping("/save")
    int saveMallOrder(@RequestBody MallOrderIn mallOrder);
}
