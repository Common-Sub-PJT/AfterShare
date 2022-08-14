package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceGetReq;
import com.ssafy.api.request.ConferenceRegistPostReq;
import com.ssafy.api.request.UserReportPostReq;
import com.ssafy.api.response.ConferenceInfoRes;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserReport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 화상회의 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "화상회의채팅방 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    UserService userService;


    @GetMapping("")
    @ApiOperation(value = "화상회의 전체 목록 조회", notes = "전체 화상회의 목록을 가져온다. ")
    public ResponseEntity<List<ConferenceRes>> getConferenceAllList() {

       List<ConferenceRes> res = conferenceService.getConferenceAllList();

        return ResponseEntity.status(200).body(res);
    }

    //get to post
    @PostMapping("/search")
    @ApiOperation(value = "화상회의 검색", notes = "선택된 조건에 대한 화상회의 목록을 가져온다. ")
    public ResponseEntity<List<ConferenceRes>> searchConference(
            @RequestBody @ApiParam(value = "검색 정보", required = true) ConferenceGetReq searchInfo) {
        List<ConferenceRes> res = new ArrayList<>();

        if(searchInfo.getKeyword() != null) { // keyword o
            res = conferenceService.searchConference(searchInfo);
        } else if(searchInfo.getKeyword() == null) { //keyword x
            res = conferenceService.searchConferenceNoKeyword(searchInfo);
        }

        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/chat")
    @ApiOperation(value = "화상회의 방 생성", notes = "<strong>회의실 생성 정보</strong>를 입력받아 화상회의 방을 생성한다. ")
    public ResponseEntity<Long> createConference(
            @RequestBody @ApiParam(value = "화상회의 생성 정보", required = true) ConferenceRegistPostReq conferenceInfo,
            @ApiIgnore Authentication authentication) {

        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        //임의로 리턴된 Conference 인스턴스. 현재 코드는 방 생성 성공 여부만 판단하기 때문에 굳이 Insert 된 정보를 응답하지 않음.
        Conference conference = conferenceService.createConference(user, conferenceInfo);

        return ResponseEntity.status(200).body(conference.getConference_id());
    }

    @GetMapping("/follow")
    @ApiOperation(value = "화상회의 팔로우 목록 조회", notes = "화상회의 목록에서 팔로우한 방장의 화상회의 방 목록을 가져온다. ")
    public ResponseEntity<List<ConferenceRes>> getConferenceFollowList(
            @ApiIgnore Authentication authentication
    ) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        List<FollowingRes> followingUser = userService.getFollowingListByUserId(user.getUserId());

        List<ConferenceRes> res = conferenceService.getConferenceFollowList(followingUser);

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{conference_id}")
    @ApiOperation(value = "화상회의 상세 정보 조회", notes = "입장한 화상회의에 대한 상세 정보를 가져온다. ")
    public ResponseEntity<ConferenceInfoRes> getConferenceInfo(
            @PathVariable("conference_id") long conference_id) {

        ConferenceInfoRes res = conferenceService.getConferenceInfo(conference_id);

        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/following")
    @ApiOperation(value = "팔로우 하기", notes = "화상 회의중 팔로우 하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> following(
            @RequestParam("followingId") String followingId,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        conferenceService.following(followingId, user);
        userService.followingCnt(user);
        conferenceService.follower(user, followingId);
        userService.followerCnt(followingId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/unfollowing")
    @ApiOperation(value = "언팔로우 하기", notes = "화상 회의중 언팔로우 하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> unfollowing(
            @RequestParam("unfollowingId") String unfollowingId,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();

        conferenceService.unfollowing(unfollowingId);
        userService.unfollowingCnt(user);
        conferenceService.unfollower(user.getUserId());
        userService.unfollowerCnt(unfollowingId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/report")
    @ApiOperation(value = "신고하기", notes = "화상 회의중 유저 신고하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> report(
            @RequestBody @ApiParam(value = "유저 신고 정보", required = true) UserReportPostReq userReportInfo,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        conferenceService.report(user, userReportInfo.getReportUserId(), userReportInfo.getReportContent());
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

}
