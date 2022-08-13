package com.ssafy.api.response;

import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
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


	public static UserRes of(User user) {
		UserRes res = new UserRes();
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

