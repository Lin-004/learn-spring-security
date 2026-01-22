package com.lin.dbspringsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.dbspringsecurity.mapper.SysRoleMapper;
import com.lin.dbspringsecurity.mapper.SysUserMapper;
import com.lin.dbspringsecurity.pojo.SysRole;
import com.lin.dbspringsecurity.pojo.SysUser;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户则抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //查询用户权限信息
        List<SysRole> roles = userMapper.selectRolesByUserId(user.getUserId());
        roles.forEach(role -> {
            roleMapper.selectMenusByRoleId(role.getRoleId());
        });

        roles.forEach(role -> {
            role.setMenus(roleMapper.selectMenusByRoleId(role.getRoleId()));
        });
        user.setRoles(roles);

        if (!user.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        }
        return user;
    }
}
