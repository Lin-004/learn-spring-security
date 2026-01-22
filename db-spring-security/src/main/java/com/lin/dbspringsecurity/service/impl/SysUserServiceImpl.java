package com.lin.dbspringsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.dbspringsecurity.pojo.SysUser;
import com.lin.dbspringsecurity.service.SysUserService;
import com.lin.dbspringsecurity.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2026-01-19 15:50:04
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




