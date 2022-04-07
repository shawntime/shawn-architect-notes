package com.shawntime.dao.mapper;

import com.shawntime.dao.entity.MallUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author mashaohua
 * @date 2022/4/6 16:49
 */
public interface MallUserMapper {

    int saveUser(@Param("user") MallUser mallUser);

    MallUser getMallUser(@Param("userId") int userId);
}
