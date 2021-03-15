package com.ssac.user.service;

import java.util.HashMap;
import java.util.List;

import com.ssac.user.dto.User;

public interface UserService {
	// 회원등록
	int createUser(User user) throws Exception;
	// 회원찾기
	User findUser(User user) throws Exception;
	List<String> getFollowingList(String userid) throws Exception;
	List<String> getFollowerList(String userid) throws Exception;
	int modifyUserInformation(User user) throws Exception;
	int modifyUserPassword(HashMap<String, String> map) throws Exception;
	int removeUser(User user) throws Exception;
	int doFollowing(HashMap<String, String> map) throws Exception;
	List<User> getSearchUser(String search) throws Exception;
	List<String> getAllUser() throws Exception;
}