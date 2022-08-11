package com.ssafy.api.controller;
import com.ssafy.api.request.UserInfoFetchReq;
import com.ssafy.api.request.UserPasswordFetchReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.api.response.FollowerRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.api.service.CommunityService;
import com.ssafy.common.error.ErrorResponse;
import com.ssafy.db.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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

import javax.transaction.Transactional;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	CommunityService communityService;


	//차송희 마이페이지 시작 --------------------------------
	@GetMapping("/profile")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "내 정보 조회 성공", response = User.class),
			@ApiResponse(code = 401, message = "토큰 만료 or 토큰 없음 or 토큰 오류 -> 권한 인증 오류", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
	})
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();

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
			@RequestBody @ApiParam(value = "유저 정보", required = true) UserInfoFetchReq userInfo,
			@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		userService.updateUser(user, userInfo);

		// 유저 이메일 중복 체크 필요
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	//유저 정보삭제
	@DeleteMapping("/{userId}")
	@ApiOperation(value = "유저 정보 삭제", notes = "유저 정보를 삭제 후 응답한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원 탈퇴 성공"),
			@ApiResponse(code = 400, message = "input 오류", response = ErrorResponse.class),
			@ApiResponse(code = 401, message = "토큰 만료 or 토큰 없음 or 토큰 오류 -> 권한 인증 오류", response = ErrorResponse.class),
			@ApiResponse(code = 404, message = "회원 탈퇴할 정보가 없습니다.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@PathVariable("userId") String userId) {
		userService.deleteUser(userId);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PatchMapping("/change-password")
	@ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> updatePassword(
			@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value = "변경할 비밀번호", required = true) UserPasswordFetchReq userPasswordUpdateReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		User user = userDetails.getUser();

		userService.updatePassword(user, userPasswordUpdateReq);

		return ResponseEntity.ok(BaseResponseBody.of(200, "비밀번호 변경 완료"));
	}

	//유저 팔로워 목록
	@GetMapping("/follower/{userId}")
	@ApiOperation(value = "팔로워 리스트 가져오기", notes = "팔로워 리스트를 가져온다.")
	public ResponseEntity<List<FollowerRes>> getUserFollowList(@PathVariable("userId") String userId) {
		List<FollowerRes> res = userService.getFollowerListByUserId(userId);

		return ResponseEntity.status(200).body(res);
	}

	//유저 팔로잉 목록
	@GetMapping("/following/{userId}")
	@ApiOperation(value = "팔로잉 리스트 가져오기", notes = "팔로잉 리스트를 가져온다.")
	public ResponseEntity<List<FollowingRes>> getUserFollowingList(@PathVariable("userId") String userId) {
		List<FollowingRes> res = userService.getFollowingListByUserId(userId);

		return ResponseEntity.status(200).body(res);
	}

	//내가 쓴 게시글
	@GetMapping("/{userId}/my-articles")
	@ApiOperation(value = "내가 쓴 글 리스트 가져오기", notes = "내가 쓴 글 리스트를 가져온다.")
	public ResponseEntity<List<CommunityRes>> getMyArticles(
			@PathVariable String userId,
			@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		List<CommunityRes> res = communityService.getListByUserId(userId);

		return ResponseEntity.status(200).body(res);
	}

	//내가 쓴 댓글
	@GetMapping("/{userId}/my-comments")
	@ApiOperation(value = "내가 쓴 댓글 리스트 가져오기", notes = "내가 쓴 댓글 리스트를 가져온다.")
	public ResponseEntity<List<String>> getMyComments(
			@PathVariable String userId,
			@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		List<String> res = communityService.getCommentListByUserId(userId);

		return ResponseEntity.status(200).body(res);
	}

	//---------------------------------------------------------------------
	//-------------------------------------------------- 조다연 회원가입 관련 시작
	@PostMapping("")
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원가입 성공"),
			@ApiResponse(code = 400, message = "input 오류", response = ErrorResponse.class),
			@ApiResponse(code = 409, message = "이메일 중복 에러(회원가입 불가)", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "서버 에러 or 이메일 전송 에러", response = ErrorResponse.class)
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
			@ApiResponse(code = 200, message = "닉네임 중복 아님"),
			@ApiResponse(code = 400, message = "input 오류", response = ErrorResponse.class),
			@ApiResponse(code = 409, message = "닉네임 중복", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
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
