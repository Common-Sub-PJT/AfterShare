package com.ssafy.db.repository;

import com.ssafy.db.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository <Follower, Integer> {
}
