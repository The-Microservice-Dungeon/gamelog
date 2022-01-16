package com.github.tmd.gamelog.domain.score.entity;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record GameScore(Double score) implements ScorableScore {

}
