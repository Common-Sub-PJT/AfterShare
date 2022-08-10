package com.ssafy.api.response;

import com.ssafy.db.entity.Follower;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FollowerResponse")
public class FollowerRes {
    @ApiModelProperty(name ="Follower ID")
    String follower_id;

    public static FollowerRes of(Follower follower) {
        FollowerRes res = new FollowerRes();

        res.setFollower_id(follower.getFollower_id());

        return res;
    }
}
