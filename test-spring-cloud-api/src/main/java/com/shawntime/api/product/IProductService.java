package com.shawntime.api.product;

import com.shawntime.api.product.model.ProductOut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/4/6 12:21
 */
@Component
@FeignClient(name = "SERVER-PRODUCT", fallbackFactory = ProductServiceFallBackFactory.class)
public interface IProductService {

    @GetMapping("/mall/product/detail")
    ProductOut getProductOut(@RequestParam("productId") int productId);
}
