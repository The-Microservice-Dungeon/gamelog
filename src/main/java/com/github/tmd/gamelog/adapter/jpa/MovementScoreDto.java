package com.github.tmd.gamelog.adapter.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class MovementScoreDto {
    private int value = 0;
}
