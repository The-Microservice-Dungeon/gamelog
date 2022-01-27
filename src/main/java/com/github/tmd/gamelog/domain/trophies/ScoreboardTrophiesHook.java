package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.repository.ScoreboardRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Game lifecycle hook that awards the ScoreboardTrophies to the Players.
 */
@Component
public class ScoreboardTrophiesHook implements GameLifecycleHook {
    private final ScoreboardRepository scoreboardJpaRepository;
    private final ScoreboardRepositoryImpl scoreboardRepository;

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerRepositoryImpl playerRepository;

    private final TrophyJpaRepository trophyJpaRepository;
    private final TrophyRepository trophyRepository;

    public ScoreboardTrophiesHook(
            ScoreboardRepository scoreboardJpaRepository,
            ScoreboardRepositoryImpl scoreboardRepository,
            PlayerJpaRepository playerJpaRepository,
            PlayerRepositoryImpl playerRepository,
            TrophyJpaRepository trophyJpaRepository,
            TrophyRepository trophyRepository) {
        this.scoreboardJpaRepository = scoreboardJpaRepository;
        this.scoreboardRepository = scoreboardRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.playerRepository = playerRepository;
        this.trophyJpaRepository = trophyJpaRepository;
        this.trophyRepository = trophyRepository;
    }

    /**
     * Method that plugs into the game lifecycle.
     * Awards the Trophies to the Players after the game has ended.
     *
     * @param event     Event that contains the game status change.
     * @param timestamp Timestamp of the status change.
     */
    @Override
    public void onGameStatus(GameStatusEvent event, Instant timestamp) {
        if (event.status() == GameStatus.ENDED) {
            awardScoreboardTrophies(event.gameId());
        }
    }

    /**
     * Award the ScoreboardTrophies to the players that have qualified.
     *
     * @param gameId ID of the current game.
     */
    private void awardScoreboardTrophies(UUID gameId) {
        Scoreboard scoreboard = scoreboardRepository.getScoreboardByGameId(new Game.GameId(gameId)).get();
        ArrayList<Trophy> trophies = trophyRepository.findAll();

        for (Trophy trophy : trophies) {
            if (trophy instanceof ScoreboardTrophy) {
                ((ScoreboardTrophy) trophy).awardToQualifiedPlayer(scoreboard);
            }
        }

        for (Player player : scoreboard.getGameScores().keySet()) {
            playerRepository.upsert(player);
        }
    }
}
