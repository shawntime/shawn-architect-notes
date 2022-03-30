package com.shawntime.service.impl;

import java.util.List;
import javax.annotation.Resource;

import com.shawntime.entity.MallOrder;
import com.shawntime.service.IMallOrderService;
import com.shawntime.utils.JsonHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author mashaohua
 * @date 2022/3/24 15:33
 */
@Service
public class MallOrderService implements IMallOrderService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<MallOrder> getMallOrderList(int userId) {
        String url = "http://SERVER-ORDER/mall/order/orderList?userId=" + userId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return JsonHelper.deSerializeList(responseEntity.getBody(), MallOrder.class);
        }
        return null;
    }
}
