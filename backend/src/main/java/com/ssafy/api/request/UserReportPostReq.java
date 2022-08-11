package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserReportPostReq")
public class UserReportPostReq {
    @ApiModelProperty(name="신고 하려는 아이디", example="ssafy_web")
    String reportUserId;

    @ApiModelProperty(name="신고 내용", example="욕설로 인한 신고")
    String reportContent;
}
