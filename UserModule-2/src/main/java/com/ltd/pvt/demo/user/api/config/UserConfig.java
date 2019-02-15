package com.ltd.pvt.demo.user.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ltd.pvt.demo.user.api.dto.UserDetail;
import com.ltd.pvt.demo.user.api.dto.UserSignin;
import com.ltd.pvt.demo.user.api.dto.UserSignup;
import com.ltd.pvt.demo.user.api.model.User;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * 1   Integrate the  Swagger Documentation
 * 
 * 2   Convert the POJO to POJO with Adapter method develop as below listed
 *      public User convertDtoToModel(UserSignup dto)
 *      public User convertSigninToModel(UserSignin signin)
 *      public UserDetail convertModelToUserDetail(User model)
 *      public UserSignup convertModelToDto(User model)
 * 
 * 
 * 
 * 
 * */
@Configuration
@EnableSwagger2
public class UserConfig {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserConfig.class);

	@Bean
	public Docket postsApi() {
		log.warn("UserConfig.postsApi()");
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ltd.pvt.demo.user.api.controller"))
				.paths(regex("/user.*"))
				.build();
	}
	
	private ApiInfo apiInfo() {
		log.info("UserConfig.apiInfo()");
		return new ApiInfoBuilder().
				title("User Service")
				.description("\"Spring Boot REST API for UserModule Application\"")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact("    aaush0101@gmail.com     "
                +"9121772537       "
                +"https://github.com/ayushNalanda/UserModule     ")
                .build();
	}
	
	public User convertDtoToModel(UserSignup dto) {
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
	
	public UserSignup convertModelToDto(User model) {
		UserSignup dto=null;
		log.debug("UserConfig.convertModelToDto()");
		dto=new UserSignup();
		dto.setUsername(model.getUsername());
		dto.setFirsrname(model.getUsername());
		dto.setLastname(model.getLastName());
		dto.setContact(model.getContact().toString());
		dto.setAddress(model.getAddress());
		dto.setPassword("***********");
		return dto;
	}
	
	public User convertSigninToModel(UserSignin signin) {
		User model=null;
		log.debug("UserConfig.convertSigninToModel()");
		model=new User();
		model.setUsername(signin.getUsername());
		model.setPassword(signin.getPassword());
		return model;
	}
	
	public UserDetail convertModelToUserDetail(User model) {
		UserDetail  detail =null;
		log.debug("UserConfig.convertModelToUserDetail()");
		detail=new UserDetail();
		detail.setFirstname(model.getFirstName());
		detail.setLastname(model.getLastName());
		detail.setUsername(model.getUsername());
		detail.setAddress(model.getAddress());
		detail.setContact(model.getContact().toString());
		return detail;
	}

}
