package com.shawntime.dao.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/4/6 11:00
 */
@Getter
@Setter
public class Product {

    private int productId;

    private String productName;

    private BigDecimal price;

    private int num;

    private int category;

    private String productDesc;

    private String productImageUrl;
}
