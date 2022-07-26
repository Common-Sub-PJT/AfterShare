package com.ssafy.api.controller;

import com.ssafy.api.request.UserInfoFetchReq;
import com.ssafy.api.response.FollowerRes;
import com.ssafy.api.response.FollowingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;

	//송희
	//@Autowired
	//private JavaMailSender sender;

	@PostMapping()
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

	//송희 0724 --------------------------------
	@GetMapping("/profile")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	//유저 정보조회 - jwt
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		//String userId = userDetails.getUsername();
		//User user = userService.getUserByUserId(userId);
		System.out.println("getUserInfo");
		User user = userDetails.getUser();

		System.out.println("user: "+user);

		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	// 유저 정보수정
	@PatchMapping("/{userId}")
	@ApiOperation(value = "유저 정보 수정", notes = "유저 정보를 수정 후 응답한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "실패"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> updateUser(
			@PathVariable("userId") String userId,
			@RequestBody @ApiParam(value = "유저 정보", required = true) UserInfoFetchReq userInfo) {

		userService.updateUser(userId, userInfo);
		// 유저 이메일 중복 체크 필요
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

	}

	//유저 정보삭제
	@DeleteMapping("/{userId}")
	@ApiOperation(value = "유저 정보 삭제", notes = "유저 정보를 삭제 후 응답한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "유저 정보 삭제(탈퇴) 성공"),
			@ApiResponse(code = 401, message = "유저 정보 삭제(탈퇴) 실패"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUser(
			@PathVariable("userId") String userId
	) {

		userService.deleteUser(userId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

	}

	//유저 팔로워 목록
	@GetMapping("follower/{userId}")
	@ApiOperation(value = "팔로워 리스트 가져오기", notes = "팔로워 리스트를 가져온다.")
	public ResponseEntity<List<FollowerRes>> getUserFollowList(@PathVariable("userId") String userId) {
		System.out.println("getUserFollowerList");
		List<FollowerRes> res = userService.getFollowerListByUserId(userId);
		return ResponseEntity.status(200).body(res);
	}

	//---------------------------------------------------------------------
}
