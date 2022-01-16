package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.domain.score.entity.AggregatedRoundScore;

/**
 * Interface for round-checked Trophies.
 * These Trophies can be earned in any round of the game.
 */
public interface RoundCheckedTrophy {

    /**
     * Check if the player (embedded in RoundScore) has fulfilled the conditions to earn this trophy
     * by this round of the game.
     * If true, the Trophy can be awarded to the player via the Player.awardTrophy() method.
     * @param roundScore RoundScore of the most recent round of the game.
     * @return True if the player has fulfilled the condition in or by this round. Else false.
     */
    public Boolean awardingConditionFulfilled(AggregatedRoundScore roundScore);

}
