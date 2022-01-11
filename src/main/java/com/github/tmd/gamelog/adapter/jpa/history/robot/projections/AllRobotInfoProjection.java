package com.github.tmd.gamelog.adapter.jpa.history.robot.projections;

import java.util.UUID;
import javax.persistence.Column;
import lombok.NonNull;

public interface AllRobotInfoProjection {
  UUID getPlayerId();
  Integer getMaxHealth();
  Integer getMaxEnergy();
  Integer getEnergyRegen();
  Integer getAttackDamage();
  Integer getMiningSpeed();
  Integer getHealth();
  Integer getEnergy();
  Integer getHealthLevel();
  Integer getDamageLevel();
  Integer getMiningSpeedLevel();
  Integer getMiningLevel();
  Integer getEnergyLevel();
  Integer getEnergyRegenLevel();
  Integer getStorageLevel();
}
