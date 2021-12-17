package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerStatusChangedEvent(
    @JsonProperty("userId") UUID userId,
    @JsonProperty("userName") String userName,
    @JsonProperty("lobbyAction") LobbyAction lobbyAction
) {}
