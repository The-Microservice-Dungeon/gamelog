package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResourceType {
  @JsonAlias({"coal"})
  @JsonProperty("COAL")
  COAL,
  @JsonAlias({"iron"})
  @JsonProperty("IRON")
  IRON,
  @JsonAlias({"gem"})
  @JsonProperty("GEM")
  GEM,
  @JsonAlias({"gold"})
  @JsonProperty("GOLD")
  GOLD,
  @JsonAlias({"platin"})
  @JsonProperty("PLATIN")
  PLATIN;
}
