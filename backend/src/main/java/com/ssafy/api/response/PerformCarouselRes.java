package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PerformCarouselRes")
public class PerformCarouselRes {
    //공연 ID
    String mt20id;
    //포스터 경로
    String poster;
    //순위
    int rnum;

    public static PerformCarouselRes of(String mt20id, String poster, int rnum) {
        PerformCarouselRes res = new PerformCarouselRes();
        res.setMt20id(mt20id);
        res.setPoster(poster);
        res.setRnum(rnum);
        return res;
    }
}
