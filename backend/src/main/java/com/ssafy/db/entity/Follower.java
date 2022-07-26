package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Follower {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long follower_serial;

//    @Column(name = "fk_user_user_serial")
//    int user_serial;

    @Column(
            length = 30
    )
    String follower_id;

    @Column(
            length = 30
    )
    String userId;

}
