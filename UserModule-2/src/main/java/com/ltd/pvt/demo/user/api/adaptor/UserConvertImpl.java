package com.ltd.pvt.demo.user.api.adaptor;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.model.User;

/**
 * 
 *      Convert the POJO to POJO with Adapter method develop as below listed
 *      public User convertDtoToModel(UserSignup dto)
 *      public User convertSigninToModel(UserSignin dto)
 *      public UserDetail convertModelToUserDetail(User model)
 *     
 * 
 * 
 * */

@Component
public class UserConvertImpl implements UserAdaptor {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserConvertImpl.class);


	@Override
	public User convertSignUp(UserSignup dto) {
		User model=null;
		log.debug("UserConfig.convertDtoToModel()");
		model=new User();
		model.setFirstName(dto.getFirsrname());
		model.setLastName(dto.getLastname());
		model.setUsername(dto.getUsername());
		model.setContact(new BigInteger(dto.getContact()));
		model.setAddress(dto.getAddress());
		model.setPassword(dto.getPassword());
		return model;
	}

	@Override
	public User convertSignin(UserSignin dto) {
		User model=null;
		log.debug("UserConfig.convertSigninToModel()");
		model=new User();
		model.setUsername(dto.getUsername());
		model.setPassword(dto.getPassword());
		return model;
	}

	@Override
	public UserDetail convertModel(User model) {
		UserDetail  dto =null;
		log.debug("UserConfig.convertModelToUserDetail()");
		dto=new UserDetail();
		dto.setFirstname(model.getFirstName());
		dto.setLastname(model.getLastName());
		dto.setUsername(model.getUsername());
		dto.setAddress(model.getAddress());
		dto.setContact(model.getContact().toString());
		return dto;
	}

}
