package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comment_id;

    @Column(
            length = 500
    )
    private String comment_content;

    @Column
    @CreationTimestamp
    private LocalDateTime comment_regtime;

    //FK : 유저 시리얼
    @ManyToOne
    @JoinColumn(name="fk_user_user_serial", referencedColumnName = "userSerial")
    private User user;

    //FK : 게시판 시리얼
    @ManyToOne
    @JoinColumn(name="fk_community_article_id", referencedColumnName = "article_id")
    private Community community;

}
