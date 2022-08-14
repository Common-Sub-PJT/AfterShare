package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Follower;
import com.ssafy.db.entity.QFollower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowerRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QFollower qFollower = QFollower.follower;

    public Follower findByFollowerId(String unfollwerId) {
        Follower follower = jpaQueryFactory.select(qFollower).from(qFollower)
                .where(qFollower.follower_id.eq(unfollwerId))
                .fetchOne();

        return follower;
    }
}
