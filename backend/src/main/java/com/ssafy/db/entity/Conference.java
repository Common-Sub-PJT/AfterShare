package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long conference_id;

    @Column(
            length = 30
    )
    private String owner_id;

    @Column(
            columnDefinition = "boolean default 0"
    )
    private boolean is_after;

    @Column(
            columnDefinition = "boolean default 0"
    )
    private boolean is_spoiler;

    @Column(
            nullable = false,
            length = 50
    )
    private String title;

    @Column(
            columnDefinition = "boolean default 0"
    )
    private boolean is_secret;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(
            length = 20
    )
    private String password;

    @Column
    @CreationTimestamp
    private LocalDateTime call_start_time;

    @Column(
            length = 30
    )
    private String sido;

    @Column(
            length = 30
    )
    private String sigungu;

    private int person_limit;

    @Column(
            length = 30
    )
    private String mt20id;

    @Column(
            length = 30
    )
    private String prfnm;

    @Column(
            length = 30
    )
    private String genrenm;

    @Column(
            length = 30
    )
    private String perform_day;

    @ManyToOne
    @JoinColumn(name = "fk_user_user_serial")
    private User user;
}