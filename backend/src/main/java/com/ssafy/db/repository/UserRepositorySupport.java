package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;

import java.util.List;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    QFollower qFollower = QFollower.follower;
    QFollowing qFollowing = QFollowing.following;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if (user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public List<Follower> findFollowerListByUserId(String userId) {
        List<Follower> followers = jpaQueryFactory.select(qFollower).from(qFollower)
                .where(qFollower.userId.eq(userId)).fetch();
        if (followers == null) {
            return null;
        }
        return followers;
    }

    public List<Following> findFollowingListByUserId(String userId) {
        List<Following> followings = jpaQueryFactory.select(qFollowing).from(qFollowing)
                .where(qFollowing.userId.eq(userId)).fetch();
        if (followings == null) return null;
        return followings;
    }

    public Optional<User> findByName(String name) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.name.eq(name)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
}
