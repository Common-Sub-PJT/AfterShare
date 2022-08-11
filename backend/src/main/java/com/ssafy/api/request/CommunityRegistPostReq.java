package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 커뮤니티 API ([POST] /api/communities) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("CommunityRegisterPostRequest")
public class CommunityRegistPostReq {
    //입력해서 넘기는 request body = 장르, 카테고리, 제목, 내용
    @ApiModelProperty(name="글 장르", example = "1")
    int article_genre;

    @ApiModelProperty(name="글 카테고리", example = "1")
    int article_category;

    @ApiModelProperty(name="글 제목", example = "레베카 리뷰입니다.")
    String article_title;

    @ApiModelProperty(name="글 내용", example = "레베카 실화냐? 가슴이 웅장해진다..")
    String article_content;
}
