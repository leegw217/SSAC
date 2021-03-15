package com.ssac.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.user.dto.User;

@Mapper
public interface UserMapper {
	public User selectUser(User user) throws Exception;
	public int insertUser(User user) throws Exception;
	public int updateUserInformation(User user) throws Exception;
	public int deleteUser(User user) throws Exception;
	public List<String> selectFollowing(String userid) throws Exception;
	public List<String> selectFollower(String userid) throws Exception;
	public int updateUserPassword(HashMap<String, String> map) throws Exception;
	public int insertFollowing(HashMap<String, String> map) throws Exception;
	public List<User> searchUser(String search) throws Exception;
	public List<String> selectAllUser() throws Exception;
}
