package com.github.tmd.gamelog.adapter.jpa.history.robot;

import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndGivenDamageProjection;
import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndNumberOfKillsProjection;
import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyPlayerAndNumberOfVictimsProjection;
import com.github.tmd.gamelog.adapter.jpa.history.robot.projections.OnlyResolvedAttackerAndDefenderAsPlayerWithDefenderHealthAndDamageProjection;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightHistoryJpaRepository extends CrudRepository<FightHistoryJpa, UUID> {
  // TODO: untested
  @Query("""
    select rh1.playerId as attackerPlayerId, rh2.playerId as defenderPlayerId, rh2.attackDamage as damage, fh.defenderHealth
      from FightHistoryJpa fh
        join CommandHistoryJpa ch on ch.transactionId = fh.transactionId
        join RobotHistoryJpa rh1 on fh.attacker = rh1.robotId
        join RobotHistoryJpa rh2 on fh.defender = rh2.robotId
        where ch.roundId = ?1 
  """)
  Set<OnlyResolvedAttackerAndDefenderAsPlayerWithDefenderHealthAndDamageProjection> findFightHistoryInRound(UUID roundId);

  @Query("""
    select rh1.playerId as playerId, sum(rh1.attackDamage) as givenDamage
      from FightHistoryJpa fh
        join CommandHistoryJpa ch on ch.transactionId = fh.transactionId
        join RobotHistoryJpa rh1 on fh.attacker = rh1.robotId
        where ch.roundId = ?1 
      group by rh1.playerId
  """)
  Set<OnlyPlayerAndGivenDamageProjection> findGivenDamageInRound(UUID roundId);

  @Query("""
    select rh1.playerId as playerId, sum(rh1.attackDamage) as numberOfKills
      from FightHistoryJpa fh
        join CommandHistoryJpa ch on ch.transactionId = fh.transactionId
        join RobotHistoryJpa rh1 on fh.attacker = rh1.robotId
        where ch.roundId = ?1  and fh.defenderHealth <= 0
      group by rh1.playerId
  """)
  Set<OnlyPlayerAndNumberOfKillsProjection> findNumberOfKillsInRound(UUID roundId);

  @Query("""
    select rh1.playerId as playerId, sum(rh1.attackDamage) as numberOfVictims
      from FightHistoryJpa fh
        join CommandHistoryJpa ch on ch.transactionId = fh.transactionId
        join RobotHistoryJpa rh1 on fh.defender = rh1.robotId
        where ch.roundId = ?1  and fh.defenderHealth <= 0
      group by rh1.playerId
  """)
  Set<OnlyPlayerAndNumberOfVictimsProjection> findNumberOfVictimsInRound(UUID roundId);
}
