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
    //화상회의방 번호
    long conference_id;
    //제목
    String title;
    //공연명
    String prfnm;
    //스포금지/주의 여부
    boolean is_spoiler;
    // 공연 일시
    String perform_day;
    //방장ID
    String owner_id;
    //비밀번호 여부
    boolean is_secret;
    //비밀번호
    String password;

    public static ConferenceRes of(Conference conference){
        ConferenceRes res = new ConferenceRes();

        res.setConference_id(conference.getConference_id());
        res.setTitle(conference.getTitle());
        res.setPrfnm(conference.getPrfnm());
        res.set_spoiler(conference.is_spoiler());
        res.setPerform_day(conference.getPerform_day());
        res.setOwner_id(conference.getOwner_id());
        res.set_secret(conference.is_secret());
        res.setPassword(conference.getPassword());

        return res;
    }
}
