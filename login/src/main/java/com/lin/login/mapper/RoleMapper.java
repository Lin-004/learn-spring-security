package com.lin.login.mapper;

import com.lin.login.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Mapper
* @createDate 2026-01-13 15:18:39
* @Entity com.lin.ssc.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(@Param("userId") Long id);
}




