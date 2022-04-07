package com.shawntime.service.impl;

import javax.annotation.Resource;

import com.shawntime.api.product.IProductService;
import com.shawntime.api.product.model.ProductOut;
import com.shawntime.dao.entity.Category;
import com.shawntime.dao.entity.Product;
import com.shawntime.dao.mapper.CategoryMapper;
import com.shawntime.dao.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
 * @author mashaohua
 * @date 2022/4/6 12:22
 */
@Service("productImplService")
public class ProductImplService implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ProductOut getProductOut(int productId) {
        Product product = productMapper.getProduct(productId);
        if (product == null) {
            return null;
        }
        ProductOut productOut = new ProductOut();
        productOut.setProductId(product.getProductId());
        productOut.setProductName(product.getProductName());
        productOut.setProductPrice(product.getPrice());
        productOut.setProductNum(product.getNum());
        productOut.setProductDesc(product.getProductDesc());
        productOut.setProductImageUrl(product.getProductImageUrl());
        productOut.setCategoryId(product.getCategory());
        Category category = categoryMapper.getCategory(product.getCategory());
        if (category != null) {
            productOut.setCateGoryName(category.getCateGoryName());
        }
        return productOut;
    }
}
