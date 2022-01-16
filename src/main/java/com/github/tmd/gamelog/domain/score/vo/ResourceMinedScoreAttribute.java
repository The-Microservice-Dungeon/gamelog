package com.github.tmd.gamelog.domain.score.vo;

import com.github.tmd.gamelog.domain.score.core.ValuebleScoreAttribute;

public record ResourceMinedScoreAttribute(
    ResourceRarity rarity,
    Integer amount
) implements ValuebleScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return rarity.rarity() * amount;
  }
}
