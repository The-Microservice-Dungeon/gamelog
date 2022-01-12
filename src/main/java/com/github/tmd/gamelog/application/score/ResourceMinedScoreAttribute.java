package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.ValueableScoreAttribute;

public record ResourceMinedScoreAttribute(
    ResourceRarity rarity,
    Integer amount
) implements ValueableScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return rarity.rarity() * amount;
  }
}
