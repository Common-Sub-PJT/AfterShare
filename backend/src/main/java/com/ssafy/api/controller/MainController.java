package com.ssafy.api.controller;

import com.ssafy.api.response.CommunityRes;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.PerformCarouselRes;
import com.ssafy.api.service.CommunityService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 메인페이지 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "메인 API", tags = {"Main"})
@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    PerformService performService;

    @Autowired
    CommunityService communityService;

    @Autowired
    ConferenceService conferenceService;

    @GetMapping("/performs/ranking")
    @ApiOperation(value = "공연 예매상황판 API 요청", notes = "공연 예매상황판을 가져온다. ")
    public ResponseEntity<List<PerformCarouselRes>> getCarouselList(
            @RequestParam String catecode) {
        //api 호출 -> xml 파싱 -> PerformCarouselRes 객체에 담아주기
        List<PerformCarouselRes> res = performService.getCarouselList(catecode);

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/communities/ranking")
    @ApiOperation(value = "커뮤니티 인기글 5개 추천수기준", notes = "추천수 기준 인기글 5개를 불러온다.")
    public ResponseEntity<List<CommunityRes>> getPopularList() {
        List<CommunityRes> res = new ArrayList<>();
        res = communityService.getPopularList();

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/conferences/ranking")
    @ApiOperation(value = "화상회의방 최근기준 4개", notes = "화상회의방 최근생성기준 4개를 불러온다.")
    public ResponseEntity<List<ConferenceRes>> getRecentConferenceList() {
        List<ConferenceRes> res = new ArrayList<>();
        res = conferenceService.getRecentConferenceList();
        return ResponseEntity.status(200).body(res);
    }

}
