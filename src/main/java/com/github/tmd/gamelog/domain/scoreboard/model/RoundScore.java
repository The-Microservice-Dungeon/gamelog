package com.github.tmd.gamelog.domain.scoreboard.model;

import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import lombok.Data;

// TODO: This should be a map Map<Round, RoundScore>
public record RoundScore(Round round) { }
