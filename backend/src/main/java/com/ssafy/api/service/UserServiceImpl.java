package com.ssafy.api.service;
import com.ssafy.api.request.UserInfoFetchReq;
import com.ssafy.api.request.UserPasswordFetchReq;
import com.ssafy.api.response.FollowerRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.common.error.exception.custom.UserNotFoundException;
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

	//----------------------------------조다연 회원관련 시작
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
	//----------------------------------조다연 회원관련 끝

	//----------------------------------차송희 마이페이지 시작
	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public List<FollowerRes> getFollowerListByUserId(String userId) {
		List<Follower> followerList = userRepositorySupport.findFollowerListByUserId(userId);
		List<FollowerRes> res = new ArrayList<>();

		for (Follower follower : followerList) {
			res.add(FollowerRes.of(follower));
		}
		return res;
	}

	@Override
	public List<FollowingRes> getFollowingListByUserId(String userId) {
		List<Following> followingList = userRepositorySupport.findFollowingListByUserId(userId);
		List<FollowingRes> res = new ArrayList<>();

		for (Following following : followingList) {
			res.add(FollowingRes.of(following));
		}
		return res;
	}


	@Override
	public void updateUser(User user, UserInfoFetchReq userInfo) {
		User updateUser = userRepositorySupport.findUserByUserId(user.getUserId()).get();
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

	@Override
	public String updateAuthStatus(String email, String authKey) {
		User user = userRepositorySupport.findByEmail(email).get();

		if (user.getAuthKey().equals(authKey)) {
			user.setAuthStatus(true);
			userRepository.save(user);

			return "SUCCESS";

		} else {
			return "FAILED";
		}
	}

	@Override
	public void updatePassword(User user, UserPasswordFetchReq req) {
		if (!passwordEncoder.matches(req.getCurrent_password(), user.getPassword())) {
			throw new UserNotFoundException(user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(req.getNew_password()));
		userRepository.save(user);
		return;
	}

	@Override
	public void followerCnt(String followingId) {
		User user = userRepositorySupport.findUserByUserId(followingId).get();
		int current_follower_cnt = user.getFollower_cnt();
		user.setFollower_cnt(current_follower_cnt+1);
		userRepository.save(user);
	}

	@Override
	public void followingCnt(User user) {
		int current_following_cnt = user.getFollowing_cnt();
		user.setFollowing_cnt(current_following_cnt+1);
		userRepository.save(user);
	}

	@Override
	public void unfollowingCnt(User user) {
		int current_following_cnt = user.getFollowing_cnt();
		user.setFollowing_cnt(current_following_cnt-1);
		userRepository.save(user);
	}

	@Override
	public void unfollowerCnt(String unfollowingId) {
		User user = userRepositorySupport.findUserByUserId(unfollowingId).get();
		int current_follower_cnt = user.getFollower_cnt();
		user.setFollower_cnt(current_follower_cnt-1);
		userRepository.save(user);
	}
	//-----------차송희 마이페이지 끝

	@Override
	public void report(String reportUserId) {
		User user = userRepositorySupport.findUserByUserId(reportUserId).get();
		int current_report_cnt = user.getReport_cnt();
		user.setReport_cnt(current_report_cnt+1);
		userRepository.save(user);
	}
}
