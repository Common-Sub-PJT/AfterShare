package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 화상회의 생성 ([POST] /conferences/chat) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceRegisterPostRequest")
public class ConferenceRegistPostReq {
    @ApiModelProperty(name="방장 ID", example = "ssafy")
    String owner_id;

    @ApiModelProperty(name="방 제목", example = "데스노트이번에대박ㅋㅋ")
    String title;

    //  0(false):모두 / 1(true):관람자만 (필수) -> default:0(false)
    @ApiModelProperty(name="모두/관람자", example = "1")
    boolean is_after;

    //  0(false):스포가능 / 1(true):스포금지 / default:0(false)
    //관람자만일 경우 무조건 스포이므로! 관람자만을 선택하면 스포가능으루.
    @ApiModelProperty(name="스포가능/스포금지", example = "0")
    boolean is_spoiler;

    @ApiModelProperty(name="방제한인원", example = "5")
    int person_limit;

    // 0(false):비번 x / 1(true):비번 o -> default:0(false)
    @ApiModelProperty(name="비밀번호 여부", example = "1")
    boolean is_secret;

    @ApiModelProperty(name="비밀번호", example = "1234")
    String password;

    @ApiModelProperty(name="시/도", example = "서울특별시")
    String sido;

    @ApiModelProperty(name="시/군/구", example = "서초구")
    String sigungu;

    @ApiModelProperty(name="공연제목", example = "데스노트")
    String prfnm;

    @ApiModelProperty(name="공연일시", example = "2022-08-10 19시 30분")
    String perform_day;

    @ApiModelProperty(name="공연ID", example = "PF191728")
    String mt20id;

    @ApiModelProperty(name="장르명", example = "뮤지컬")
    String genrenm;
}
