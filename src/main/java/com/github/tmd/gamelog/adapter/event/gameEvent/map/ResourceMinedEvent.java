package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ResourceMinedEvent(
  @JsonProperty("planet_id") UUID planetId,
  @JsonProperty("resource_id") UUID resourceId,
  @JsonProperty("resource_type") ResourceType resourceType,
  @JsonProperty("amount_mined") Integer amountMined,
  @JsonProperty("amount_left") Integer amountLeft
){}
