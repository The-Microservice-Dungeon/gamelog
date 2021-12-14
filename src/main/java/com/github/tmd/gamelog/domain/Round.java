package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Round {
    private String gameId;
    private int roundNumber;
    private String roundId;

    public boolean equals(Round round) {
        return round.getRoundId().equals(this.getRoundId());
    }

    @Override
    public String toString() {
        return "Round{" +
                "gameId='" + gameId + '\'' +
                ", roundNumber=" + roundNumber +
                ", roundId='" + roundId + '\'' +
                '}';
    }
}
