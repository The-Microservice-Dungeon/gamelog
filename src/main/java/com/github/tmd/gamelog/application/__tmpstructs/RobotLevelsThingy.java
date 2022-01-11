package com.github.tmd.gamelog.application.__tmpstructs;

// TODO: find name and package
public record RobotLevelsThingy(
    Integer healthLevel,
    Integer damageLevel,
    Integer miningSpeedLevel,
    Integer miningLevel,
    Integer energyLevel,
    Integer energyRegenLevel,
    Integer storageLevel
) implements ValueableScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return healthLevel + damageLevel + miningSpeedLevel+ miningLevel + energyLevel + energyRegenLevel + storageLevel;
  }
}
