package com.github.tmd.gamelog.adapter.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static RoundScoreDto fromRoundScore(RoundScore roundScore) {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        //TODO
        return roundScoreDto;
    }
}
