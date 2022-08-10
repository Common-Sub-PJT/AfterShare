package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 수정 API ([POST] /api/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserInfoFetchReq")
public class UserInfoFetchReq {
    @ApiModelProperty(name="유저 Name", example="김잠송")
    String userName;
    @ApiModelProperty(name="유저 Email", example="yourEmail@naver.com")
    String userEmail;
    @ApiModelProperty(name="유저 profile_img")
    String profileImg;
    @ApiModelProperty(name="유저 about_me", example="안녕하세요 김잠송입니다.")
    String aboutMe;
}
