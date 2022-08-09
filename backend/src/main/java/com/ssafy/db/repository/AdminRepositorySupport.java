package com.ssafy.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.AdminStopUserRes;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.QUserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUserReport qUserReport = QUserReport.userReport;
    QUser qUser = QUser.user;

    public List<AdminStopUserRes> findAllGroupByUserSerial() {
        List<AdminStopUserRes> userReports= jpaQueryFactory.select(Projections.fields (AdminStopUserRes.class,
                        qUserReport.user_id.as("user_id"),
                        qUser.name.as("name"),
                        qUser.report_cnt.as("report_cnt"),
                        qUser.is_ban.as("is_ban"),
                        qUserReport.latest_report_day.max().as("latest_report_day")))
                .from(qUserReport, qUser)
                .where(qUserReport.user_id.eq(qUser.userId),
                        qUser.is_ban.eq(true))
                .groupBy(qUserReport.user_id)
                .fetch();

        return userReports;
    }

    public List<AdminStopUserRes> findByName(String name) {
        List<AdminStopUserRes> userReports= jpaQueryFactory.select(Projections.fields (AdminStopUserRes.class,
                        qUserReport.user_id.as("user_id"),
                        qUser.name.as("name"),
                        qUser.report_cnt.as("report_cnt"),
                        qUser.is_ban.as("is_ban"),
                        qUserReport.latest_report_day.max().as("latest_report_day")))
                .from(qUserReport, qUser)
                .where(qUserReport.user_id.eq(qUser.userId),
                        qUser.is_ban.eq(true),
                        qUserReport.user.name.eq(name))
                .groupBy(qUserReport.user_id)
                .fetch();

        return userReports;
    }
}
