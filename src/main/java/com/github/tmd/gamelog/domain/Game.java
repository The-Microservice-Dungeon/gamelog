package com.github.tmd.gamelog.domain;

import java.util.UUID;
import lombok.Data;

@Data
public class Game {
  private final GameId id;

  public record GameId(UUID id) {}
}
