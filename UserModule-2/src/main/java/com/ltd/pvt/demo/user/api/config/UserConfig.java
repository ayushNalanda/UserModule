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
	
	

}
