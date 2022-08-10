package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 비밀번호 변경 API ([PATCH] /api/change-password) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("UserPasswordFetchReq")
public class UserPasswordFetchReq {
    @ApiModelProperty(name = "현재 비밀번호", example = "test")
    String current_password;

    @ApiModelProperty(name = "변경할 비밀번호", example = "change_password")
    String new_password;
}
