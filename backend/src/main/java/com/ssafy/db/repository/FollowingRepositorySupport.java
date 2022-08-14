package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Following;
import com.ssafy.db.entity.QFollowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowingRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QFollowing qFollowing = QFollowing.following;

    public Following findByFollowingId(String unfollowingId) {
        Following following = jpaQueryFactory.select(qFollowing).from(qFollowing)
                .where(qFollowing.following_id.eq(unfollowingId))
                .fetchOne();

        return following;
    }
}
