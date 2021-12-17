package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlanetExploredEvent(@JsonProperty("planet") PlanetExploredPlanetPayload planet) {}
