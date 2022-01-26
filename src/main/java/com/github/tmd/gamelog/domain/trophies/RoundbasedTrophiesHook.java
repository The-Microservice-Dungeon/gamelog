package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerStatisticsDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PlayerStatisticsJpaRepository playerStatisticsJpaRepository;
    PlayerStatisticsRepositoryImpl playerStatisticsRepository;

    @Autowired
    PlayerJpaRepository playerJpaRepository;
    PlayerRepositoryImpl playerRepository;

    @Autowired
    TrophyJpaRepository trophyJpaRepository;
    TrophyRepository trophyRepository;

    /**
     * Default constructor.
     */
    public RoundbasedTrophiesHook() {
        playerStatisticsRepository = new PlayerStatisticsRepositoryImpl(playerStatisticsJpaRepository, new PlayerStatisticsDtoMapper());
        playerRepository = new PlayerRepositoryImpl(playerJpaRepository, new PlayerDtoMapper());
        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());
    }

    /**
     * Constructor for unit tests.
     * Allows for plugging in a mocked PlayerStatisticsRepositoryImpl.
     * @param playerStatisticsRepository Repository of the PlayerStatistics.
     * @param playerRepository Repository of the Players.
     * @param trophyRepository Repository of the Trophies.
     */
    public RoundbasedTrophiesHook(PlayerStatisticsRepositoryImpl playerStatisticsRepository, PlayerRepositoryImpl playerRepository, TrophyRepository trophyRepository) {
        this.playerStatisticsRepository = playerStatisticsRepository;
        this.playerRepository = playerRepository;
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
