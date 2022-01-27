package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import com.github.tmd.gamelog.domain.trophies.Trophy;

/**
 * Class that extends Trophy with a function to update a player on the Scoreboard.
 */
public abstract class ScoreboardChangingTrophy extends Trophy {

    void awardTrophyAndUpdateScoreboard(Scoreboard scoreboard, Player player) {
        AggregatedScore score = scoreboard.getGameScores().get(player);
        scoreboard.getGameScores().remove(player);
        player.awardTrophy(this, scoreboard.getGame().getId().id());
        scoreboard.getGameScores().put(player, score);
    }

}
