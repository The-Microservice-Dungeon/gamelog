package com.github.tmd.gamelog.application.__tmpstructs;

// TODO: find a proper name and package
public record ResourceMinedThingy(
    Integer rarity,
    Integer amount
) implements ValueableScoreAttribute<Integer> {

  @Override
  public Integer value() {
    return rarity * amount;
  }
}
