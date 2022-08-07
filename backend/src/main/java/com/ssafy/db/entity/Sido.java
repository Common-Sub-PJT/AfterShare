package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Sido {
    @Id
    int signgucode;

    @Column(
            length = 30
    )
    String sidoname;

}
