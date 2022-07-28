package com.github.tmd.gamelog.adapter.metrics;

public class DungeonMetrics {
  public static final String DUNGEON_PREFIX = "tmd"; 
  
  public static final String ROUND_GAUGE = DUNGEON_PREFIX + "." + "game.round";
  public static final String ROUND_STATUS_INFO = DUNGEON_PREFIX + "." + "round.status.info";
 // public static final String TRADING_ITEM_PRICES = DUNGEON_PREFIX + "." + "trading.item.price";
  public static final String TRADING_RESOURCE_PRICES = DUNGEON_PREFIX + "." + "trading.resource.price";

  public static final String SCORE_TOTAL = DUNGEON_PREFIX + "." + "score.total";
  public static final String SCORE_TRADING = DUNGEON_PREFIX + "." + "score.trading";
  public static final String SCORE_FIGHTING = DUNGEON_PREFIX + "." + "score.fighting";
  public static final String SCORE_MINING = DUNGEON_PREFIX + "." + "score.mining";
  public static final String SCORE_MOVEMENT = DUNGEON_PREFIX + "." + "score.movement";
  public static final String SCORE_ROBOT = DUNGEON_PREFIX + "." + "score.robot";
}
