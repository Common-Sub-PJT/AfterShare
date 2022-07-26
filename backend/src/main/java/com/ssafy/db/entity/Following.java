package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Following {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long following_serial;

//    @Column(name = "fk_user_user_serial")
//    int user_serial;

    @Column(
            length = 30
    )
    String following_id;

    @Column(
            length = 30
    )
    String userId;

}
