package com.github.tmd.gamelog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TestMessage(@JsonProperty("test") String test) {

}
