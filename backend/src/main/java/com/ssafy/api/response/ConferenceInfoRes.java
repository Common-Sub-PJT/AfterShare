package com.ssafy.api.response;

import com.ssafy.db.entity.Conference;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("ConferenceInfoResponse")
public class ConferenceInfoRes {
    //화상회의방 번호
    long conference_id;
    //방장 ID
    String owner_id;
    //모두참여/관람자만
    boolean is_after;
    //스포가능/스포금지
    boolean is_spoiler;
    //방제목
    String title;
    //비밀방
    boolean is_secret;
    //비밀번호
    String password;
    //화상회의 시작시간
    LocalDateTime call_start_time;
    //------공연정보
    //시도
    String sido;
    //시군구
    String sigungu;
    //방인원제한
    int person_limit;
    //공연ID
    String mt20id;
    //공연명
    String prfnm;
    //장르명
    String genrenm;
    //공연일시
    String perform_day;

    public static ConferenceInfoRes of(Conference conference){
        ConferenceInfoRes res = new ConferenceInfoRes();

        res.setConference_id(conference.getConference_id());
        res.setOwner_id(conference.getOwner_id());
        res.set_after(conference.is_after());
        res.set_spoiler(conference.is_spoiler());
        res.setTitle(conference.getTitle());
        res.set_secret(conference.is_secret());
        res.setPassword(conference.getPassword());
        res.setCall_start_time(conference.getCall_start_time());
        res.setSido(conference.getSido());
        res.setSigungu(conference.getSigungu());
        res.setPerson_limit(conference.getPerson_limit());
        res.setMt20id(conference.getMt20id());
        res.setPrfnm(conference.getPrfnm());
        res.setGenrenm(conference.getGenrenm());
        res.setPerform_day(conference.getPerform_day());

        return res;
    }
}
