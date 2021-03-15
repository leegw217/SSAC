package com.ssac.newsfeed.service;

import java.util.List;

import com.ssac.newsfeed.dto.NewsFeed;
import com.ssac.newsfeed.dto.NewsFeedComment;
import com.ssac.newsfeed.dto.NewsFeedLike;

public interface NewsFeedService {

	List<NewsFeed> listNewsFeedById(String userid) throws Exception;

	List<NewsFeed> listNewsFeed(String userid) throws Exception;

	int writeNewsFeed(NewsFeed newsfeed) throws Exception;

	int modifyNewsFeed(NewsFeed newsfeed) throws Exception;

	int removeNewsFeed(int no) throws Exception;

	List<NewsFeedLike> listNewsFeedLike(int no) throws Exception;

	int countComment(int no) throws Exception;

	int writeNewsFeedLike(NewsFeedLike like) throws Exception;

	int removeNewsFeedLike(NewsFeedLike like) throws Exception;

	List<NewsFeedComment> listNewsFeedComment(int no) throws Exception;

	int writeNewsFeedComment(NewsFeedComment comment) throws Exception;

	int modifyNewsFeedComment(NewsFeedComment comment) throws Exception;

	int removeNewsFeedComment(int no) throws Exception;

	NewsFeed getNewsFeedByNo(int no) throws Exception;

}