package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ApiModel("AdminStopUserListResponse")
public class AdminStopUserRes {
    //신고당한 유저 아이디
    String user_id;
    //신고된 유저 네임
    String name;
    //신고된 횟수
    int report_cnt;
    //유저의 정지 여부
    Boolean is_ban;
    //마지막 신고 날짜
    LocalDateTime latest_report_day;

    public static AdminStopUserRes of(AdminStopUserRes stopRes){
        AdminStopUserRes res = new AdminStopUserRes();

        res.setUser_id(stopRes.getUser_id());
        res.setName(stopRes.getName());
        res.setLatest_report_day(stopRes.getLatest_report_day());
        res.setIs_ban(stopRes.getIs_ban());
        res.setReport_cnt(stopRes.getReport_cnt());

        return res;
    }
}
