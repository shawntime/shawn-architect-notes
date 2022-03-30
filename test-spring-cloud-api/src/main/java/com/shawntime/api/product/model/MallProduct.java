package com.shawntime.api.product.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/3/30 17:41
 */
@Getter
@Setter
public class MallProduct {

    private int id;

    private String productName;

    private int productCategoryId;

    private String productDesc;

    private String productImageUrl;

    private BigDecimal productPrice;

}
