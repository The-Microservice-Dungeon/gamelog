package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the RoundbasedTrophiesHook class.
 */
@SpringBootTest
@DirtiesContext
class RoundbasedTrophiesHookTest {

    UUID roundId = UUID.randomUUID();
    UUID gameId = UUID.randomUUID();
    @MockBean
    PlayerStatisticsRepositoryImpl playerStatisticsRepository;
    @Autowired
    PlayerJpaRepository playerJpaRepository;
    PlayerRepositoryImpl playerRepository;
    @Autowired
    TrophyJpaRepository trophyJpaRepository;
    TrophyRepository trophyRepository;

    @Autowired
    RoundbasedTrophiesHook roundbasedTrophiesHook;

    Player playerLoser = new Player(UUID.randomUUID(), "Loser", new HashSet<>());
    Player player3rdPlace = new Player(UUID.randomUUID(), "Third Place", new HashSet<>());
    Player player2ndPlace = new Player(UUID.randomUUID(), "Second Place", new HashSet<>());
    Player player1stPlace = new Player(UUID.randomUUID(), "First Place", new HashSet<>());

    @Test
    @Transactional
    void testAwardingOfRoundbasedTrophies() throws NoSuchElementException {
        initDependencies();
        runMethodUnderTest();

        playerLoser = playerRepository.findById(playerLoser.getId()).get();
        assertThat(playerLoser.getEarnedTrophies()).isEmpty();

        player3rdPlace = playerRepository.findById(player3rdPlace.getId()).get();
        Set<Trophy> bronzeTrophies = getBronzeTrophies();
        assertThat(playerTrophiesToTrophies(player3rdPlace.getEarnedTrophies())).isEqualTo(bronzeTrophies);

        player2ndPlace = playerRepository.findById(player2ndPlace.getId()).get();
        Set<Trophy> bronzeAndSilverTrophies = mergeTrophySets(bronzeTrophies, getSilverTrophies());
        assertThat(playerTrophiesToTrophies(player2ndPlace.getEarnedTrophies())).isEqualTo(bronzeAndSilverTrophies);

        player1stPlace = playerRepository.findById(player1stPlace.getId()).get();
        Set<Trophy> bronzeSilverAndGoldTrophies = mergeTrophySets(bronzeAndSilverTrophies, getGoldTrophies());
        assertThat(playerTrophiesToTrophies(player1stPlace.getEarnedTrophies())).isEqualTo(bronzeSilverAndGoldTrophies);
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
        Mockito.when(playerStatisticsRepository.getPlayerStatisticsForGame(gameId)).thenReturn(generatePlayerStatistics());
    }

    /**
     * Generate the test data for mocking the input to the method under test.
     * @return Set<PlayerStatistics> with the test data.
     */
    private Set<PlayerStatistics> generatePlayerStatistics() {
        Set<PlayerStatistics> playerStatistics = new HashSet<>();
        playerStatistics.add(new PlayerStatistics(playerLoser, 0, 0, 0, 0));
        playerStatistics.add(new PlayerStatistics(player3rdPlace, 1, 1, 1, 1));
        playerStatistics.add(new PlayerStatistics(player2ndPlace, 10, 1000, 25, 15));
        playerStatistics.add(new PlayerStatistics(player1stPlace, 25, 10000, 150, 40));
        return playerStatistics;
    }

    /**
     * Run the method under test.
     */
    private void runMethodUnderTest() {
        RoundStatusChangedEvent roundStatusChangedEvent = new RoundStatusChangedEvent(gameId, roundId, 1, RoundStatus.ENDED);
        roundbasedTrophiesHook.onRoundStatus(roundStatusChangedEvent, gameId, Instant.now());
    }

    /**
     * Turn a Set of PlayerTrophies into a Set of Trophies.
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
     * Merge two Sets of Trophies.
     * @param trophiesA First Set of Trophies.
     * @param trophiesB Second Set of Trophies
     * @return Merged Set of Trophies.
     */
    private Set<Trophy> mergeTrophySets(Set<Trophy> trophiesA, Set<Trophy> trophiesB) {
        Set<Trophy> mergedTrophies = new HashSet<>();
        mergedTrophies.addAll(trophiesA);
        mergedTrophies.addAll(trophiesB);
        return mergedTrophies;
    }

    /**
     * Get the Trophies of the Bronze rank.
     * @return Set of the Trophies of the Bronze rank.
     */
    private Set<Trophy> getBronzeTrophies() {
        Set<Trophy> bronzeTrophies = new HashSet<>();
        bronzeTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingBronzeTrophy));
        bronzeTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningBronzeTrophy));
        bronzeTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingBronzeTrophy));
        bronzeTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingBronzeTrophy));
        return bronzeTrophies;
    }

    /**
     * Get the Trophies of the Silver rank.
     * @return Set of the Trophies of the Silver rank.
     */
    private Set<Trophy> getSilverTrophies() {
        Set<Trophy> silverTrophies = new HashSet<>();
        silverTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingSilverTrophy));
        silverTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningSilverTrophy));
        silverTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingSilverTrophy));
        silverTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingSilverTrophy));
        return silverTrophies;
    }

    /**
     * Get the Trophies of the Gold rank.
     * @return Set of the Trophies of the Gold rank.
     */
    private Set<Trophy> getGoldTrophies() {
        Set<Trophy> goldTrophies = new HashSet<>();
        goldTrophies.add(trophyRepository.findByTrophyType(TrophyType.FightingGoldTrophy));
        goldTrophies.add(trophyRepository.findByTrophyType(TrophyType.MiningGoldTrophy));
        goldTrophies.add(trophyRepository.findByTrophyType(TrophyType.TradingGoldTrophy));
        goldTrophies.add(trophyRepository.findByTrophyType(TrophyType.TravelingGoldTrophy));
        return goldTrophies;
    }
}