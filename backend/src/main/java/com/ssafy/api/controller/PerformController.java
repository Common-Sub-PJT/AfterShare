package com.ssafy.api.controller;

import com.ssafy.api.response.PerformRes;
import com.ssafy.api.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    PerformService performService;
    
    @GetMapping("")
    @ApiOperation(value = "공연 목록 조회 API 요청", notes = "전체 공연 목록을 가져온다. ")
    public ResponseEntity<List<PerformRes>> getPerformAllList() {
        System.out.println("getPerformAllList");
        //api 호출 -> xml 파싱
        List<PerformRes> res = performService.getPerformAllList();
        System.out.println("서비스 들어갓다나옴");
        return ResponseEntity.status(200).body(res);
    }


}
