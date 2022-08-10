package com.ssafy.api.controller;

import com.ssafy.api.service.MailService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 메일 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "메일 API", tags = {"Mail"})
@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    private MailService mailService;

    @PatchMapping("/findmy/sendemail")
    @ApiOperation(value = "비밀번호 찾기", notes = "입력한 이메일로 변경된 비밀번호를 전송한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> findPwd(
            @ApiIgnore Authentication authentication) throws Exception {
        // 유저 정보 가져오기
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        mailService.sendFindPwdMail(user);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
