package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.history.robot.FightHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.FightHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.robot.RobotHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.RobotHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerStatisticsDtoMapper;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for class PlayerStatisticsRepository.
 */
@SpringBootTest
@Transactional
public class PlayerStatisticsRepositoryTest {

    UUID player1Id = UUID.randomUUID();
    UUID player1RobotId = UUID.randomUUID();
    UUID player2Id = UUID.randomUUID();
    UUID player2RobotId = UUID.randomUUID();
    UUID planet1ID = UUID.randomUUID();
    UUID gameId = UUID.randomUUID();
    UUID round1Id = UUID.randomUUID();

    Player player1;
    Player player2;

    @Autowired
    private PlayerStatisticsJpaRepository playerStatisticsJpaRepository;
    private PlayerStatisticsDtoMapper playerStatisticsDtoMapper;
    private PlayerStatisticsRepositoryImpl playerStatisticsRepository;

    @Autowired
    private FightHistoryJpaRepository fightHistoryJpaRepository;

    @Autowired
    private RobotHistoryJpaRepository robotHistoryJpaRepository;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;
    private PlayerRepositoryImpl playerRepository;

    @Autowired
    private TrophyJpaRepository trophyJpaRepository;
    private TrophyRepository trophyRepository;

    @BeforeEach
    void beforeEach() {
        playerStatisticsDtoMapper = new PlayerStatisticsDtoMapper();
        playerStatisticsRepository = new PlayerStatisticsRepositoryImpl(playerStatisticsJpaRepository, playerStatisticsDtoMapper);
        playerRepository = new PlayerRepositoryImpl(playerJpaRepository, new PlayerDtoMapper());

        trophyRepository = new TrophyRepository(trophyJpaRepository, new TrophyDtoMapper());
        trophyRepository.initRepository();
    }

    @Test
    @Transactional
    void testPlayerStatisticsWithEmptyStatistics(){
        player1 = new Player(player1Id, "Max Mustermann");
        Trophy testTrophy = trophyRepository.findAll().get(0);
        player1.awardTrophy(testTrophy, gameId);
        PlayerTrophy testPlayerTrophy = player1.getEarnedTrophies().stream().findFirst().get();
        addPlayer(player1);

        Set<PlayerStatistics> playerStatisticsSet = playerStatisticsRepository.getPlayerStatisticsForGame(gameId);

        assertThat(playerStatisticsSet.size()).isEqualTo(1);

        PlayerStatistics player1Statistics = playerStatisticsSet.stream().filter(element -> element.getPlayer().getId() == player1Id).findAny().orElse(new PlayerStatistics());

        assertThat(player1Statistics.getPlayer()).isEqualTo(player1);
        assertThat(player1.getEarnedTrophies().contains(testPlayerTrophy)).isTrue();
        assertThat(player1Statistics.getKills()).isEqualTo(0);
        assertThat(player1Statistics.getEarnedMoney()).isEqualTo(0);
        assertThat(player1Statistics.getMinedResources()).isEqualTo(0);
        assertThat(player1Statistics.getTraveledDistance()).isEqualTo(0);
    }

    @Test
    @Disabled
    void testGetPlayerStatisticsForGame() {
        Player player1 = new Player(player1Id, "Max Mustermann");
        Player player2 = new Player(player2Id, "Arno Nühm");
        addPlayer(player1);
        addPlayer(player2);

        addRobotEvent(round1Id, player1RobotId, player1Id, planet1ID, true);
        addRobotEvent(round1Id, player2RobotId, player2Id, planet1ID, true);

        FightHistoryJpa robot1DestroysRobot2 = new FightHistoryJpa(UUID.randomUUID(), player1RobotId, player2RobotId, 0, Instant.now());
        fightHistoryJpaRepository.save(robot1DestroysRobot2);

    }

    private void addRobotEvent(UUID roundId, UUID robotId, UUID playerId, UUID planetId, boolean alive) {
        RobotHistoryJpa robotEvent = new RobotHistoryJpa(roundId, robotId, playerId, planetId, alive,
                100, 100, 100, 100, 100, 100, 100,
                100, 100, 100, 100, 100, 100,
                100);
        robotHistoryJpaRepository.save(robotEvent);
    }

    private void addPlayer(Player player) {
        playerRepository.upsert(player);
    }

}
