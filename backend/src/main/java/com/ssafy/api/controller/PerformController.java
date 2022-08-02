package com.ssafy.api.controller;

import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;
import com.ssafy.api.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 공연 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공연 KOPISAPI 호출 API", tags = {"Perform"})
@RestController
@RequestMapping("/api/performs")
public class PerformController {

    //------------조다연 시작-----------------
    @Autowired
    PerformService performService;
    
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
}
