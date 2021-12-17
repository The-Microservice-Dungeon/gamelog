package com.github.tmd.gamelog.adapter.jpa;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class MovementScoreDto {
    private int value = 0;
}
