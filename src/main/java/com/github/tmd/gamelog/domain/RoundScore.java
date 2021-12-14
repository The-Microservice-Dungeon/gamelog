package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RoundScore {
    private Player player;
    private Round round;
    private int movementScore;

    public RoundScore(Player player, Round round) {
        this();

        this.player = player;
        this.round = round;
    }

    public RoundScore() {
        this.movementScore = 0;
    }

    public void increaseMovementScoreBy(int increase) {
        this.movementScore += increase;
    }

    public int getMovementScore() {
        return this.movementScore;
    }

    @Override
    public String toString() {
        return "RoundScore{" +
                "player=" + player +
                ", round=" + round +
                ", movementScore=" + movementScore +
                '}';
    }
}
