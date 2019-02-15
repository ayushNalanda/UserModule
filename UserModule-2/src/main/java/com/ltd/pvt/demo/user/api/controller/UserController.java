package com.ltd.pvt.demo.user.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


/**
 * Implement the these API as below of list
 * 
 * public UserDetail getUser( @PathVariable  String username) throws UserNotFoundException
 * public  ResponseEntity signUp( @Valid @RequestBody UserSignup user) throws UserAlreadyExistException
 * public ResponseEntity signin(@Valid @RequestBody UserSignin signin) throws UsernameAndPasswordNotMatchException
 * public List<String> getAllUsername() throws UserNotFoundException
 * 
 * 
 * */


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
	@ApiOperation(value=" Get User Details by username")
	public UserDetail getUser(@ApiParam(format="Usermail",required=true) @PathVariable  String username) throws UserNotFoundException {
		
		log.debug("UserController.getUser()");
		//check pathvariable as email
		if(!username.matches(EMAIL_PATTERN)) 
		     throw new RuntimeException("InValid Username Format as Email { username : "+username+"  }");
		return userService.searchUser(username);	
	}//end of method
	
	
	
	@ApiResponse(message="String",code=1)
	@PostMapping("/signup")
	@ApiOperation(value=" Signup")
	public  ResponseEntity signUp(@ApiParam @Valid @RequestBody UserSignup user) throws UserAlreadyExistException {
		log.debug("UserController.signUp()");
		// return success message
		
		 return new ResponseEntity( userService.signup(user), HttpStatus.CREATED);	
	}//end of method
	
	@PostMapping("/signin")
	@ApiOperation(value="Signin")
	public ResponseEntity signin(@Valid @RequestBody UserSignin signin) throws UsernameAndPasswordNotMatchException {
		log.debug("UserController.signin() ");
		// return success message
		 return new ResponseEntity( userService.signin(signin), HttpStatus.ACCEPTED);
	}//end of method
	
	@GetMapping("/getAllUserName")
	@ApiOperation(value="AllUserName")
	@ApiResponses(value = {
			
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),

	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),

	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	        })
	
	public List<String> getAllUsername() throws UserNotFoundException {
		log.debug("UserController. getAllUsername()");
		//return the list of username
		 return userService.fetchAllUsername();	
	}//end of method
}//end of class
