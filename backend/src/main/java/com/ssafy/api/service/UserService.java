package com.ssafy.api.service;

import com.ssafy.api.request.UserInfoFetchReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.FollowerRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);

	//송희 0725
	List<FollowerRes> getFollowerListByUserId(String userId);
	List<FollowingRes> getFollowingListByUserId(String userId);

	//송희
	void updateUser(String userId, UserInfoFetchReq userInfo);
	void deleteUser(String userId);

}
