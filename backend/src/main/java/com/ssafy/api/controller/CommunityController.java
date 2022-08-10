package com.ssafy.api.controller;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.api.service.CommunityService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 커뮤니치 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "커뮤니티 API", tags = {"Community"})
@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    //차송희 시작-----------------------------------
    @PostMapping("")
    @ApiOperation(value = "커뮤니티 글 작성", notes = "로그인 후 글 작성 가능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> Register(
            @RequestBody @ApiParam(value = "글 작성 정보", required = true) CommunityRegistPostReq communityRegisterInfo
            ,@ApiIgnore Authentication authentication) {

        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        Community community = communityService.create(user, communityRegisterInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @GetMapping("/all")
    @ApiOperation(value = "전체 게시글 목록 조회", notes = "전체 게시글 목록을 조회한다.")
    public ResponseEntity<List<CommunityRes>> getAllList(){
        List<CommunityRes> res = communityService.getAllList();
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{_genre}/{_category}")
    @ApiOperation(value = "장르/카테고리별 게시글 목록 조회", notes = "장르/카테고리별 게시글 목록을 조회한다.")
    public ResponseEntity<List<CommunityRes>> getList(
            @PathVariable int _genre,
            @PathVariable int _category) {
        List<CommunityRes> res = communityService.getListByGenre(_genre,_category);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{_genre}/{_category}/{searchcategory}/{searchword}")
    @ApiOperation(value = "장르/카테고리별 게시글 목록 조회", notes = "장르/카테고리별 게시글 목록을 조회한다.")
    public ResponseEntity<List<CommunityRes>> getList(
            @PathVariable int _genre,
            @PathVariable int _category,
            @PathVariable String searchcategory,
            @PathVariable String searchword) {
        List<CommunityRes> res = communityService.getListSearch(_genre,_category, searchcategory, searchword);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{_id}")
    @ApiOperation(value = "게시글 상세보기", notes="게시글 상세조회를 실행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = " 사용 가능"),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Community> getDetails(@PathVariable long _id){
        Community community = communityService.getById(_id);

        return ResponseEntity.status(200).body(community);
    }

    @PatchMapping("/{_id}")
    @ApiOperation(value = "게시글 상세 수정", notes="게시글 상세를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = " 사용 가능"),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> updateDetails(
            @PathVariable long _id,
            @RequestBody @ApiParam(value = "글 내용") CommunityRegistPostReq Info,
            @ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        communityService.update(_id, Info);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/{_id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제 후 응답한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 삭제 성공"),
            @ApiResponse(code = 401, message = "게시글 삭제 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> delete(
            @PathVariable("_id") long _id
    ) {

        communityService.deletearticle(_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

    }

    @PostMapping("/{_id}/like")
    @ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> recommendarticle(
            @PathVariable("_id") long _id
            ,@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        Community community = communityService.getById(_id);
        communityService.recommend(_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/{_id}/count")
    @ApiOperation(value = "게시글 조회수", notes = "게시글 조회수 증가")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> ViewCnt(
            @PathVariable("_id") long _id
            ,@ApiIgnore Authentication authentication
    ){
        Community commu = communityService.getById(_id);
        communityService.updateViewCnt(_id,commu);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    //------------------댓글 CRUD---------------------------------------------------------
    @PostMapping("/{_id}/comments")
    @ApiOperation(value = "댓글 작성", notes = "댓글 작성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> commentRegister(
            @RequestParam @ApiParam(value = "댓글 정보", required = true) String comment
            ,@ApiIgnore Authentication authentication
            ,@PathVariable long _id) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        Community community = communityService.getById(_id);
        Comment comments = communityService.createComment(user, comment, community);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PatchMapping("/{_id}/comments/{comment_id}")
    @ApiOperation(value = "댓글 수정", notes="댓글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = " 사용 가능"),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> updateComments(
            @PathVariable long _id,
            @PathVariable long comment_id,
            @RequestParam @ApiParam(value = "댓글 정보", required = true) String comment,
            @ApiIgnore Authentication authentication
    ){
        Comment comments = communityService.updateComment(comment, comment_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
    @DeleteMapping("/{_id}/comments/{comment_id}")
    @ApiOperation(value = "댓글 삭제", notes="댓글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = " 사용 가능"),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteComments(
            @PathVariable long comment_id){
        communityService.deleteComment(comment_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
    //------------차송희 끝---------------------------------
}
