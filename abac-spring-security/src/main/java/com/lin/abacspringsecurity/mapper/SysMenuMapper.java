package com.lin.abacspringsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.abacspringsecurity.pojo.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2026-01-19 15:49:30
* @Entity com.lin.dbspringsecurity.pojo.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    @Select("SELECT DISTINCT m.* FROM sys_menu m " +
            "JOIN sys_role_menu rm ON m.menu_id = rm.menu_id " +
            "JOIN sys_user_role ur ON rm.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<SysMenu> selectByUserId(Long userId);
}




