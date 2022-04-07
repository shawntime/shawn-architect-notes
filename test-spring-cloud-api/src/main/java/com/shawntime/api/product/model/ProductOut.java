package com.shawntime.api.product.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/4/6 12:21
 */
@Getter
@Setter
public class ProductOut {

    private int productId;

    private String productName;

    private BigDecimal productPrice;

    private int productNum;

    private String productDesc;

    private String productImageUrl;

    private int categoryId;

    private String cateGoryName;
}
