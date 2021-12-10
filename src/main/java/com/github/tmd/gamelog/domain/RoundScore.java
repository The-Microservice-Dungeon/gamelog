package com.github.tmd.gamelog.domain;

public class RoundScore {

    int movementScore = 0;

    public void increaseMovementScoreBy(int increase) {
        this.movementScore += increase;
    }

    public int getMovementScore() {
        return this.movementScore;
    }

}
