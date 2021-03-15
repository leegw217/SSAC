package com.ssac.user.jwt.service;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ssac.user.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	private String signature = "ForSignToken";

	public String create(User user) {
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setHeaderParam("typ", "JWT"); // 헤더 파라미터
		jwtBuilder.setSubject("loginToken") // 토큰 제목
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 토큰 유효기간 지정(30분)
				.claim("user", user).claim("greeting", user.getNickname() + "님 반갑습니다."); // 안내성 claim 담기
		jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());	// 시그니쳐를 이용해 암호화
		String jwt = jwtBuilder.compact();	// builder로 생성된 token -> String 직렬화
		return jwt;
	}

	public void checkValid(String jwt) {
		Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
	}
	

	public Map<String, Object> get(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
		} catch(final Exception e) {
			throw new RuntimeException();
		}
		
		return claims.getBody();
	}
}