package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceGetReq;
import com.ssafy.api.request.ConferenceRegistPostReq;
import com.ssafy.api.response.ConferenceInfoRes;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.db.entity.*;

import java.util.List;

/**
 *	화상회의 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

public interface ConferenceService {

    List<ConferenceRes> getRecentConferenceList();

    List<ConferenceRes> getConferenceAllList();

    List<ConferenceRes> searchConference(ConferenceGetReq searchInfo);

    List<ConferenceRes> searchConferenceNoKeyword(ConferenceGetReq searchInfo);

    Conference createConference(User user, ConferenceRegistPostReq conferenceInfo);

    List<ConferenceRes> getConferenceFollowList(List<FollowingRes> followingUser);

    ConferenceInfoRes getConferenceInfo(long conference_id);

    Following following(String following_id, String userId);

    Follower follower(String userId, String follower_id);

    UserReport report(User user, String reportUserId, String reportContent);
}
