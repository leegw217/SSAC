package com.ssac.user.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssac.user.dto.KakaoProfile;
import com.ssac.user.dto.KakaoToken;
import com.ssac.user.dto.NaverProfile;
import com.ssac.user.dto.NaverToken;
import com.ssac.user.dto.User;
import com.ssac.user.jwt.service.JwtService;
import com.ssac.user.service.NaverService;
import com.ssac.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/social")
public class SocialLoginController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private NaverService naverService;
	@Value("${naverlogin.key}")
	private String naver_pw;
	@Value("${kakaologin.key}")
	private String kakao_pw;

	@ApiOperation(value = "네이버 로그인", notes = "네이버 로그인 창 주소를 리턴한다. 해당 주소로 새창을 띄워서 로그인진행")
	@GetMapping("/naver/call")
	@ResponseBody
	public String naverCall() {
		SecureRandom random = new SecureRandom();
		String status = new BigInteger(130, random).toString(32);
		String url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=MPvkgO4ohChxb_eiLLvU&redirect_uri=http://localhost:9000/ssac/social/naver/callback&state=";
		url += status;
		return url;
	}

	
	@ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 창 주소를 리턴한다. 해당 주소로 새창을 띄워서 로그인진행")
	@GetMapping("/kakao/call")
	@ResponseBody
	public String kakaoCallI() {
		String url = "https://kauth.kakao.com/oauth/authorize?client_id=240f3a7ec31b62f784b30b6dfbd4ae2f&redirect_uri=http://localhost:9000/ssac/social/kakao/callback&response_type=code";
		return url;
	}
	
	@ApiOperation(value = "네이버 로그인 콜백", notes = "신경 안써도 됨")
	@GetMapping("/naver/callback")
	public ResponseEntity<?> naverCallback(String code, HttpSession session) {
		NaverToken naverToken = null;
		try {
			naverToken = naverService.getToken(code);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("네이버 토큰을 받아오는 중 문제가 발생");
		}
		session.setAttribute("naverRefreshToken", naverToken.getRefresh_token());
		NaverProfile naverProfile = null;
		try {
			naverProfile = naverService.getProfile(naverToken);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("네이버 회원정보를 받아오는 중 문제가 발생");
		}
		// 회원가입
		String naverId = naverProfile.getResponse().getId() + "_" + naverProfile.getResponse().getName();
		try {
			User check = userService.findUser(new User(naverId));
			User user = new User();
			user.setId(naverId);
			user.setPw(naver_pw);
			user.setNickname(naverProfile.getResponse().getName());
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", user.getId());
			resultMap.put("nickname", user.getNickname());
			// 로그인한적이 없으면 회원가입
			if (check == null) {
				// MyTeam창으로 이동
				resultMap.put("message", "singup");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} else {
				// 메인창으로 이동
				String token = jwtService.create(user);
				System.out.println("토큰생성 완료");
				resultMap.put("access-token", token);
				resultMap.put("message", "login");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 찾기 오류");
		}
		return null;
	}

	@ApiOperation(value = "카카오 로그인 콜백", notes = "신경 안써도 됨")
	@GetMapping("/kakao/callback")
	public ResponseEntity<?> kakaoCallback(String code, HttpSession session) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "240f3a7ec31b62f784b30b6dfbd4ae2f");
		params.add("redirect_uri", "http://localhost:9000/ssac/social/kakao/callback");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		KakaoToken kakaoToken = new KakaoToken();
		try {
			kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + kakaoToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = new KakaoProfile();
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 회원가입
		String kakaoId = kakaoProfile.getId()+"_kakao";
		try {
			User check = userService.findUser(new User(kakaoId));
			User user = new User();
			user.setId(kakaoId);
			user.setPw(kakao_pw);
			user.setNickname(kakaoProfile.getProperties().getNickname());
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", user.getId());
			resultMap.put("nickname", user.getNickname());
			// 로그인한적이 없으면 회원가입
			if (check == null) {
				// MyTeam창으로 이동
				resultMap.put("message", "singup");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} else {
				// 메인창으로 이동
				String token = jwtService.create(user);
				System.out.println("토큰생성 완료");
				resultMap.put("access-token", token);
				resultMap.put("message", "login");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 찾기 오류");
		}
		return null;
	}
}
