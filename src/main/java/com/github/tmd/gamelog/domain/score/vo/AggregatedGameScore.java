package com.github.tmd.gamelog.domain.score.vo;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record AggregatedGameScore(Double score) implements ScorableScore {

}
