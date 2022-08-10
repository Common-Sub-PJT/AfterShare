package com.ssafy.api.response;

import com.ssafy.db.entity.Conference;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    //시작 시간
//    LocalDateTime call_start_time;
    // 공연 일시
    String perform_day;
    //방장ID
    String owner_id;

    public static ConferenceRes of(Conference conference){
        ConferenceRes res = new ConferenceRes();

        res.setTitle(conference.getTitle());
        res.setPrfnm(conference.getPrfnm());
        res.set_spoiler(conference.is_spoiler());
//        res.setCall_start_time(conference.getCall_start_time());
        res.setPerform_day(conference.getPerform_day());
        res.setOwner_id(conference.getOwner_id());

        return res;
    }
}
