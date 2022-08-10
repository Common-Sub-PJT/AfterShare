package com.ssafy.api.response;

import com.ssafy.db.entity.Following;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FollowingResponse")
public class FollowingRes {
    @ApiModelProperty(name ="Following ID")
    String following_id;

    public static FollowingRes of(Following following) {
        FollowingRes res = new FollowingRes();

        res.setFollowing_id(following.getFollowing_id());

        return res;
    }
}

