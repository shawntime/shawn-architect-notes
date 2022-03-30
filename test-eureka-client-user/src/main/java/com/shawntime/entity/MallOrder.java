package com.shawntime.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/3/24 14:12
 */
@Setter
@Getter
public class MallOrder {

    private int id;

    private int userId;

    private String userName;

    private int productId;

    private String productName;
}
