package com.ssafy.api.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공연 KOPISAPI 호출 API", tags = {"Perform"})
@RestController
@RequestMapping("/api/performs")
public class PerformController {
}
