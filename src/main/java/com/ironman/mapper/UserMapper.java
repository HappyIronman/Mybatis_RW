package com.ironman.mapper;

import com.ironman.beans.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:35 2017/11/28 0028
 **/
@Repository
public interface UserMapper {
	User selectUserById(Long id);

	void insertUser(User user);
}
