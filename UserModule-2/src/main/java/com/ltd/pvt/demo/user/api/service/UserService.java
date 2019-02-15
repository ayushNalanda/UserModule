package com.ltd.pvt.demo.user.api.service;

import java.util.List;

import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.exception.UserAlreadyExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotFoundException;
import com.ltd.pvt.demo.user.api.exception.UsernameAndPasswordNotMatchException;




/**
 * This is UserService Interface
 * 
 * 
 * */

public interface UserService {
	
	
	 public UserDetail searchUser(String userName) throws UserNotFoundException;
	 public String signup(UserSignup dto) throws UserAlreadyExistException;
	 public String signin(UserSignin signin) throws UsernameAndPasswordNotMatchException;
	 public List<String> fetchAllUsername() throws UserNotFoundException;

}
