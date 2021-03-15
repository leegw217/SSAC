package com.ssac.user.jwt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssac.user.jwt.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getMethod().contentEquals("OPTIONS")) {
			return true;
		}
		else {
			String token = request.getHeader("access-token");
			System.out.println("토큰 : "+token);
			if(token != null && token.length() > 0) {
				jwtService.checkValid(token);
				return true;
			}
			else {
				throw new RuntimeException("인증 토큰이 만료되었습니다.");
			}
		}
	}
}