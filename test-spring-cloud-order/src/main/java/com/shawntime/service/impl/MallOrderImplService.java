package com.shawntime.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.shawntime.api.product.IProductService;
import com.shawntime.api.product.model.ProductOut;
import com.shawntime.api.order.IMallOrderService;
import com.shawntime.api.order.model.MallOrderIn;
import com.shawntime.api.order.model.MallOrderOut;
import com.shawntime.dao.entity.MallOrder;
import com.shawntime.dao.mapper.MallOrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author mashaohua
 * @date 2022/3/24 14:17
 */
@Service("mallOrderImplService")
public class MallOrderImplService implements IMallOrderService {

    @Resource
    private MallOrderMapper mallOrderMapper;

    @Resource
    private IProductService productService;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<MallOrderOut> getMallOrderList(int userId) {
        List<MallOrder> mallOrderList = mallOrderMapper.getMallOrderList(userId);
        return mallOrderList.stream()
                .map(order -> {
                    MallOrderOut orderOut = new MallOrderOut();
                    orderOut.setId(order.getId());
                    orderOut.setUserId(order.getUserId());
                    orderOut.setUserName(order.getUserName());
                    orderOut.setProductId(order.getProductId());
                    orderOut.setProductName(order.getProductName());
                    // Ribbon方式
                    // String url = "http://SERVER-PRODUCT/mall/product/detail?productId=1";
                    // ProductOut product = restTemplate.getForObject("url", ProductOut.class);
                    // ResponseEntity<ProductOut> productOut = restTemplate.getForEntity("http://SERVER-PRODUCT/mall/product/detail?productId=1", ProductOut.class);
                    // OpenFeign方式
                    ProductOut productOut = productService.getProductOut(order.getProductId());
                    orderOut.setProductOut(productOut);
                    return orderOut;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int saveMallOrder(MallOrderIn mallOrderIn) {
        MallOrder mallOrder = new MallOrder();
        mallOrder.setUserId(mallOrderIn.getUserId());
        mallOrder.setUserName(mallOrderIn.getUserName());
        mallOrder.setProductId(mallOrderIn.getProductId());
        mallOrder.setProductName(mallOrderIn.getProductName());
        return mallOrderMapper.saveMallOrder(mallOrder);
    }
}
