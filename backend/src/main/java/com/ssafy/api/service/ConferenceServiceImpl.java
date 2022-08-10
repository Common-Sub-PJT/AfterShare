package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceGetReq;
import com.ssafy.api.request.ConferenceRegistPostReq;
import com.ssafy.api.response.ConferenceInfoRes;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.FollowingRes;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *	화상회의 API 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("ConferenceService")
public class ConferenceServiceImpl implements ConferenceService{

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;

    @Override
    public List<ConferenceRes> getRecentConferenceList() {
        List<Conference> conferenceList = conferenceRepositorySupport.findRecentConferenceList();
        List<ConferenceRes> res = new ArrayList<>();
        for(Conference conference: conferenceList){
            res.add(ConferenceRes.of(conference));
        }
        return res;
    }

    @Override
    public List<ConferenceRes> getConferenceAllList() {
        List<Conference> conferenceList = conferenceRepository.findAll();

        List<ConferenceRes> res = new ArrayList<>();

        for (Conference conference : conferenceList) {
            //db에서 불러온 conference 정보에서 필요한 정보만 conferenceRes에 담아 목록 만들어주기
            res.add(ConferenceRes.of(conference));
        }

        return res;
    }

    @Override
    public List<ConferenceRes> searchConference(ConferenceGetReq searchInfo) {
        List<Conference> conferenceList = conferenceRepositorySupport.findByKeywordContaining(searchInfo);

        List<ConferenceRes> res = new ArrayList<>();

        for (Conference conference : conferenceList) {
            //db에서 불러온 conference 정보에서 필요한 정보만 conferenceRes에 담아 목록 만들어주기
            res.add(ConferenceRes.of(conference));
        }

        return res;
    }

    @Override
    public List<ConferenceRes> searchConferenceNoKeyword(ConferenceGetReq searchInfo) {
        List<Conference> conferenceList = conferenceRepositorySupport.findBySidoSigungu(searchInfo);

        List<ConferenceRes> res = new ArrayList<>();

        for (Conference conference : conferenceList) {
            //db에서 불러온 conference 정보에서 필요한 정보만 conferenceRes에 담아 목록 만들어주기
            res.add(ConferenceRes.of(conference));
        }

        return res;
    }

    @Override
    public Conference createConference(User userInfo, ConferenceRegistPostReq conferenceInfo) {
        Conference conference = new Conference();

        //ConferenceRegistPostReq 담긴 정보들로 설정
        conference.setOwner_id(conferenceInfo.getOwner_id());
        conference.setTitle(conferenceInfo.getTitle());
        conference.set_after(conferenceInfo.is_after());
        conference.set_spoiler(conferenceInfo.is_spoiler());
        conference.setPerson_limit(conferenceInfo.getPerson_limit());
        conference.set_secret(conferenceInfo.is_secret());
        conference.setPassword(conferenceInfo.getPassword());
        conference.setSido(conferenceInfo.getSido());
        conference.setSigungu(conferenceInfo.getSigungu());
        conference.setPrfnm(conferenceInfo.getPrfnm());
        conference.setPerform_day(conferenceInfo.getPerform_day());
        conference.setMt20id(conferenceInfo.getMt20id());
        conference.setGenrenm(conferenceInfo.getGenrenm());
        conference.setUser(userInfo);

        return conferenceRepository.save(conference);
    }

    @Override
    public List<ConferenceRes> getConferenceFollowList(List<FollowingRes> followingUserList) {
        List<ConferenceRes> res = new ArrayList<>();

        for (FollowingRes followingUser : followingUserList) {
            Conference conference = conferenceRepositorySupport.findConferenceFollowListByUserId(followingUser.getFollowing_id());

            res.add(ConferenceRes.of(conference));
        }

        return res;
    }

    @Override
    public ConferenceInfoRes getConferenceInfo(long conference_id) {
        Conference conference = conferenceRepositorySupport.findByConferenceId(conference_id);

        ConferenceInfoRes res = ConferenceInfoRes.of(conference);

        return res;
    }
}

