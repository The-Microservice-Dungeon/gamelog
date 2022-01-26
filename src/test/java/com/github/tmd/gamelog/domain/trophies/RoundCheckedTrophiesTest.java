package com.github.tmd.gamelog.domain.trophies;

import com.github.tmd.gamelog.adapter.jpa.PlayerJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.PlayerRepositoryImpl;
import com.github.tmd.gamelog.adapter.jpa.TrophyJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RoundCheckedTrophiesTest {

    @Autowired
    TrophyJpaRepository trophyJpaRepository;
    TrophyRepository trophyRepository;

    @Autowired
    PlayerJpaRepository playerJpaRepository;
    PlayerRepositoryImpl playerRepository;

    Player player1;

    UUID gameId = UUID.randomUUID();

    void setup() {
        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());
        trophyRepository.initRepository();
        playerRepository = new PlayerRepositoryImpl(playerJpaRepository, new PlayerDtoMapper());
        player1 = new Player(UUID.randomUUID(), "Max Mustermann", new HashSet<>());
        playerRepository.upsert(player1);
    }

    @Test
    void testTravelingTrophies() {
        setup();
        testAwardingConditionFulfilled(
                TrophyType.TravelingBronzeTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 0),
                new PlayerStatistics(player1, 0, 0, 0, 1)
        );

        testAwardingConditionFulfilled(
                TrophyType.TravelingSilverTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 14),
                new PlayerStatistics(player1, 0, 0, 0, 15)
        );

        testAwardingConditionFulfilled(
                TrophyType.TravelingGoldTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 39),
                new PlayerStatistics(player1, 0, 0, 0, 40)
        );
    }

    @Test
    void testMiningTrophies() {
        setup();
        testAwardingConditionFulfilled(
                TrophyType.MiningBronzeTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 0),
                new PlayerStatistics(player1, 0, 0, 1, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.MiningSilverTrophy,
                new PlayerStatistics(player1, 0, 0, 24, 0),
                new PlayerStatistics(player1, 0, 0, 25, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.MiningGoldTrophy,
                new PlayerStatistics(player1, 0, 0, 149, 0),
                new PlayerStatistics(player1, 0, 0, 150, 0)
        );
    }

    @Test
    void testTradingTrophies() {
        setup();
        testAwardingConditionFulfilled(
                TrophyType.TradingBronzeTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 0),
                new PlayerStatistics(player1, 0, 1, 0, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.TradingSilverTrophy,
                new PlayerStatistics(player1, 0, 999, 0, 0),
                new PlayerStatistics(player1, 0, 1000, 0, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.TradingGoldTrophy,
                new PlayerStatistics(player1, 0, 9999, 0, 0),
                new PlayerStatistics(player1, 0, 10000, 0, 0)
        );
    }

    @Test
    void testFightingTrophies() {
        setup();
        testAwardingConditionFulfilled(
                TrophyType.FightingBronzeTrophy,
                new PlayerStatistics(player1, 0, 0, 0, 0),
                new PlayerStatistics(player1, 1, 0, 0, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.FightingSilverTrophy,
                new PlayerStatistics(player1, 9, 0, 0, 0),
                new PlayerStatistics(player1, 10, 0, 0, 0)
        );

        testAwardingConditionFulfilled(
                TrophyType.FightingGoldTrophy,
                new PlayerStatistics(player1, 24, 0, 0, 0),
                new PlayerStatistics(player1, 25, 0, 0, 0)
        );
    }

    /**
     * Test the awardingConditionFulfilled function of a RoundCheckedTrophy.
     * Get the Trophy to check from the repo and check against an insufficient
     * and a sufficient PlayerStatistics. Ideally, use the min. value of the condition
     * for the sufficient PS and min. value - 1 for the insufficient one.
     *
     * @param trophyType                   TrophyType Type of the trophy (translates into the matching Trophy subclass).
     * @param playerStatisticsInsufficient PlayerStatistics that doesn't fulfill the awarding condition.
     * @param playerStatisticsSufficient   PlayerStatistics that fulfills the awarding condition.
     */
    void testAwardingConditionFulfilled(TrophyType trophyType, PlayerStatistics playerStatisticsInsufficient, PlayerStatistics playerStatisticsSufficient) {
        Trophy trophy = trophyRepository.findByTrophyType(trophyType);

        assertThat(playerStatisticsInsufficient.getPlayer().trophyAlreadyEarned(trophy, gameId)).isFalse();
        assertThat(((RoundCheckedTrophy) trophy).awardingConditionFulfilled(playerStatisticsInsufficient)).isFalse();

        assertThat(playerStatisticsSufficient.getPlayer().trophyAlreadyEarned(trophy, gameId)).isFalse();
        assertThat(((RoundCheckedTrophy) trophy).awardingConditionFulfilled(playerStatisticsSufficient)).isTrue();

        playerStatisticsSufficient.getPlayer().awardTrophy(trophy, gameId);
        assertThat(playerStatisticsSufficient.getPlayer().trophyAlreadyEarned(trophy, gameId)).isTrue();
    }

}
