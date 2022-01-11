package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.Set;
import java.util.UUID;

public interface OnlyPlayerAndMovementDifficultyAndRobotsProjection {
  UUID getPlayerId();
  Integer getMovementDifficulty();
  Set<UUID> getRobots();
}
