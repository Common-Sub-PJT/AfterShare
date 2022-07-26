package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

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
}
