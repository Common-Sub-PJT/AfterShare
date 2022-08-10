package com.ssafy.api.controller;

import com.ssafy.api.response.AdminReportRes;
import com.ssafy.api.response.AdminStopUserRes;
import com.ssafy.api.service.AdminService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 관리자 페이지 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "관리자 API", tags = {"Admin"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/users")
    @ApiOperation(value = "정지된 유저 전체 목록 조회", notes = "정지된 유저의 전체 목록을 가져온다. ")
    public ResponseEntity<List<AdminStopUserRes>> getStopUserAllList() {

        List<AdminStopUserRes> res = adminService.getStopUserAllList();

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/users/{name}")
    @ApiOperation(value = "정지된 유저 이름으로 검색", notes = "정지된 유저의 전체 목록에서 이름으로 검색한다. ")
    public ResponseEntity<List<AdminStopUserRes>> searchStopUserList(@PathVariable String name) {

        List<AdminStopUserRes> res = adminService.searchStopUserList(name);

        return ResponseEntity.status(200).body(res);
    }

    @PatchMapping("/users/{name}")
    @ApiOperation(value = "정지된 유저 정지 해제", notes = "유저의 정지를 해제한다. ")
    public ResponseEntity<? extends BaseResponseBody> updateIs_ban(
            @PathVariable String name,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        //is_ban : true -> false, 신고누적횟수 0으로 초기화
        adminService.updateIs_ban(user, name);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/report")
    @ApiOperation(value = "신고 목록 조회", notes = "신고 전체 목록을 가져온다. ")
    public ResponseEntity<List<AdminReportRes>> getReportAllList() {

        List<AdminReportRes> res = adminService.getReportAllList();

        return ResponseEntity.status(200).body(res);
    }
}
