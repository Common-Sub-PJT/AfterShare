package com.ssafy.db.repository;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
