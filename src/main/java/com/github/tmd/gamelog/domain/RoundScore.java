package com.github.tmd.gamelog.domain;

public class RoundScore {

    int movementScore;

    public RoundScore() {
        this.movementScore = 0;
    }

    public void increaseMovementScoreBy(int increase) {
        this.movementScore += increase;
    }

    public int getMovementScore() {
        return this.movementScore;
    }

}
