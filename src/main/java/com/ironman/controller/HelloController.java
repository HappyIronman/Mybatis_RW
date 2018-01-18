package com.ironman.controller;

import com.ironman.beans.User;
import com.ironman.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:00 2017/11/28 0028
 **/
@Controller
public class HelloController {
	private static Logger logger = Logger.getLogger(HelloController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(String name, ModelMap modelMap){
		logger.info(name);
		modelMap.addAttribute("hello", name);
		return "index";
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable("id") Long userId){
		return userService.getUserById(userId);
	}

	@RequestMapping(value = "/user/insert/{username}", method = RequestMethod.GET)
	@ResponseBody
	public User insertUser(@PathVariable("username") String username){
		User user = new User();
		user.setUsername(username);
//		userService.insertUser(user);
		user = userService.testTrans(user);
		return user;
	}

}
