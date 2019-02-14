package com.ltd.pvt.demo.user.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ltd.pvt.demo.user.api.dto.UserError;
import com.ltd.pvt.demo.user.api.exception.UserAlreadyExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotFoundException;
import com.ltd.pvt.demo.user.api.exception.UsernameAndPasswordNotMatchException;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

@RestControllerAdvice
public class ExceptionAdvice {
	
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserError> mapException(UserNotFoundException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UsernameAndPasswordNotMatchException.class)
	public ResponseEntity<UserError> mapException(UsernameAndPasswordNotMatchException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<UserError> mapException(UserAlreadyExistException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<UserError> mapException(MethodArgumentNotValidException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		BindingResult result=ex.getBindingResult();
		
		
		List<String> listError=new ArrayList<>();
		result.getAllErrors().forEach(arg->{
			String str="{ code = "+arg.getCode()+" : "+arg.getDefaultMessage()+" }";
			listError.add(str);
		});
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"  Validation Failed due to "+listError.toString());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<UserError> mapException(Exception ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
