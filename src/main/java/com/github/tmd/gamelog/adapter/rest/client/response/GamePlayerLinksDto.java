package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record GamePlayerLinksDto(
    @JsonProperty("self") LinkDto selfLink,
    @JsonProperty("player") LinkDto playerLink
) {
  public UUID getPlayerId() {
    var path = this.selfLink().href().getPath();
    var idPart = path.substring(path.lastIndexOf('/') + 1);
    return UUID.fromString(idPart);
  }
}
