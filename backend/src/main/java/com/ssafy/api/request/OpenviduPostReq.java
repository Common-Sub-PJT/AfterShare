package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("OpenviduPostReq")
public class OpenviduPostReq {

    @ApiModelProperty(name="sessionName", example="session1")
    String sessionName;
}