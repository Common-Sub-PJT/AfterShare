package com.ssafy.db.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.ConferenceGetReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.QConference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class ConferenceRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QConference qConference = QConference.conference;

    public List<Conference> findBySidoSigungu(ConferenceGetReq searchInfo) {
        List<Conference> conferences = jpaQueryFactory.select(qConference).from(qConference)
                .where(
                        sidoEq(searchInfo.getSido()),
                        sigunguEq(searchInfo.getSigungu()),
                        afterEq(searchInfo.getIs_after()),
                        genrenmEq(searchInfo.getGenrenm())
                )
                .fetch();

        if(conferences == null) {
            return null;
        }

        return conferences;
    }

    public List<Conference> findByKeywordContaining(ConferenceGetReq searchInfo) {
        List<Conference> conferences = jpaQueryFactory.select(qConference).from(qConference)
                .where(
                        sidoEq(searchInfo.getSido()),
                        sigunguEq(searchInfo.getSigungu()),
                        afterEq(searchInfo.getIs_after()),
                        genrenmEq(searchInfo.getGenrenm()),
                        keywordEq(searchInfo.getType(), searchInfo.getKeyword())
                )
                .fetch();

        if(conferences == null) {
            return null;
        }

        return conferences;
    }

    private BooleanExpression afterEq(Boolean is_after) {
        if(is_after!=null){ // 모두/관람자만 선택되어있을 시
            return qConference.is_after.eq(is_after);
        }
        return null;
    }

    private BooleanExpression keywordEq(int type, String keyword) {
        if(type == 0){ //공연제목
            return qConference.prfnm.like("%" + keyword + "%");
        } else if (type == 1) { //방제목
            return qConference.title.like("%" + keyword + "%");
        }
        return null;
    }

    private BooleanExpression genrenmEq(String genrenm) {
        if(genrenm.equals("ALL")){
            return null;
        } else {
            return qConference.genrenm.eq(genrenm);
        }
    }

    private BooleanExpression sigunguEq(String sigungu) {
        return StringUtils.hasText(sigungu) ? qConference.sigungu.eq(sigungu) : null ;
    }

    private BooleanExpression sidoEq(String sido) {
        return StringUtils.hasText(sido) ? qConference.sido.eq(sido) : null ;
    }
    //차송희 시작---------------------------------
    public List<Conference> findRecentConferenceList() {
        List<Conference> conferences = jpaQueryFactory.select(qConference).from(qConference)
                .orderBy(qConference.call_start_time.desc())
                .limit(4)
                .fetch();
        return conferences;
    }
    //차송희 끝------------------------------------

    public Conference findConferenceFollowListByUserId(String followingUserId) {
        Conference conferences = jpaQueryFactory.select(qConference).from(qConference)
                .where(qConference.owner_id.eq(followingUserId))
                .fetchOne();
        return conferences;
    }

    public Conference findByConferenceId(long conference_id) {
        Conference conference = jpaQueryFactory.select(qConference).from(qConference)
                .where(qConference.conference_id.eq(conference_id))
                .fetchOne();
        return conference;
    }
}
