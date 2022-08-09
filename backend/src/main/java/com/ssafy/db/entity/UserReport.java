package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long report_serial;

    @Column(
            length = 30
    )
    private String user_id;

    @Column(
            length = 30
    )
    private String report_user_id;

    @Column(
            columnDefinition = "TEXT"
    )
    private String report_content;

    @Column
    @CreationTimestamp
    private LocalDateTime latest_report_day;

    //FK : 유저 시리얼
    @ManyToOne
    @JoinColumn(name="fk_user_user_serial", referencedColumnName = "userSerial")
    private User user;
}
