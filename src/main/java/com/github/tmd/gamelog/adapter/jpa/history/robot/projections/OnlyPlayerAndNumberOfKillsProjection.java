package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.UUID;

public interface OnlyPlayerAndNumberOfKillsProjection {
  UUID getPlayerId();
  Integer getNumberOfKills();
}
