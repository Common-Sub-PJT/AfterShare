package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 화상회의 목록 검색 ([GET] /conferences/search) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceGetRequest")
public class ConferenceGetReq {
    //입력해서 넘기는 request body = type(title와 prfnm 중 무엇인지), keyword(입력된 단어), sido, sigungu, is_after

    //  false:모두 / true:관람자만 (필수) / null:선택안함
    @ApiModelProperty(name="모두/관람자", example = "0")
    Boolean is_after;

    //  0:공연제목 / 1:방제목 -> default:0
    @ApiModelProperty(name="선택타입", example = "0")
    int type;

    @ApiModelProperty(name="검색단어", example = "레베카")
    String keyword;

    @ApiModelProperty(name="시/도", example = "서울특별시")
    String sido;

    @ApiModelProperty(name="시/군/구", example = "중구")
    String sigungu;

    //  default:ALL / 공통
    @ApiModelProperty(name="장르코드", example = "AAAB")
    String genrenm;

}
