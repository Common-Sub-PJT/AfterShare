package com.ssafy.api.response;

import com.ssafy.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("OpenviduPostRes")
public class OpenviduPostRes extends BaseResponseBody{
    @ApiModelProperty(name="openvidu 생성 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String token;

    public static OpenviduPostRes of(Integer statusCode, String message, String Token) {
        OpenviduPostRes res = new OpenviduPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setToken(Token);
        return res;
    }
}
