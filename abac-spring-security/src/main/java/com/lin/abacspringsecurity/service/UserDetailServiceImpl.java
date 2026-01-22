package com.lin.abacspringsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.abacspringsecurity.mapper.SysRoleMapper;
import com.lin.abacspringsecurity.mapper.SysUserMapper;
import com.lin.abacspringsecurity.pojo.SysMenu;
import com.lin.abacspringsecurity.pojo.SysRole;
import com.lin.abacspringsecurity.pojo.SysUser;
import com.lin.abacspringsecurity.pojo.SysUserAttr;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查用户
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        //查角色
        List<SysRole> sysRoles = userMapper.selectRolesByUserId(user.getUserId());
        sysRoles.forEach(role -> {
            //查权限
            List<SysMenu> sysMenus = roleMapper.selectMenusByRoleId(role.getRoleId());
            role.setMenus(sysMenus);
        });
        user.setRoles(sysRoles);

        //查属性
        List<SysUserAttr> sysUserAttrs = userMapper.selectUserAttrByUserId(user.getUserId());
        if (sysUserAttrs != null && !sysUserAttrs.isEmpty()) {
            // 转换为 Map<String, String>
            user.setAttrs(sysUserAttrs.stream()
                    .collect(Collectors.toMap(s -> s.getAttrKey(), s -> s.getAttrValue())));
        }
        return user;
    }
}
