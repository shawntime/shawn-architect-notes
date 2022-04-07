package com.shawntime.api.order.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/4/6 16:30
 */
@Getter
@Setter
public class MallOrderIn {

    private int userId;

    private String userName;

    private int productId;

    private String productName;
}
