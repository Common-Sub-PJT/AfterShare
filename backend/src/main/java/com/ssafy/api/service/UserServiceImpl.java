package com.ssafy.api.service;

import com.ssafy.api.request.UserInfoFetchReq;
import com.ssafy.api.response.FollowerRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.db.entity.Follower;
import com.ssafy.db.entity.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.ArrayList;
import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public List<FollowerRes> getFollowerListByUserId(String userId) {
		List<Follower> followerList = userRepositorySupport.findFollowerListByUserId(userId);
		System.out.println("userID : "+ userId);
		List<FollowerRes> res = new ArrayList<>();

		for (Follower follower : followerList) {
			res.add(FollowerRes.of(follower));
		}
		return res;
	}

	@Override
	public List<FollowingRes> getFollowingListByUserId(String userId) {
		List<Following> followingList = userRepositorySupport.findFollowingListByUserId(userId);
		System.out.println("userID : "+ userId);
		List<FollowingRes> res = new ArrayList<>();

		for (Following following : followingList) {
			res.add(FollowingRes.of(following));
		}
		return res;
	}


	@Override
	public void updateUser(String userId, UserInfoFetchReq userInfo) {
		User updateUser = userRepositorySupport.findUserByUserId(userId).get();
		updateUser.setName(userInfo.getUserName());
		updateUser.setEmail(userInfo.getUserEmail());
		updateUser.setProfile_img(userInfo.getProfileImg());
		updateUser.setAbout_me(userInfo.getAboutMe());
		userRepository.save(updateUser);
	}

	@Override
	public void deleteUser(String userId) {
		User deleteUser = userRepositorySupport.findUserByUserId(userId).get();
		userRepository.deleteById(deleteUser.getId());
	}


}
