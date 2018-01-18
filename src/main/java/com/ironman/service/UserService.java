package com.ironman.service;

import com.ironman.beans.User;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:43 2017/11/28 0028
 **/
public interface UserService {
	User getUserById(Long id);

	void insertUser(User user);

	User testTrans(User user);

}
