package com.shawntime.dao.mapper;

import java.util.List;

import com.shawntime.dao.entity.MallOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @author mashaohua
 * @date 2022/3/24 14:12
 */
public interface MallOrderMapper {

    List<MallOrder> getMallOrderList(@Param("userId") int userId);
}
