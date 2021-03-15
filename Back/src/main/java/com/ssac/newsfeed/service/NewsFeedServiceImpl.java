package com.ssac.newsfeed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssac.mappers.NewsFeedMapper;
import com.ssac.newsfeed.dto.NewsFeed;
import com.ssac.newsfeed.dto.NewsFeedComment;
import com.ssac.newsfeed.dto.NewsFeedLike;

@Service
public class NewsFeedServiceImpl implements NewsFeedService {
	@Autowired
	private NewsFeedMapper mapper;
	
	@Override
	public List<NewsFeed> listNewsFeedById(String userid) throws Exception {
		return mapper.selectNewsFeedByUserId(userid);
	}
	
	@Override
	public List<NewsFeed> listNewsFeed(String userid) throws Exception {
		return mapper.selectNewsFeed(userid);
	}
	
	@Override
	public int writeNewsFeed(NewsFeed newsfeed) throws Exception {
		return mapper.insertNewsFeed(newsfeed);
	}
	
	@Override
	public int modifyNewsFeed(NewsFeed newsfeed) throws Exception {
		return mapper.updateNewsFeed(newsfeed);
	}
	
	@Override
	public int removeNewsFeed(int no) throws Exception {
		return mapper.deleteNewsFeed(no);
	}
	
	@Override
	public List<NewsFeedLike> listNewsFeedLike(int no) throws Exception {
		return mapper.selectLikeId(no);
	}
	
	@Override
	public int countComment(int no) throws Exception {
		return mapper.selectCommentCount(no);
	}
	
	@Override
	public int writeNewsFeedLike(NewsFeedLike like) throws Exception {
		return mapper.insertLike(like);
	}
	
	@Override
	public int removeNewsFeedLike(NewsFeedLike like) throws Exception {
		return mapper.deleteLike(like);
	}
	
	@Override
	public List<NewsFeedComment> listNewsFeedComment(int no) throws Exception {
		return mapper.selectComment(no);
	}
	
	@Override
	public int writeNewsFeedComment(NewsFeedComment comment) throws Exception {
		return mapper.insertComment(comment);
	}
	
	@Override
	public int modifyNewsFeedComment(NewsFeedComment comment) throws Exception {
		return mapper.updateComment(comment);
	}
	
	@Override
	public int removeNewsFeedComment(int no) throws Exception {
		return mapper.deleteComment(no);
	}
	
	@Override
	public NewsFeed getNewsFeedByNo(int no) throws Exception {
		return mapper.selectNewsFeedByNo(no);
	}
}
