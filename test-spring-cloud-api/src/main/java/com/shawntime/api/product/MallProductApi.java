package com.shawntime.api.product;

import com.shawntime.api.product.model.MallProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mashaohua
 * @date 2022/3/30 17:41
 */
@FeignClient(value = "SERVER-PRODUCT", url = "/mall/product")
public interface MallProductApi {

    @GetMapping("/detail")
    MallProduct getMallProduct(@RequestParam("productId") int productId);
}
