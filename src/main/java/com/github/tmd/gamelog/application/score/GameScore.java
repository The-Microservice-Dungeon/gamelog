package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.domain.score.core.ScorableScore;

public record GameScore(Double score) implements ScorableScore {

}
