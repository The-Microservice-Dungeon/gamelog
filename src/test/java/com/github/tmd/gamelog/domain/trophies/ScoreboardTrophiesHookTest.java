package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the ScoreboardTrophiesHook class.
 */
@SpringBootTest
@DirtiesContext
class ScoreboardTrophiesHookTest {

    UUID gameId = UUID.randomUUID();

    @Autowired
    PlayerJpaRepository playerJpaRepository;
    PlayerRepositoryImpl playerRepository;
    @Autowired
    TrophyJpaRepository trophyJpaRepository;
    TrophyRepository trophyRepository;

    @MockBean
    ScoreboardRepositoryImpl scoreboardRepository;

    @Autowired
    ScoreboardTrophiesHook scoreboardTrophiesHook;

    Player playerLoser = new Player(UUID.randomUUID(), "Loser", new HashSet<>());
    Player player3rdPlace = new Player(UUID.randomUUID(), "Third Place", new HashSet<>());
    Player player2ndPlace = new Player(UUID.randomUUID(), "Second Place", new HashSet<>());
    Player player1stPlace = new Player(UUID.randomUUID(), "First Place", new HashSet<>());

    @Test
    @Transactional
    void testAwardingToQualifiedPlayers() throws NoSuchElementException {
        initDependencies();
        runMethodUnderTest();

        playerLoser = playerRepository.findById(playerLoser.getId()).get();
        assertThat(playerLoser.getEarnedTrophies()).isEmpty();

        player3rdPlace = playerRepository.findById(player3rdPlace.getId()).get();
        assertThat(playerTrophiesToTrophies(player3rdPlace.getEarnedTrophies())).isEqualTo(getThirdPlaceTrophies());

        player2ndPlace = playerRepository.findById(player2ndPlace.getId()).get();
        assertThat(playerTrophiesToTrophies(player2ndPlace.getEarnedTrophies())).isEqualTo(getSecondPlaceTrophies());

        player1stPlace = playerRepository.findById(player1stPlace.getId()).get();
        assertThat(playerTrophiesToTrophies(player1stPlace.getEarnedTrophies())).isEqualTo(getFirstPlaceTrophies());
    }

    /**
     * Init the required dependencies: database and mocked player statistics.
     */
    private void initDependencies() {
        initDatabase();
        initMocking();
    }

    /**
     * Initialize the repositories and fill the database with the required defaults and testing data.
     */
    private void initDatabase() {
        playerRepository = new PlayerRepositoryImpl(playerJpaRepository, new PlayerDtoMapper());
        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());

        trophyRepository.initRepository();

        playerRepository.upsert(playerLoser);
        playerRepository.upsert(player3rdPlace);
        playerRepository.upsert(player2ndPlace);
        playerRepository.upsert(player1stPlace);
    }

    /**
     * Initialize the mocking of the getPlayerStatisticsForGame function.
     * This is where the method under test gets its main input from.
     */
    private void initMocking() {
        Mockito.when(scoreboardRepository.getScoreboardByGameId(new Game.GameId(gameId))).thenReturn(generateScoreboard());
    }

    /**
     * Generate the Scoreboard with the test data.
     *
     * @return Scoreboard with the test data.
     */
    private Optional<Scoreboard> generateScoreboard() {
        Map<Player, AggregatedScore> gameScores = new HashMap<>();
        gameScores.put(player1stPlace, AggregatedScore.builder().fightingScore(100.0).miningScore(100.0).movementScore(100.0).tradingScore(100.0).build());
        gameScores.put(player2ndPlace, AggregatedScore.builder().fightingScore(75.0).miningScore(75.0).movementScore(75.0).tradingScore(75.0).build());
        gameScores.put(player3rdPlace, AggregatedScore.builder().fightingScore(50.0).miningScore(50.0).movementScore(50.0).tradingScore(50.0).build());
        gameScores.put(playerLoser, AggregatedScore.builder().fightingScore(25.0).miningScore(25.0).movementScore(25.0).tradingScore(25.0).build());
        Scoreboard scoreboard = Scoreboard.builder().gameScores(gameScores).game(new Game(new Game.GameId(gameId))).build();
        return Optional.ofNullable(scoreboard);
    }

    /**
     * Run the method under test.
     */
    private void runMethodUnderTest() {
        GameStatusEvent gameStatusEvent = new GameStatusEvent(gameId, GameStatus.ENDED);
        scoreboardTrophiesHook.onGameStatus(gameStatusEvent, Instant.now());
    }

    /**
     * Turn a Set of PlayerTrophies into a Set of Trophies.
     *
     * @param playerTrophies Set of PlayerTrophies.
     * @return Set of Trophies.
     */
    private Set<Trophy> playerTrophiesToTrophies(Set<PlayerTrophy> playerTrophies) {
        Set<Trophy> trophies = new HashSet<>();
        for (PlayerTrophy playerTrophy :
                playerTrophies) {
            trophies.add(playerTrophy.getTrophy());
        }
        return trophies;
    }

    /**
     * Get the Trophies for the third place.
     *
     * @return Set of the Trophies the third place.
     */
    private Set<Trophy> getThirdPlaceTrophies() {
        Set<Trophy> thirdPlaceTrophies = new HashSet<>();
        thirdPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.GameThirdPlaceTrophy));
        thirdPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingThirdPlaceTrophy));
        thirdPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningThirdPlaceTrophy));
        thirdPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingThirdPlaceTrophy));
        thirdPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingThirdPlaceTrophy));
        return thirdPlaceTrophies;
    }

    /**
     * Get the Trophies for the second place.
     *
     * @return Set of the Trophies the second place.
     */
    private Set<Trophy> getSecondPlaceTrophies() {
        Set<Trophy> secondPlaceTrophies = new HashSet<>();
        secondPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.GameSecondPlaceTrophy));
        secondPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingSecondPlaceTrophy));
        secondPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningSecondPlaceTrophy));
        secondPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingSecondPlaceTrophy));
        secondPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingSecondPlaceTrophy));
        return secondPlaceTrophies;
    }

    /**
     * Get the Trophies for the first place.
     *
     * @return Set of the Trophies the first place.
     */
    private Set<Trophy> getFirstPlaceTrophies() {
        Set<Trophy> firstPlaceTrophies = new HashSet<>();
        firstPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.GameFirstPlaceTrophy));
        firstPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingFirstPlaceTrophy));
        firstPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningFirstPlaceTrophy));
        firstPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingFirstPlaceTrophy));
        firstPlaceTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingFirstPlaceTrophy));
        return firstPlaceTrophies;
    }
}