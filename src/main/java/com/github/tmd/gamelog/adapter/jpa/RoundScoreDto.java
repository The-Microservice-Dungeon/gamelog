package com.github.tmd.gamelog.adapter.jpa;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
public class RoundScoreDto implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    private String gameId;
    private String roundId;
    private int roundNumber;
    private String playerId;

    @Embedded
    private MovementScoreDto movementScore;

    @Override
    public String toString() {
        return "RoundScoreDto{" +
                "id=" + id +
                ", game='" + gameId + '\'' +
                ", round='" + roundId + '\'' +
                ", player='" + playerId + '\'' +
                ", movementScore=" + movementScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundScoreDto)) return false;

        RoundScoreDto that = (RoundScoreDto) o;

        if (movementScore != that.movementScore) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(gameId, that.gameId)) return false;
        if (!Objects.equals(roundId, that.roundId)) return false;
        return Objects.equals(playerId, that.playerId);
    }

    @Override
    public RoundScoreDto clone() {
        try {
            RoundScoreDto clone = (RoundScoreDto) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
