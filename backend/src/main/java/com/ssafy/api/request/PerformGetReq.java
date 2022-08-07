package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 공연 검색 API ([GET] /performs/search) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("PerformGetReqest")
public class PerformGetReq {
    //---------차송희 시작-------------------------------------
    @ApiModelProperty(name="시도 이름", example = "서울특별시")
    String sidoname;
    @ApiModelProperty(name="구군 이름", example = "종로구")
    String gugunname;
    @ApiModelProperty(name="검색 단어", example = "레베카")
    String search_word;
    @ApiModelProperty(name="장르 코드(알파벳)", example = "AAAA")
    String genre;
    //---------차송희 끝-------------------------------------
}
