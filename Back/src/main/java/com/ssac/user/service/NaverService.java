package com.ssac.user.service;

import com.ssac.user.dto.NaverProfile;
import com.ssac.user.dto.NaverToken;

public interface NaverService {

	NaverToken getToken(String code) throws Exception;

	NaverProfile getProfile(NaverToken naverToken) throws Exception;

	NaverToken refreshToken(String token) throws Exception;

	String deleteToken(String token) throws Exception;

}