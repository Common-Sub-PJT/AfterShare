package com.ssafy.api.response;

import com.ssafy.db.entity.UserReport;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ApiModel("AdminReportListResponse")
public class AdminReportRes {
    //신고 번호
    long report_serial;
    //신고 사유
    String report_content;
    //신고당한 유저
    String user_id;
    //신고한 유저
    String report_user_id;
    //신고 날짜
    LocalDateTime latest_report_day;

    public static AdminReportRes of(UserReport userReport){
        AdminReportRes res = new AdminReportRes();

        res.setReport_serial(userReport.getReport_serial());
        res.setReport_content(userReport.getReport_content());
        res.setUser_id(userReport.getUser_id());
        res.setReport_user_id(userReport.getReport_user_id());
        res.setLatest_report_day(userReport.getLatest_report_day());

        return res;
    }
}
