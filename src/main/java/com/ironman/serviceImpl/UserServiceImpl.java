package com.ironman.serviceImpl;

import com.ironman.beans.User;
import com.ironman.dao.UserDAO;
import com.ironman.mapper.UserMapper;
import com.ironman.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:44 2017/11/28 0028
 **/
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;


	public User getUserById(Long id){
		User user = null;
		try {
			user = userMapper.selectUserById(id);
			logger.info(user);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		return user;
	}

	public void insertUser(User user){
		userMapper.insertUser(user);
	}

	//	@Transactional
	public User testTrans(User user){
		User user1 = getUserById(user.getId());
//		insertUser(user);
		return user;
	}
}
