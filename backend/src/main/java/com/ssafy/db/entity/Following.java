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

    //@MapsId //@id로 지정한 컬럼에 @OneToOne 이나 @ManyToOne 관계를 매핑시키는 역할
    @ManyToOne
    @JoinColumn(name="fk_user_user_serial",referencedColumnName = "userSerial")
    private User user;

}
