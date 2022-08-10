package com.ssafy.api.service;


import com.ssafy.api.request.ConferenceGetReq;
import com.ssafy.api.request.PerformGetReq;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;
import com.ssafy.db.entity.Gugun;

import java.util.List;

/**
 *	화상회의 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

public interface ConferenceService {

    List<ConferenceRes> getRecentConferenceList();

    List<ConferenceRes> getConferenceAllList();

    List<ConferenceRes> searchConference(ConferenceGetReq searchInfo);

    List<ConferenceRes> searchConferenceNoKeyword(ConferenceGetReq searchInfo);
}
