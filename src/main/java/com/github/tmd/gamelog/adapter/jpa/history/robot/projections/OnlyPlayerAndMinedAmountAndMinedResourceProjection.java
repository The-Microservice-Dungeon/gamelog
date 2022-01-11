package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import com.github.tmd.gamelog.adapter.jpa.history.robot.MiningHistoryResourceJpa;
import java.util.UUID;

public interface OnlyPlayerAndMinedAmountAndMinedResourceProjection {
  UUID getPlayerId();
  Integer getMinedAmount();
  MiningHistoryResourceJpa getResource();
}
