package com.shawntime.controller;

import javax.annotation.Resource;

import com.shawntime.api.product.IProductService;
import com.shawntime.api.product.model.ProductOut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mashaohua
 * @date 2022/4/6 16:21
 */
@RestController
@RequestMapping("/mall/product")
public class ProductController {

    @Resource(name = "productImplService")
    private IProductService productImplService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ProductOut product(Integer productId) {
        return productImplService.getProductOut(productId);
    }
}
