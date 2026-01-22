package com.lin.abacspringsecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_role")
public class SysRole {
 @TableId(type = IdType.AUTO)
 private Long roleId;
 private String roleName;
 private String roleKey;

 @TableField(exist = false)
 private List<SysMenu> menus;
}
