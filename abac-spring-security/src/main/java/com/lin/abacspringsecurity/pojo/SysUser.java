package com.lin.abacspringsecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@TableName("sys_user")
public class SysUser implements UserDetails {

    @TableId(type = IdType.AUTO)
    private Long userId;

    @TableField("login_name")
    private String username; // Spring Security认证使用的字段

    private String password;

    private String status; // 状态（0正常 1锁定）

    private String delFlag; // 删除标志（0代表存在 1代表删除）

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private Map<String, String> attrs; // 用户的属性集合，用于 ABAC 动态权限评估

    // 实现UserDetails接口
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 组装 GrantedAuthority 集合，将角色和菜单权限都加入
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()))
                .collect(Collectors.toList()));

        authorities.addAll(roles.stream()
                .flatMap(role -> role.getMenus().stream())
                .map(menu -> new SimpleGrantedAuthority(menu.getPerms()))
                .collect(Collectors.toList()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return "0".equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return "0".equals(delFlag);
    }
}