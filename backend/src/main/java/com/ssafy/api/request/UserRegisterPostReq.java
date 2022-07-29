package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 * UserRegisterRequestDto
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	//입력해서 넘기는 request body = id, nickname, email, password
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String userId;
	@ApiModelProperty(name="유저 Name", example="kim_ssafy")
	String name;
	@ApiModelProperty(name="유저 Email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
}
