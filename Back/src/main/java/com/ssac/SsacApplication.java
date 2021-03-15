package com.ssac;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.ssac.user.jwt.interceptor.JwtInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SsacApplication implements WebMvcConfigurer {
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	public static void main(String[] args) {
		SpringApplication.run(SsacApplication.class, args);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
				.excludePathPatterns(Arrays.asList("/**"));
	}
}
