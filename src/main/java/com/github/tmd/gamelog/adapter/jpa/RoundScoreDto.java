package com.github.tmd.gamelog.adapter.jpa;


import com.github.tmd.gamelog.domain.RoundScore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RoundScoreDto {

    @Id
    private Long id;

    int movementScore;

    public static RoundScoreDto fromRoundScore(RoundScore roundScore) {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.movementScore = roundScore.getMovementScore();
        return roundScoreDto;
    }
}
