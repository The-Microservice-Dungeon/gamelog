package com.github.tmd.gamelog.adapter.jpa.dto;


import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class RoundScoreDto implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    private String gameId;
    private String roundId;
    private int roundNumber;
    private UUID playerId;

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
