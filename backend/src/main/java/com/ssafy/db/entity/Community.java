package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 커뮤니티 모델 정의.
 */

@Entity(name="community")
@Getter
@Setter
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long article_id;
    //글 제목
    @Column(
            length = 50
    )
    String article_title;
    //글 내용
    String article_content;
    //글 장르
    int article_genre;
    //글 카테고리
    int article_category;
    //글 작성시간
    @Column
    @CreationTimestamp
    LocalDateTime regtime;
    //글 조회수
    int view_cnt;
    //글 추천수
    int recommend;

    //FK : 유저 시리얼
    @ManyToOne
    @JoinColumn(name="fk_user_user_serial", referencedColumnName = "userSerial", unique = true)
    private User user;
}
