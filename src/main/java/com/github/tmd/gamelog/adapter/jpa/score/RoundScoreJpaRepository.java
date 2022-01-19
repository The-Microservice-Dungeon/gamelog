package com.github.tmd.gamelog.adapter.jpa.score;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundScoreJpaRepository extends JpaRepository<RoundScoreJpa, UUID> {
  RoundScoreJpa findByRoundId(UUID roundId);

  @Query("""
    select distinct rs 
    from RoundScoreJpa rs 
      join GameRoundStatusHistoryJpa gsh on gsh.roundId = rs.roundId
    where gsh.gameId = ?1
    order by gsh.roundNumber ASC
  """)
  List<RoundScoreJpa> findAllOrderedRoundScoresInGame(UUID gameId);
}
