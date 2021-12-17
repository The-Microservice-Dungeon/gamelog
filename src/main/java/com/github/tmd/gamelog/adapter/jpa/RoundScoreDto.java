package com.github.tmd.gamelog.adapter.jpa;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
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
