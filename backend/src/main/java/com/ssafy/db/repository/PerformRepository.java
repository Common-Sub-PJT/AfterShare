package com.ssafy.db.repository;

import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformRepository {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
    Optional<Sido> findBySidoName(String sidoname);
    Optional<Gugun> findByGugunName(String sidoname, String gugunname);
}

