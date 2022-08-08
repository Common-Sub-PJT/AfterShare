package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.QComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 코멘트 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class CommentRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QComment qComment = QComment.comment;

    public long countCommentByArticleId(long article_id){
        long comments = jpaQueryFactory.select(qComment).from(qComment)
                .where(qComment.community.article_id.eq(article_id)).fetchCount();
        return comments;
    }

    public Comment findCommentByCommentId(long comment_id) {
        Comment comments = jpaQueryFactory.select(qComment).from(qComment)
                .where(qComment.comment_id.eq(comment_id)).fetchOne();
        return comments;
    }

    public List<Comment> findCommentListByUserId(String userId) {
        List<Comment> comments = jpaQueryFactory.select(qComment).from(qComment)
                .where(qComment.user.userId.eq(userId)).fetch();
        if(comments==null) return null;
        return comments;
    }
}
