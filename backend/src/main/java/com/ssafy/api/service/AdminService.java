package com.ssafy.api.service;

import com.ssafy.api.response.AdminReportRes;
import com.ssafy.api.response.AdminStopUserRes;
import com.ssafy.db.entity.User;

import java.util.List;

public interface AdminService {
    List<AdminStopUserRes> getStopUserAllList();

    List<AdminStopUserRes> searchStopUserList(String userId);

    void updateIs_ban(User user, String name);

    List<AdminReportRes> getReportAllList();
}
