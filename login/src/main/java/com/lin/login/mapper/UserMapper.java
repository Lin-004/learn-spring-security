package com.lin.login.mapper;

import com.lin.login.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Mapper
* @createDate 2026-01-13 11:24:51
* @Entity com.lin.ssc.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




