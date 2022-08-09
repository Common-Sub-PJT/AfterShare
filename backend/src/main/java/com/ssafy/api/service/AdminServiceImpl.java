package com.ssafy.api.service;

import com.ssafy.api.response.AdminReportRes;
import com.ssafy.api.response.AdminStopUserRes;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.QUserReport;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserReport;
import com.ssafy.db.repository.AdminRepository;
import com.ssafy.db.repository.AdminRepositorySupport;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("AdminService")
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminRepositorySupport adminRepositorySupport;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRepositorySupport userRepositorySupport;

    QUserReport qUserReport = QUserReport.userReport;
    QUser qUser = QUser.user;

    @Override
    public List<AdminStopUserRes> getStopUserAllList() {

        List<AdminStopUserRes> userReportList = adminRepositorySupport.findAllGroupByUserSerial();

        List<AdminStopUserRes> res = new ArrayList<>();

        for (AdminStopUserRes report : userReportList) {
            //db에서 불러온 report 정보에서 필요한 정보만 AdminStopUserRes 담아 목록 만들어주기
            res.add(AdminStopUserRes.of(report));
        }

        return res;
    }

    @Override
    public List<AdminStopUserRes> searchStopUserList(String name) {
        List<AdminStopUserRes> userReportList = adminRepositorySupport.findByName(name);

        List<AdminStopUserRes> res = new ArrayList<>();

        for (AdminStopUserRes report : userReportList) {
            //db에서 불러온 report 정보에서 필요한 정보만 AdminStopUserRes 담아 목록 만들어주기
            res.add(AdminStopUserRes.of(report));
        }

        return res;
    }

    @Override
    public void updateIs_ban(User user, String name) {
        User updateUser = userRepositorySupport.findByName(name).get();

        updateUser.set_ban(false);
        updateUser.setReport_cnt(0);

        userRepository.save(updateUser);
    }

    @Override
    public List<AdminReportRes> getReportAllList() {
        List<UserReport> reportList = adminRepository.findAll();

        List<AdminReportRes> res = new ArrayList<>();

        for (UserReport report : reportList) {
            res.add(AdminReportRes.of(report));
        }

        return res;
    }
}
