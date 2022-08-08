package com.ssafy.api.service;


import com.ssafy.api.request.PerformGetReq;
import com.ssafy.api.response.PerformCarouselRes;
import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;
import com.ssafy.db.entity.Gugun;

import java.util.List;

/**
 *	공연 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

public interface PerformService {

    //--------조다연 시작--------
    List<PerformRes> getPerformAllList();

    PerformInfoRes getPerformInfo(String mt20id);
    //--------조다연 끝--------

    //-------차송희 api 검색 시작-------------
    //시도 이름으로 시도코드 찾기
    int searchSido(PerformGetReq searchInfo);

    //시도 코드로 구군이름 목록 찾기
    List<Gugun> searchGugunList(int sidocode);

    //구군 이름으로 구군코드 찾기
    int searchGugun(PerformGetReq searchInfo);

    //시도코드, 구군코드, 검색단어로 공연목록 찾기
    List<PerformRes> searchPerform(int sidocode, int guguncode, PerformGetReq searchInfo);

    List<PerformCarouselRes> getCarouselList(String catecode);
    //-------끝----------------------------
}
