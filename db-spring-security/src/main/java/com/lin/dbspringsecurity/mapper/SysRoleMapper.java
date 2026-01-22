package com.lin.dbspringsecurity.mapper;

import com.lin.dbspringsecurity.pojo.SysMenu;
import com.lin.dbspringsecurity.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2026-01-19 15:49:51
* @Entity com.lin.dbspringsecurity.pojo.SysRole
*/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT m.* FROM sys_menu m " +
            "JOIN sys_role_menu rm ON m.menu_id = rm.menu_id " +
            "WHERE rm.role_id = #{roleId}")
    List<SysMenu> selectMenusByRoleId(Long roleId);
}




