package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Game lifecycle hook that awards the RoundCheckedTrophies to the Players.
 */
@Component
public class RoundbasedTrophiesHook implements GameLifecycleHook {
    private final PlayerStatisticsJpaRepository playerStatisticsJpaRepository;
    private final PlayerStatisticsRepositoryImpl playerStatisticsRepository;

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerRepositoryImpl playerRepository;

    private final TrophyJpaRepository trophyJpaRepository;
    private final TrophyRepository trophyRepository;

    public RoundbasedTrophiesHook(
        PlayerStatisticsJpaRepository playerStatisticsJpaRepository,
        PlayerStatisticsRepositoryImpl playerStatisticsRepository,
        PlayerJpaRepository playerJpaRepository,
        PlayerRepositoryImpl playerRepository,
        TrophyJpaRepository trophyJpaRepository,
        TrophyRepository trophyRepository) {
        this.playerStatisticsJpaRepository = playerStatisticsJpaRepository;
        this.playerStatisticsRepository = playerStatisticsRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.playerRepository = playerRepository;
        this.trophyJpaRepository = trophyJpaRepository;
        this.trophyRepository = trophyRepository;
    }

    /**
     * Method that plugs into the game lifecycle.
     * Awards the Trophies to the Players when a round has ended.
     * @param event Event that contains the round status change.
     * @param gameId ID of the game.
     * @param timestamp Timestamp of the status change.
     */
    @Override
    public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
        if (event.roundStatus() == RoundStatus.ENDED) {
            awardTrophiesToPlayers(gameId);
        }
    }

    /**
     * Award all newly earned Trophies to the players.
     *
     * @param gameId UUID of the ongoing game.
     */
    private void awardTrophiesToPlayers(UUID gameId) {
        Set<PlayerStatistics> playerStatistics = playerStatisticsRepository.getPlayerStatisticsForGame(gameId);
        ArrayList<Trophy> trophies = trophyRepository.findAll();

        for (PlayerStatistics playerStatistics1 :
                playerStatistics) {
            awardTrophiesToPlayerIfEarned(gameId, trophies, playerStatistics1);
        }
    }

    /**
     * Check if a Player (embedded in PlayerStatistics) has fulfilled
     * the conditions for new Trophies. Award the earned Trophies to the Player.
     *
     * @param gameId           UUID of the ongoing game.
     * @param trophies         ArrayList of the Trophies that can be earned.
     * @param playerStatistics PlayerStatistics containing the Player and the player statistics (kills, earned money, etc.)
     */
    private void awardTrophiesToPlayerIfEarned(UUID gameId, ArrayList<Trophy> trophies, PlayerStatistics playerStatistics) {
        for (Trophy trophy : trophies) {
            if (trophy instanceof RoundCheckedTrophy && ((RoundCheckedTrophy) trophy).awardingConditionFulfilled(playerStatistics)) {
                playerStatistics.getPlayer().awardTrophy(trophy, gameId);
                playerRepository.upsert(playerStatistics.getPlayer());
            }
        }
    }
}
