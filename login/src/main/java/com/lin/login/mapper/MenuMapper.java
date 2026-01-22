package com.lin.login.mapper;

import com.lin.login.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2026-01-13 14:39:19
* @Entity com.lin.ssc.pojo.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectByUserId(@Param("userId") Long id);
}




