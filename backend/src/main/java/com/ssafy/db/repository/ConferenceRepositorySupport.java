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
                        afterEq(searchInfo.getIs_after()))
                .fetch();

        if(conferences == null) {
            System.out.println("findBySidoSigungu::::null");
            return null;
        }

        return conferences;
    }

    public List<Conference> findByKeywordContaining(ConferenceGetReq searchInfo) {
        List<Conference> conferences = jpaQueryFactory.select(qConference).from(qConference)
                .where(
                        sidoEq(searchInfo.getSido()),
                        sigunguEq(searchInfo.getSigungu()),
                        keywordEq(searchInfo.getType(), searchInfo.getKeyword()),
                        afterEq(searchInfo.getIs_after())
                )
                .fetch();

        if(conferences == null) {
            System.out.println("findByKeywordContaining::::null");
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

    private BooleanExpression sigunguEq(String sigungu) {
        return StringUtils.hasText(sigungu) ? qConference.sigungu.eq(sigungu) : null ;
    }

    private BooleanExpression sidoEq(String sido) {
        return StringUtils.hasText(sido) ? qConference.sido.eq(sido) : null ;
    }


}
