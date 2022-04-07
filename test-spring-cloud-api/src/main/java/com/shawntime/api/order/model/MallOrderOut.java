package com.shawntime.api.order.model;

import com.shawntime.api.product.model.ProductOut;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mashaohua
 * @date 2022/3/24 14:12
 */
@Setter
@Getter
public class MallOrderOut {

    private int id;

    private int userId;

    private String userName;

    private int productId;

    private String productName;

    private ProductOut productOut;
}
