package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;

	//-------------------------------------------------- 조다연 회원가입 관련 시작
	@PostMapping("")
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	
	//중복검사
	//url을 다르게 해줘야 함 : 다 같은 getmapping이라 PathVariable 구분 불가하기 때문
	//같은 url에서 하려면 RequestParam으로 바꿔줘야할 듯
	//아이디 중복 검사
	@GetMapping("/check-userid/{userId}")
	@ApiOperation(value = "회원가입 시 아이디 중복검사", notes = "회원 가입 시 아이디 중복검사를 실행한다")
	@ApiResponses({
			//
			@ApiResponse(code = 200, message = "존재하는 유저 아님 - 사용 가능"),
			@ApiResponse(code = 401, message = "이미 존재하는 유저"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> chDplByUserId(@PathVariable String userId) {
		if (userService.chDplByUserId(userId)) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "False"));
		} else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "True"));
		}
	}

	//닉네임 중복 검사
	@GetMapping("/check-name/{name}")
	@ApiOperation(value = "회원가입 시 닉네임 중복검사", notes = "회원 가입 시 닉네임 중복검사를 실행한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "존재하는 유저 아님 - 사용 가능"),
			@ApiResponse(code = 401, message = "이미 존재하는 유저"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> chDplByName(@PathVariable String name) {
		if (userService.chDplByName(name)) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "False"));
		} else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "True"));
		}
	}

	//이메일 중복 검사
	//check Duplicate email
	@GetMapping("/check-email/{email}")
	@ApiOperation(value = "회원가입 시 이메일 중복검사", notes = "회원 가입 시 이메일 중복검사를 실행한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "존재하는 유저 아님 - 사용 가능"),
			@ApiResponse(code = 401, message = "이미 존재하는 유저"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> chDplByEmail(@PathVariable String email) {
		if (userService.chDplByEmail(email)) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "False"));
		} else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "True"));
		}
	}
	//-------------------------------------------------- 조다연 회원가입 관련 끝
}
