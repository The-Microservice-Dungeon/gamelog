package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.Score.MovementScore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoundScore {
    private Player player;
    private Round round;
    private MovementScore movementScore;

    public RoundScore(Player player, Round round) {
        this();

        this.player = player;
        this.round = round;
    }

    public RoundScore() {
        this.movementScore = new MovementScore();
    }
}
