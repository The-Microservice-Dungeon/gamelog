package com.github.tmd.gamelog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record Player(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {

}
