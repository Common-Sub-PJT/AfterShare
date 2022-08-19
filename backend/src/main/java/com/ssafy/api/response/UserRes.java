package com.ssafy.api.response;

import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/users/profile) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="user_serial")
	long user_serial;
	@ApiModelProperty(name="is_ban")
	boolean is_ban;
	@ApiModelProperty(name="is_staff")
	boolean is_staff;
	@ApiModelProperty(name="User ID")
	String userId;
	@ApiModelProperty(name="name")
	String name;
	@ApiModelProperty(name="email")
	String email;
	@ApiModelProperty(name="profile_img")
	String profile_img;
	@ApiModelProperty(name="following")
	int following_cnt;
	@ApiModelProperty(name="follower")
	int follower_cnt;
	@ApiModelProperty(name="aboutme")
	String about_me;
	@ApiModelProperty(name="social_login")
	int social_login;


	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUser_serial(user.getId());
		res.set_ban(user.is_ban());
		System.out.println("==="+user.is_ban());
		res.set_staff(user.is_staff());
		res.setUserId(user.getUserId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setProfile_img(user.getProfile_img());
		res.setFollowing_cnt(user.getFollowing_cnt());
		res.setFollower_cnt(user.getFollower_cnt());
		res.setAbout_me(user.getAbout_me());
		return res;
	}
}

