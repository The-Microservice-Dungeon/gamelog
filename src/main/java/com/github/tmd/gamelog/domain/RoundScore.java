package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundScore {

    private int movementScore;
    private String game;
    private String round;
    private String player;

    public RoundScore() {
        this.movementScore = 0;
    }

    public void increaseMovementScoreBy(int increase) {
        this.movementScore += increase;
    }

    public int getMovementScore() {
        return this.movementScore;
    }

    public static RoundScore fromRoundScoreDto(RoundScoreDto roundScoreDto) {
        RoundScore result = new RoundScore();
        result.setMovementScore(roundScoreDto.getMovementScore());
        result.setGame(roundScoreDto.getGame());
        result.setRound(roundScoreDto.getRound());
        result.setPlayer(roundScoreDto.getPlayer());

        return result;
    }

    @Override
    public String toString() {
        return "RoundScore{" +
                "movementScore=" + movementScore +
                ", game='" + game + '\'' +
                ", round='" + round + '\'' +
                ", player='" + player + '\'' +
                '}';
    }
}
