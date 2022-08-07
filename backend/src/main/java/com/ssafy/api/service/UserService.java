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

	//----------------------------------조다연 회원관련 시작
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
	boolean chDplByUserId(String userId);
	boolean chDplByName(String name);
	boolean chDplByEmail(String email);
	//----------------------------------조다연 회원관련 끝

	//----------------------------------차송희 마이페이지 시작
	List<FollowerRes> getFollowerListByUserId(String userId);
	List<FollowingRes> getFollowingListByUserId(String userId);

	void updateUser(User user, UserInfoFetchReq userInfo);
	void deleteUser(String userId);
	//----------------------------------차송희 마이페이지 끝


}

