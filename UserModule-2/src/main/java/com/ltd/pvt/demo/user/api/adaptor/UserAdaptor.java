package com.ltd.pvt.demo.user.api.adaptor;

import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.model.User;


public interface UserAdaptor {
	
	  User        convertSignUp(UserSignup dto);
	  User        convertSignin(UserSignin dto);
	 UserDetail   convertModel(User model);

}
