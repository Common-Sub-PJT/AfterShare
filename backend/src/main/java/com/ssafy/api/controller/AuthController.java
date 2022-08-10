package com.ssafy.api.controller;

import com.ssafy.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /*
    test Token for id:chasonghui
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFzb25naHVpIiwiaXNzIjoic3NhZnkuY29tIiwiZXhwIjoxNjYwMTUwNjY4LCJpYXQiOjE2NTg4NTQ2Njh9.ib8MXnJYTl9DZ34fW3TBZptDc_31cuae6p0xZes75t76XjfZRGP5i0GIY0FHdkJNQyG5HDc5aJ4qfECbAnvGkg
     */
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserLoginPostRes> login(
            @RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
        String userId = loginInfo.getId();
        String password = loginInfo.getPassword();

        User user = userService.getUserByUserId(userId);

        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if(passwordEncoder.matches(password, user.getPassword())) {
            //계정 정지 당한 유저라면
            if(user.is_ban()) {
                //마지막 신고 날짜
                LocalDateTime latest_report_day = adminService.getLatestReportDay(user.getUserId());

                //현재 날짜
                LocalDateTime now = LocalDateTime.now();

                //현재 날짜와 마지막 신고 날짜의 차이가 한 달이 넘으면 정지 자동 해제
                if(ChronoUnit.MONTHS.between(latest_report_day, now) >= 1) {
                    adminService.updateIs_ban(user, user.getName());

                    // 유효한 패스워드가 맞고 계정이 정지 해제된 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
                    return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
                } else {
                    // 계정이 정지 되어 있는 경우, 로그인 실패로 응답.
                    return ResponseEntity.status(402).body(UserLoginPostRes.of(402, "Stopped Account", null));
                }
            }
            // 유효한 패스워드가 맞고 계정이 정지되지 않은 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
        }
        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
    }
}
