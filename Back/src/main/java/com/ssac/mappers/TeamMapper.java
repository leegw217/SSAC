package com.ssac.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.team.dto.MyTeam;
import com.ssac.team.dto.Team;

@Mapper
public interface TeamMapper {
	public List<Team> selectTeam() throws Exception;
	public Team selectTeamByNo(int no) throws Exception;
	public List<MyTeam> selectMyTeam(String userid) throws Exception;
	public int insertMyTeam(MyTeam myteam) throws Exception;
	public int updateMyTeamName(MyTeam myteam) throws Exception;
	public int deleteMyTeam(MyTeam myteam) throws Exception;
	public int updateTeamCount(HashMap<String, Integer> map) throws Exception;
	public List<Team> selectNewTeam(String userid) throws Exception;
	public List<Team> selectSearchTeam(HashMap<String, String> map) throws Exception;
	public List<HashMap<String, Object>> selectFriendTeams(String userid) throws Exception;
}
