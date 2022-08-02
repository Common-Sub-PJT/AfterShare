package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.QGugun;
import com.ssafy.db.entity.QSido;
import com.ssafy.db.entity.Sido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Perform 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class PerformRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QSido qSido = QSido.sido;
    QGugun qGugun = QGugun.gugun;

    // 시도 이름으로 시도 코드 찾기
    public int findBySidoName(String sidoName){
        Sido sido = jpaQueryFactory.select(qSido).from(qSido)
                .where(qSido.sidoname.eq(sidoName)).fetchOne();
        if(sido ==null) return 0;
        return sido.getSigngucode();
    }

    // 시도 코드로 구군 코드 찾아서 구군코드 이름 보여주기
    //%찾는문자열%
    //%찾는문자열~
    //~찾는문자열%
    public List<Gugun> findByGugunnameContaining(int sidocode){
        List<Gugun> gugunNameList = jpaQueryFactory.select(qGugun).from(qGugun)
                .where(qGugun.signgucodesub.like(String.valueOf(sidocode)+"__")).fetch();
        return gugunNameList;
    }

    // 구군 이름으로 구군 코드 찾기
    public int findByGugunName(String gugunName){
        Gugun gugun = jpaQueryFactory.select(qGugun).from(qGugun)
                .where(qGugun.gugunname.eq(gugunName)).fetchOne();
        if(gugun ==null) return 0;
        return gugun.getSigngucodesub();
    }

}
