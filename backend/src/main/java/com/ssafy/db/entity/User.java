package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * 유저 모델 정의.
 */
@Entity(name="user")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "userSerial"))
public class User extends BaseEntity {
    @Column(
            nullable = false,
            length = 30,
            unique = true
    )
    private String userId;

    @Column(
            nullable = false,
            length = 20
    )
    private String name;

    @Column(
            nullable = false,
            length = 100,
            unique = true
    )
    private String email;

    private int social_login;

    private int following_cnt;
    private int follower_cnt;

    @Column(
            length = 50
    )
    private String profile_img;

    @Column(
            length = 1000
    )
    private String about_me;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String authKey;

    private Boolean authStatus;

    private boolean is_staff;

    private boolean is_ban;

    private int report_cnt;

//    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
//    private List<UserReport> userreports = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
//    private List<Conference> conferences = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
//    private List<Community> communities = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
//    private List<Comment> comments = new ArrayList<>();

    /*
    연관관계 설정 (db연관관계 -> 객체 연관관계 코드로 표현하는곳)
    이해가 안되면 https://ttl-blog.tistory.com/129 여기서 확인
     */
}
