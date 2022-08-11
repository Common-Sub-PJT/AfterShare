package com.ssafy.db.repository;

import com.ssafy.db.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
}
