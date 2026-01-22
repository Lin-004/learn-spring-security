package com.lin.abacspringsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.abacspringsecurity.pojo.SysRole;
import com.lin.abacspringsecurity.pojo.SysUser;
import com.lin.abacspringsecurity.pojo.SysUserAttr;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2026-01-19 15:50:04
* @Entity com.lin.dbspringsecurity.pojo.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {


    //仅仅快速演示，实际项目中建议使用xml配置复杂的关联查询
    @Select("SELECT r.* FROM sys_role r " +
            "JOIN sys_user_role ur ON r.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "menus", column = "role_id",
                    many = @Many(select = "com.lin.abacspringsecurity.mapper.SysMenuMapper.selectByUserId"))
    })
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 获取用户属性
     * @param userId
     * @return
     */
    @Select("SELECT * FROM sys_user_attr WHERE user_id = #{userId}")
    List<SysUserAttr> selectUserAttrByUserId(Long userId);
}




