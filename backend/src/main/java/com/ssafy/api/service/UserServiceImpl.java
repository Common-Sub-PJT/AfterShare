package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

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

		//userRegisterPostReq에 담긴 정보들로 설정
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setEmail(userRegisterInfo.getEmail());
		user.setName(userRegisterInfo.getName());

		//프로필 이미지를 기본 이미지로 설정할 때 기본 이미지의 경로를 어떻게 ? ~~

		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public boolean chDplByUserId(String userId) {
		// 유저 정보가 존재하면
		//isPresent => Optional에서 반환값 없음 != null
		if(userRepositorySupport.findUserByUserId(userId).isPresent())
			return true;
		else return false;
	}

	@Override
	public boolean chDplByName(String name) {
		// 유저의 닉네임이 존재하면
		if(userRepositorySupport.findByName(name).isPresent())
			return true;
		else return false;
	}

	@Override
	public boolean chDplByEmail(String email) {
		// 유저의 이메일이 존재하면
		if(userRepositorySupport.findByEmail(email).isPresent())
			return true;
		else return false;
	}


}
