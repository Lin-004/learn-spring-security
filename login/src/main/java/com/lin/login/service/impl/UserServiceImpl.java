//package com.lin.ssc.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lin.ssc.mapper.MenuMapper;
//import com.lin.ssc.mapper.RoleMapper;
//import com.lin.ssc.pojo.Menu;
//import com.lin.ssc.pojo.Role;
//import com.lin.ssc.pojo.User;
//import com.lin.ssc.service.UserService;
//import com.lin.ssc.mapper.UserMapper;
//import com.lin.ssc.utils.JwtUtil;
//import com.lin.ssc.utils.ResponseResult;
//import jakarta.annotation.Resource;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
//* @author Administrator
//* @description 针对表【user】的数据库操作Service实现
//* @createDate 2026-01-13 11:24:51
//*/
//@Service
//public class UserServiceImpl extends ServiceImpl<UserMapper, User>
//    implements UserDetailsService,UserService{
//
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private MenuMapper menuMapper;
//    @Resource
//    private RoleMapper roleMapper;
//    @Resource
//    private AuthenticationConfiguration authenticationConfiguration;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 根据用户名查询用户
//     * @param username
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
//        queryWrapper.eq(User::getUsername,username);
//        User user = userMapper.selectOne(queryWrapper);
//        if(user==null){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        //查询⽤户拥有的权限
////        List<Menu> menuList = menuMapper.selectByUserId(user.getId());
////        if (CollectionUtils.isEmpty(menuList)) {
////            throw new RuntimeException("出错了");
////        }
//
////        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
////        menuList.forEach(menu->{
////            String permission = menu.getPermission();
////            if (!StringUtils.isEmpty(permission)) {
////                grantedAuthorities.add(new SimpleGrantedAuthority(permission));
////            }
////        });
////
////
////
////        List<Role> roleList = roleMapper.selectByUserId(user.getId());
////        if (CollectionUtils.isEmpty(roleList)) {
////            throw new RuntimeException("出错了");
////        }
////        roleList.forEach(role->{
////            if (!StringUtils.isEmpty(role.getName())) {
////                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
////            }
////        });
//
//        return new org.springframework.security.core.userdetails.User(
//                username,user.getPassword(),true,true,true,
//                true,Collections.emptyList()
//        );
//    }
//
//    @Override
//    public ResponseResult login(String username, String password) throws Exception {
//        // 将客户端接收的 username 和 password 封装成UsernamePasswordAuthenticationToken
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        //获得认证管理器
//        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
//        //认证⽤户信息
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//
//        User user = (User)authenticate.getPrincipal();
//
//        String name = user.getUsername();
//        String token = JwtUtil.createJWT(name);
//
//        stringRedisTemplate.opsForValue().set(name,token);
//
//        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
//        return ResponseResult.success("登录成功,token:" + map);
//    }
//}