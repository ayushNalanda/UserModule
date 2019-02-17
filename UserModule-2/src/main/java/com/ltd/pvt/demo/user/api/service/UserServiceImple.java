package com.ltd.pvt.demo.user.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.pvt.demo.user.api.adaptor.UserAdaptor;
import com.ltd.pvt.demo.user.api.config.UserConfig;
import com.ltd.pvt.demo.user.api.dao.UserRepository;
import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.exception.UserAlreadyExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotFoundException;
import com.ltd.pvt.demo.user.api.exception.UsernameAndPasswordNotMatchException;
import com.ltd.pvt.demo.user.api.model.User;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * 
 * Implement the All Method of UserService as below listed
 * 
 *   public UserDetail searchUser(String userName) throws UserNotFoundException
 *   public String signup(UserSignup dto) throws UserAlreadyExistException
 *   public String signin(UserSignin signin) throws UsernameAndPasswordNotMatchException
 *   public List<String> fetchAllUsername() throws UserNotFoundException
 * 
 * 
 * 
 * */
@Service
@Transactional
public class UserServiceImple implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImple.class);

	@Autowired(required=true)
	private UserRepository repository;
	@Autowired
	private UserAdaptor adaptor;
	
	private Predicate<User> p = user -> user==null;
	@Autowired
	HttpServletRequest request;
	@Autowired
    private LoginAttemptService loginAttemptService;
	
	public UserDetail searchUser(String userName) throws UserNotFoundException {
		User model=null;
		 log.debug("UserService.searchUser()");
		try {
			
			//find the user
		model=repository.findByUsername(userName);
		//model is null or not
		if(!p.test(model))
		           return adaptor.convertModel(model);
		}
		catch (Exception e){
			log.warn("UserService.searchUser()  "+e.getMessage());
			throw new RuntimeException(" Internal Server Problem   "+e.getMessage());
		}//end of try & catch
		
		//model not found throw UserNotFoundException
		throw new UserNotFoundException("    User Not Found    "+userName);
	}//end of method
	
	public String signup(UserSignup dto) throws UserAlreadyExistException {
		User isUser=null;
		log.debug("UserService.signup()");
		
	     try {
		//check the isUser found or not
		 isUser=repository.findByUsername(dto.getUsername());
		 //not found then save the data
		 if(p.test(isUser)) {
			repository.save(adaptor.convertSignUp(dto));
			return " SignUp Scussful";
		 }//end of if
	     }
	     catch (Exception e) {
	    	 log.warn("UserServiceImple.signup()");
	    	 throw new RuntimeException(" Internal Server Problem   "+e.getMessage());
		}
		 
		 //User found so throw the UserAlreadyExistException
		 log.warn("UserService.signup()");
		throw new UserAlreadyExistException("    User Already Exist   "+dto.getUsername());
	}//end of method
	
	public String signin(UserSignin signin) throws Exception  {
		User isUser=null,model=null;
		String ip=null;
		boolean match=false;
		log.debug("UserService.signin()");
		try {
			
			 ip = getClientIP();
	        if (loginAttemptService.isBlocked(ip)) {
	            throw new RuntimeException("You are  blocked ");
	        }
			//convert dto to model
			model=adaptor.convertSignin(signin);
			
			//unreference the obj
			signin=null;
			//ch
		    isUser=repository.findByUsername(model.getUsername());
		    
			match=isUser.getPassword().equals(model.getPassword());
			if(match)
			return "  NEXT  PAGE     ";
			}
		catch (NullPointerException e) {
			log.warn("UserService.signin()"+e.getMessage());
		throw new UserNotExistException("User Not ExisT  Please SignUP  "+"  "+model.getUsername());
		}
		catch (Exception e) {
			log.warn("  UserService.signin()   "+e.getMessage());
		 throw new RuntimeException(" Internal Server Problem   "+e.getMessage());
		}//end of try & catch
		log.warn("UserService.signin()");
		throw new UsernameAndPasswordNotMatchException("Username or Password Not Match  "+model.getUsername());
	}//end of method

	public List<String> fetchAllUsername() throws UserNotFoundException {
		// TODO Auto-generated method stub
		
		log.debug("UserService.fetchAllUsername()");
		try {
			
			Function<User, String> f= user->user.getUsername();
		   return repository.findAll().stream().map(f).collect(Collectors.toList());
		}
		catch (NullPointerException e) {
			log.warn("   UserService.fetchAllUsername()  "+e.getMessage());
			throw new UserNotFoundException(" Record Not Available  ");
		}
		catch (Exception e) {
			log.warn("  UserService.fetchAllUsername()   "+e.getMessage());
		 throw new RuntimeException(" Internal Server Problem   "+e.getMessage());
		}//end of try & catch
	}//end of method
	
	
	 private String getClientIP() {
	        String xfHeader = request.getHeader("X-Forwarded-For");
	        if (xfHeader == null) {
	            return request.getRemoteAddr();
	        }
	        return xfHeader.split(",")[0];
	    }
	

}//end of class
