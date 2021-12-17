package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Round {
    private String gameId;
    private int roundNumber;
    private String roundId;

    public boolean equals(Round round) {
        return round.getRoundId().equals(this.getRoundId());
    }
}
