package com.ironman.dao;

import com.ironman.beans.User;
import com.ironman.conf.DBContextHolder;
import com.ironman.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 12:11 2017/11/28 0028
 **/
@Repository
public class UserDAO {
	@Autowired
	UserMapper userMapper;

	public User selectUserById(Long id){

		//根据具体需要设置不同的数据库
		DBContextHolder.setDbType(DBContextHolder.DB_MASTER);
		//DBContextHolder.setDbType(DBContextHolder.DB_SLAVE);
		return userMapper.selectUserById(id);
	}
}
