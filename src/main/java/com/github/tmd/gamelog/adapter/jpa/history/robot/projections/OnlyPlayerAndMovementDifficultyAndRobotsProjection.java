package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.UUID;

public interface OnlyPlayerAndMovementDifficultyAndRobotsProjection {
  UUID getPlayerId();
  Integer getOverallMovementDifficulty();
  Integer getNumberOfMovedRobots();
}
