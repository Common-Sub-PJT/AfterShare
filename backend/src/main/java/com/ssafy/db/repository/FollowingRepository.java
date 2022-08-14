package com.ssafy.db.repository;

import com.ssafy.db.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
}
