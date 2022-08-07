package com.ssafy.api.response;

import com.ssafy.db.entity.Conference;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceListResponse")
public class ConferenceRes {
    //제목
    String title;
    //공연명
    String prfnm;
    //스포금지/주의 여부
    boolean is_spoiler;

    public static ConferenceRes of(Conference conference){
        ConferenceRes res = new ConferenceRes();

        res.setTitle(conference.getTitle());
        res.setPrfnm(conference.getPrfnm());
        res.set_spoiler(conference.is_spoiler());

        return res;
    }
}
