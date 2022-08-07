package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PerformInfoResponse")
public class PerformInfoRes {
    //--------공연 상세정보 항목
    //공연시설ID -> 상세정보 아래에 위치, 지도를 위해서 필요함
    String mt10id;
    //공연명
    String prfnm;
    //공연시작일
    String prfpdfrom;
    //공연종료일
    String prfpdto;
    //공연시설명(공연장명)
    String fcltynm;
    //공연출연진
    String prfcast;
    //공연제작진
    String prfcrew;
    //공연런타임
    String prfruntime;
    //공연관람연령
    String prfage;
    //제작사
    String entrpsnm;
    //티켓가격
    String pcseguidance;
    //포스터이미지경로
    String poster;
    //줄거리
    String sty;
    //공연장르명
    String genrenm;
    //공연상태
    String prfstate;
    //오픈런
    String openrun;
    //소개이미지목록 -> styurls의 자식태그를 , 넣어 합쳐서 넣어주기
    String styurls;
    //공연시간
    String dtguidance;

    //--------공연시설 상세정보 항목
    //전화번호
    String telno;
    //홈페이지
    String relateurl;
    //주소
    String adres;
    //위도
    String la;
    //경도
    String lo;

    public static PerformInfoRes of(String mt10id,String prfnm,
                                    String prfpdfrom, String prfpdto,
                                    String fcltynm, String prfcast,
                                    String prfcrew, String prfruntime,
                                    String prfage, String entrpsnm,
                                    String pcseguidance, String poster,
                                    String sty, String genrenm,
                                    String prfstate, String openrun,
                                    String dtguidance) {
        PerformInfoRes res = new PerformInfoRes();

        //--------공연 상세정보 항목
        res.setMt10id(mt10id);
        res.setPrfnm(prfnm);
        res.setPrfpdfrom(prfpdfrom);
        res.setPrfpdto(prfpdto);
        res.setFcltynm(fcltynm);
        res.setPrfcast(prfcast);
        res.setPrfcrew(prfcrew);
        res.setPrfruntime(prfruntime);
        res.setPrfage(prfage);
        res.setEntrpsnm(entrpsnm);
        res.setPcseguidance(pcseguidance);
        res.setPoster(poster);
        res.setSty(sty);
        res.setGenrenm(genrenm);
        res.setPrfstate(prfstate);
        res.setOpenrun(openrun);
        res.setDtguidance(dtguidance);

        return res;
    }

    public static PerformInfoRes of(PerformInfoRes res, String styurls) {
        res.setStyurls(styurls);

        return res;
    }

    public static PerformInfoRes of(PerformInfoRes res, String telno, String relateurl,
                                    String adres, String la, String lo) {
        //--------공연시설 상세정보 항목
        res.setTelno(telno);
        res.setRelateurl(relateurl);
        res.setAdres(adres);
        res.setLa(la);
        res.setLo(lo);

        return res;
    }
}
