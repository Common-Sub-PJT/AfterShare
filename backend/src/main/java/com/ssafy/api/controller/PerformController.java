package com.ssafy.api.controller;

import com.ssafy.api.request.PerformGetReq;
import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;
import com.ssafy.api.service.PerformService;
import com.ssafy.db.entity.Gugun;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공연 API호출 API", tags = {"Perform"})
@RestController
@RequestMapping("/api/performs")
public class PerformController {
    @Autowired
    PerformService performService;

    //------------조다연 시작-----------------

    @GetMapping("")
    @ApiOperation(value = "공연 목록 조회 API 요청", notes = "전체 공연 목록을 가져온다. ")
    public ResponseEntity<List<PerformRes>> getPerformAllList() {
        //api 호출 -> xml 파싱 -> PerformRes 객체에 담아주기
        List<PerformRes> res = performService.getPerformAllList();

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{mt20id}")
    @ApiOperation(value = "공연 상세정보 조회 API 요청", notes = "선택된 공연에 대한 상세 정보를 가져온다. ")
    public ResponseEntity<PerformInfoRes> getPerformInfo(
            @PathVariable("mt20id") String mt20id) {
        //api 호출 -> xml 파싱 -> PerformInfoRes 객체에 담아주기
        PerformInfoRes res = performService.getPerformInfo(mt20id);

        return ResponseEntity.status(200).body(res);
    }
    //------------조다연 끝-----------------

    //공연 API 검색 (시도, 구군, 검색단어, 현재페이지, 페이지당 목록수)

    //---------차송희 시작------------------------------
    //get to post
    @PostMapping("/guguns")
    @ApiOperation(value = "구군 이름 검색", notes = "시도, 구군, 검색단어, 현재페이지, 페이지당 목록수를 통한 api검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<Gugun>> searchGugunList(
            @RequestBody @ApiParam(value = "구군 이름 리스트", required = true) PerformGetReq searchInfo) {

        //시도이름으로 시도코드찾기
        int sidocode = performService.searchSido(searchInfo);

        //시도이름으로 시도코드찾기 시도코드로 구군이름목록 반환
        List<Gugun> gugunList = performService.searchGugunList(sidocode);
        return ResponseEntity.status(200).body(gugunList);
    }
    //get to post
    @PostMapping("/search")
    @ApiOperation(value = "공연 정보 검색", notes = "시도, 구군, 검색단어, 현재페이지, 페이지당 목록수를 통한 api검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<PerformRes>> searchPerform(
            @RequestBody @ApiParam(value = "검색 정보", required = true) PerformGetReq searchInfo){
        //시도이름으로 시도코드찾기, //구군이름으로 구군코드찾기
        int sidocode = 0;
        int guguncode =0;
        if(!searchInfo.getSidoname().equals("")){
            sidocode = performService.searchSido(searchInfo);
            guguncode = performService.searchGugun(searchInfo);
        }  //시도코드 , 구군코드, 검색 단어로 공연
        List<PerformRes> res = performService.searchPerform(sidocode, guguncode, searchInfo);
        return ResponseEntity.status(200).body(res);
    }
    //---------차송희 끝------------------------------
}
