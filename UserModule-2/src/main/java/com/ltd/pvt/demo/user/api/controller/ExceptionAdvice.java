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
import com.ltd.pvt.demo.user.api.exception.UserNotExistException;
import com.ltd.pvt.demo.user.api.exception.UserNotFoundException;
import com.ltd.pvt.demo.user.api.exception.UsernameAndPasswordNotMatchException;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;
/**
 *                 Handle the All of these Exception as below listed
 * UserNotFoundException.class
 * UsernameAndPasswordNotMatchException
 * UserAlreadyExistException
 * MethodArgumentNotValidException
 * Exception
 * 
 * */


@RestControllerAdvice
public class ExceptionAdvice {
	
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserError> mapException(UserNotFoundException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.NOT_FOUND.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.NOT_FOUND);
	}//end of method
	
	@ExceptionHandler(UsernameAndPasswordNotMatchException.class)
	public ResponseEntity<UserError> mapException(UsernameAndPasswordNotMatchException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.NOT_ACCEPTABLE);
	}//end of method
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<UserError> mapException(UserAlreadyExistException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.NOT_ACCEPTABLE);
	}//end of method
	
	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<UserError> mapException(UserNotExistException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.NOT_ACCEPTABLE);
	}//end of method
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<UserError> mapException(MethodArgumentNotValidException ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		
		//get defaultMessage and reason of corresponding code from BindingResult in list  of String
		
		List<String> listError=new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(arg->{
			String str="{ code = "+arg.getCode()+" : "+arg.getDefaultMessage()+" }";
			listError.add(str);
		});//end of forEach()
		
		//list  of Error message set im UserError
		UserError error=new UserError(HttpStatus.PRECONDITION_FAILED.value(),"  Validation Failed due to "+listError.toString());
		return new ResponseEntity<UserError>(error,HttpStatus.PRECONDITION_FAILED);
	}//end of method
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<UserError> mapException(Exception ex){
		log.error("ExceptionAdvice.mapException()   "+ex.getMessage());
		UserError error=new UserError(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<UserError>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}//end of method

}
