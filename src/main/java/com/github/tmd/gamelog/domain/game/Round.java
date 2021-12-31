package com.github.tmd.gamelog.domain.game;

import java.util.UUID;
import lombok.Data;

@Data
public class Round {
  private final RoundId roundId;
  private final Integer roundNumber;

  public record RoundId(UUID roundId) { }
}
