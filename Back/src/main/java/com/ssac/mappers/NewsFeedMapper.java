package com.ssac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.newsfeed.dto.NewsFeed;
import com.ssac.newsfeed.dto.NewsFeedComment;
import com.ssac.newsfeed.dto.NewsFeedLike;

@Mapper
public interface NewsFeedMapper {
	public List<NewsFeed> selectNewsFeedByUserId(String userid) throws Exception;
	public List<NewsFeed> selectNewsFeed(String userid) throws Exception;
	public int insertNewsFeed(NewsFeed newsfeed) throws Exception;
	public int updateNewsFeed(NewsFeed newsfeed) throws Exception;
	public int deleteNewsFeed(int no) throws Exception;
	public List<NewsFeedLike> selectLikeId(int no) throws Exception;
	public int selectCommentCount(int no) throws Exception;
	public int insertLike(NewsFeedLike like) throws Exception;
	public int deleteLike(NewsFeedLike like) throws Exception;
	public List<NewsFeedComment> selectComment(int no) throws Exception;
	public int insertComment(NewsFeedComment comment) throws Exception;
	public int updateComment(NewsFeedComment comment) throws Exception;
	public int deleteComment(int no) throws Exception;
	public NewsFeed selectNewsFeedByNo(int no) throws Exception;
}
