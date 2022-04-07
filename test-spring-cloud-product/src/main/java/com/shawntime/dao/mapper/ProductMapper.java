package com.shawntime.dao.mapper;

import com.shawntime.dao.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * @author mashaohua
 * @date 2022/4/6 10:59
 */
public interface ProductMapper {

    Product getProduct(@Param("productId") int productId);
}
