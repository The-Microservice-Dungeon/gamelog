package com.github.tmd.gamelog.adapter.jpa;


import com.github.tmd.gamelog.domain.RoundScore;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RoundScoreDto {

    @Id
    @GeneratedValue
    private Long id;

    private String game;
    private String round;
    private String player;

    private int movementScore;

    public static RoundScoreDto fromRoundScore(RoundScore roundScore) {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.setMovementScore(roundScore.getMovementScore());
        roundScoreDto.setGame(roundScore.getGame());
        roundScoreDto.setRound(roundScore.getRound());
        roundScoreDto.setPlayer(roundScore.getPlayer());
        return roundScoreDto;
    }

    @Override
    public String toString() {
        return "RoundScoreDto{" +
                "id=" + id +
                ", game='" + game + '\'' +
                ", round='" + round + '\'' +
                ", player='" + player + '\'' +
                ", movementScore=" + movementScore +
                '}';
    }
}
