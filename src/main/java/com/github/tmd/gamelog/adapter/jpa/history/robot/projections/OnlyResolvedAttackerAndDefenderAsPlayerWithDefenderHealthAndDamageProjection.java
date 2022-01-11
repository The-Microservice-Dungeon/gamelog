package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.UUID;

public interface OnlyResolvedAttackerAndDefenderAsPlayerWithDefenderHealthAndDamageProjection {
  UUID getAttackerPlayerId();
  UUID getDefenderPlayerId();
  Integer getDamage();
  Integer getDefenderHealth();
}
