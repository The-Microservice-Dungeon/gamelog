package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.ValuebleScoreAttribute;

public record RobotLevelsScoreAttribute(
    Integer healthLevel,
    Integer damageLevel,
    Integer miningSpeedLevel,
    Integer miningLevel,
    Integer energyLevel,
    Integer energyRegenLevel,
    Integer storageLevel
) implements ValuebleScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return healthLevel + damageLevel + miningSpeedLevel+ miningLevel + energyLevel + energyRegenLevel + storageLevel;
  }
}
