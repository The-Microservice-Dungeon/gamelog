package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.core.ValuebleScoreAttribute;

public record ResourceMinedScoreAttribute(
    ResourceRarity rarity,
    Integer amount
) implements ValuebleScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return rarity.rarity() * amount;
  }
}
