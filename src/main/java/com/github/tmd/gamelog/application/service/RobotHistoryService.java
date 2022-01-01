package com.github.tmd.gamelog.application.service;

import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;
import com.github.tmd.gamelog.adapter.jpa.history.robot.FightHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.FightHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.robot.MiningHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.MiningHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.robot.MiningHistoryResourceJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.MovementHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.MovementHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.robot.PlanetBlockHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.PlanetBlockHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.robot.RobotHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.robot.RobotHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.rest_client.client.RobotRestClient;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RobotHistoryService {

  private final FightHistoryJpaRepository fightHistoryJpaRepository;
  private final MiningHistoryJpaRepository miningHistoryJpaRepository;
  private final MovementHistoryJpaRepository movementHistoryJpaRepository;
  private final PlanetBlockHistoryJpaRepository planetBlockHistoryJpaRepository;
  private final RobotHistoryJpaRepository robotHistoryJpaRepository;
  private final RobotRestClient robotRestClient;

  public RobotHistoryService(
      FightHistoryJpaRepository fightHistoryJpaRepository,
      MiningHistoryJpaRepository miningHistoryJpaRepository,
      MovementHistoryJpaRepository movementHistoryJpaRepository,
      PlanetBlockHistoryJpaRepository planetBlockHistoryJpaRepository,
      RobotHistoryJpaRepository robotHistoryJpaRepository,
      RobotRestClient robotRestClient) {
    this.fightHistoryJpaRepository = fightHistoryJpaRepository;
    this.miningHistoryJpaRepository = miningHistoryJpaRepository;
    this.movementHistoryJpaRepository = movementHistoryJpaRepository;
    this.planetBlockHistoryJpaRepository = planetBlockHistoryJpaRepository;
    this.robotHistoryJpaRepository = robotHistoryJpaRepository;
    this.robotRestClient = robotRestClient;
  }

  @Transactional
  public void insertFightHistory(UUID transactionId, UUID attacker, UUID defender,
      Integer defenderHealth) {
    this.fightHistoryJpaRepository.save(
        new FightHistoryJpa(transactionId, attacker, defender, defenderHealth));
  }

  @Transactional
  public void insertMiningHistory(UUID transactionId, Integer minedAmount, ResourceType type) {
    this.miningHistoryJpaRepository.save(new MiningHistoryJpa(transactionId, minedAmount,
        MiningHistoryResourceJpa.fromResourceType(type)));
  }

  @Transactional
  public void insertMovementHistory(UUID transactionId, Iterable<UUID> robots, UUID planetId,
      Integer movementDifficulty) {
    this.movementHistoryJpaRepository.save(new MovementHistoryJpa(transactionId,
        StreamSupport.stream(robots.spliterator(), false).collect(
            Collectors.toSet()), planetId, movementDifficulty));
  }

  @Transactional
  public void insertPlanetBlockHistory(UUID transactionId, UUID planetId) {
    this.planetBlockHistoryJpaRepository.save(new PlanetBlockHistoryJpa(transactionId, planetId));
  }

  // TODO: Well this could take a loooooong time
  public void insertRobotRoundHistoryForPlayer(UUID roundId, UUID playerId) {
    var result = this.robotRestClient.getRobotsOfPlayer(playerId)
        .stream().map(robot -> new RobotHistoryJpa(roundId, robot.id(), robot.player(), robot.planet(), robot.alive(),
            robot.maxHealth(), robot.maxEnergy(), robot.energyRegen(), robot.attackDamage(),
            robot.miningSpeed(), robot.health(), robot.energy(),
            robot.healthLevel(), robot.damageLevel(), robot.miningSpeedLevel(), robot.miningLevel(),
            robot.energyLevel(), robot.energyRegenLevel(), robot.storageLevel()))
        .collect(Collectors.toSet());
    this.robotHistoryJpaRepository.saveAll(result);
  }
}
