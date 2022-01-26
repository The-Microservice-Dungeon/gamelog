package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.domain.score.entity.Scoreboard;

/**
 * Interface for Scoreboard Trophies.
 * These Trophies are awarded for the placement
 * on the scoreboard at the end of the game.
 */
public interface ScoreboardTrophy {

    void awardToQualifiedPlayer(Scoreboard scoreboard);

}
