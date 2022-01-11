package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.UUID;

public interface OnlyPlayerAndNumberOfVictimsProjection {
  UUID getPlayerId();
  Integer getNumberOfVictims();
}
