package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PerformListResponse")
public class PerformRes {
    //공연명
    String prfnm;
    //공연 장르명
    String genrenm;
    //공연 시작일
    String prfpdfrom;
    //공연 종료일
    String prfpdto;
    //공연 포스터 경로
    String poster;
    //공연 시설명
    String fcltynm;
    //공연 ID
    String mt20id;

    //생성자
    public static PerformRes of(String prfnm, String genrenm,
                                String prfpdfrom, String prfpdto,
                                String poster, String fcltynm, String mt20id) {
        PerformRes res = new PerformRes();
        res.setPrfnm(prfnm);
        res.setGenrenm(genrenm);
        res.setPrfpdfrom(prfpdfrom);
        res.setPrfpdto(prfpdto);
        res.setPoster(poster);
        res.setFcltynm(fcltynm);
        res.setMt20id(mt20id);
        return res;
    }
}