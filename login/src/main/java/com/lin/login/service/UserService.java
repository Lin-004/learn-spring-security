package com.lin.login.service;

import com.lin.login.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.login.utils.ResponseResult;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2026-01-13 11:24:51
*/
public interface UserService extends IService<User> {

    ResponseResult login(String username, String password) throws Exception;
}
