package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.jpa.PlayerJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.PlayerRepositoryImpl;
import com.github.tmd.gamelog.adapter.jpa.TrophyJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the ScoreboardTrophies.
 */
@SpringBootTest
@Transactional
public class ScoreboardTrophiesTest {

    @Autowired
    TrophyJpaRepository trophyJpaRepository;
    TrophyRepository trophyRepository;

    @Autowired
    PlayerJpaRepository playerJpaRepository;
    PlayerRepositoryImpl playerRepository;

    Player player1st = new Player(UUID.randomUUID(), "First");
    Player player2nd = new Player(UUID.randomUUID(), "Second");
    Player player3rd = new Player(UUID.randomUUID(), "Third");
    Player playerLoser = new Player(UUID.randomUUID(), "Loser");

    UUID gameId = UUID.randomUUID();

    /**
     * Setup repositories and players.
     */
    void setup() {
        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());
        trophyRepository.initRepository();
        playerRepository = new PlayerRepositoryImpl(playerJpaRepository, new PlayerDtoMapper());
        playerRepository.upsert(player1st);
        playerRepository.upsert(player2nd);
        playerRepository.upsert(player3rd);
        playerRepository.upsert(playerLoser);
    }

    @Test
    void testGameTrophies() {
        setup();

        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1st, AggregatedScore.builder().fightingScore(100.0).miningScore(100.0).movementScore(100.0).tradingScore(100.0).build());
        gameScores.put(player2nd, AggregatedScore.builder().fightingScore(75.0).miningScore(75.0).movementScore(75.0).tradingScore(75.0).build());
        gameScores.put(player3rd, AggregatedScore.builder().fightingScore(50.0).miningScore(50.0).movementScore(50.0).tradingScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().fightingScore(25.0).miningScore(25.0).movementScore(25.0).tradingScore(25.0).build());
        Scoreboard scoreboard = generateScoreboard(gameScores);

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.GameThirdPlaceTrophy, player3rd, new ArrayList<>(Arrays.asList(player1st, player2nd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.GameSecondPlaceTrophy, player2nd, new ArrayList<>(Arrays.asList(player1st, player3rd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.GameFirstPlaceTrophy, player1st, new ArrayList<>(Arrays.asList(player2nd, player3rd, playerLoser)));

    }

    @Test
    void testFightingTrophies() {
        setup();

        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1st, AggregatedScore.builder().fightingScore(100.0).build());
        gameScores.put(player2nd, AggregatedScore.builder().fightingScore(75.0).build());
        gameScores.put(player3rd, AggregatedScore.builder().fightingScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().fightingScore(25.0).build());
        Scoreboard scoreboard = generateScoreboard(gameScores);

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.FightingThirdPlaceTrophy, player3rd, new ArrayList<>(Arrays.asList(player1st, player2nd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.FightingSecondPlaceTrophy, player2nd, new ArrayList<>(Arrays.asList(player1st, player3rd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.FightingFirstPlaceTrophy, player1st, new ArrayList<>(Arrays.asList(player2nd, player3rd, playerLoser)));

    }

    @Test
    void testMiningTrophies() {
        setup();

        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1st, AggregatedScore.builder().miningScore(100.0).build());
        gameScores.put(player2nd, AggregatedScore.builder().miningScore(75.0).build());
        gameScores.put(player3rd, AggregatedScore.builder().miningScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().miningScore(25.0).build());
        Scoreboard scoreboard = generateScoreboard(gameScores);

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.MiningThirdPlaceTrophy, player3rd, new ArrayList<>(Arrays.asList(player1st, player2nd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.MiningSecondPlaceTrophy, player2nd, new ArrayList<>(Arrays.asList(player1st, player3rd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.MiningFirstPlaceTrophy, player1st, new ArrayList<>(Arrays.asList(player2nd, player3rd, playerLoser)));

    }

    @Test
    void testTradingTrophies() {
        setup();

        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1st, AggregatedScore.builder().tradingScore(100.0).build());
        gameScores.put(player2nd, AggregatedScore.builder().tradingScore(75.0).build());
        gameScores.put(player3rd, AggregatedScore.builder().tradingScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().tradingScore(25.0).build());
        Scoreboard scoreboard = generateScoreboard(gameScores);

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TradingThirdPlaceTrophy, player3rd, new ArrayList<>(Arrays.asList(player1st, player2nd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TradingSecondPlaceTrophy, player2nd, new ArrayList<>(Arrays.asList(player1st, player3rd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TradingFirstPlaceTrophy, player1st, new ArrayList<>(Arrays.asList(player2nd, player3rd, playerLoser)));

    }

    @Test
    void testTravelingTrophies() {
        setup();

        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1st, AggregatedScore.builder().movementScore(100.0).build());
        gameScores.put(player2nd, AggregatedScore.builder().movementScore(75.0).build());
        gameScores.put(player3rd, AggregatedScore.builder().movementScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().movementScore(25.0).build());
        Scoreboard scoreboard = generateScoreboard(gameScores);

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TravelingThirdPlaceTrophy, player3rd, new ArrayList<>(Arrays.asList(player1st, player2nd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TravelingSecondPlaceTrophy, player2nd, new ArrayList<>(Arrays.asList(player1st, player3rd, playerLoser)));

        testAwardingToQualifiedPlayer(scoreboard, TrophyType.TravelingFirstPlaceTrophy, player1st, new ArrayList<>(Arrays.asList(player2nd, player3rd, playerLoser)));

    }

    /**
     * Test awarding the trophy to the player qualified by the game score.
     * Assert that the qualified player has received the trophy and that the unqualified ones haven't.
     *
     * @param scoreboard         Scoreboard with the game scores.
     * @param trophyType         Type of the Trophy to award.
     * @param qualifiedPlayer    The player that is qualified to receive the trophy.
     * @param unqualifiedPlayers The list of players that aren't qualified to receive the trophy.
     */
    private void testAwardingToQualifiedPlayer(Scoreboard scoreboard, TrophyType trophyType, Player qualifiedPlayer, List<Player> unqualifiedPlayers) {
        Trophy trophy = trophyRepository.findByTrophyType(trophyType);

        assertThat(qualifiedPlayer.trophyAlreadyEarned(trophy, gameId)).isFalse();

        ((ScoreboardTrophy) trophy).awardToQualifiedPlayer(scoreboard);
        assertThat(qualifiedPlayer.trophyAlreadyEarned(trophy, gameId)).isTrue();

        for (Player unqualifiedPlayer : unqualifiedPlayers) {
            assertThat(unqualifiedPlayer.trophyAlreadyEarned(trophy, scoreboard.getGame().getId().id())).isFalse();
        }
    }

    /**
     * Generate a new Scoreboard.
     *
     * @param gameScores GameScore for the new Scoreboard.
     * @return new Scoreboard.
     */
    private Scoreboard generateScoreboard(Map<Player, AggregatedScore> gameScores) {
        Game game = new Game(new Game.GameId(gameId));
        return Scoreboard.builder().game(game).gameScores(gameScores).build();
    }

}
