package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;

public enum MiningHistoryResourceJpa {
  COAL,
  IRON,
  GEM,
  GOLD,
  PLATIN;

  public static MiningHistoryResourceJpa fromResourceType(ResourceType resourceType) {
    return switch (resourceType) {
      case COAL -> MiningHistoryResourceJpa.COAL;
      case IRON -> MiningHistoryResourceJpa.IRON;
      case GEM -> MiningHistoryResourceJpa.GEM;
      case GOLD -> MiningHistoryResourceJpa.GOLD;
      case PLATIN -> MiningHistoryResourceJpa.PLATIN;
    };
  }
}
