package com.ltd.pvt.demo.user.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.exception.UserAlreadyExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotFoundException;
import com.ltd.pvt.demo.user.api.exception.UsernameAndPasswordNotMatchException;
import com.ltd.pvt.demo.user.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@Api(value="User Service")
public class UserController {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired(required=true)
	private UserService userService;

	@GetMapping("/get/{username}")
	@ApiOperation(value="User Details by username")
	public UserDetail getUser(@ApiParam(format="Usermail",required=true) @PathVariable  String username) throws UserNotFoundException {
		log.debug("UserController.getUser()");
		if(!username.matches(EMAIL_PATTERN)) 
		     throw new RuntimeException("InValid Username Format as Email { username : "+username+"  }");
		return userService.searchUser(username);	
	}
	@ApiResponse(message="String",code=1)
	@PostMapping("/signup")
	@ApiOperation(value="signup")
	public  String signUp(@ApiParam @Valid @RequestBody UserSignup user) throws UserAlreadyExistException {
		log.debug("UserController.signUp()");
		 return userService.signup(user);	
	}
	
	@PostMapping("/signin")
	@ApiOperation(value="signin")
	public String signin(@Valid @RequestBody UserSignin signin) throws UsernameAndPasswordNotMatchException {
		log.debug("UserController.signin() ");
		 return userService.signin(signin);	
	}
	
	@GetMapping("/getAllUserName")
	@ApiOperation(value="AllUserName")
	public List<String> getAllUsername() throws UserNotFoundException {
		log.debug("UserController. getAllUsername()");
		 return userService.fetchAllUsername();	
	}
}
