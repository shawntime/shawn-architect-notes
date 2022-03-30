package com.shawntime.api.order;

import java.util.List;

import com.shawntime.api.order.model.MallOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/3/30 17:25
 */
@FeignClient("SERVER-ORDER")
public interface MallOrderApi {

    @GetMapping("/mall/order/list")
    List<MallOrder> getMallOrderList(@RequestParam("userId") int userId);

    @PostMapping("/mall/order/save")
    int saveMallOrder(@RequestBody MallOrder mallOrder);
}
