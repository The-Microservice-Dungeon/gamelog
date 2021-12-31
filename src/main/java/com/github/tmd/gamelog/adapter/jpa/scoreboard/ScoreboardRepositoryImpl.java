package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreboardRepositoryImpl implements ScoreboardRepository {
  private final ScoreboardJpaRepository scoreboardJpaRepository;
  private final ScoreboardMapper scoreboardMapper;

  public ScoreboardRepositoryImpl(
      ScoreboardJpaRepository scoreboardJpaRepository,
      ScoreboardMapper scoreboardMapper) {
    this.scoreboardJpaRepository = scoreboardJpaRepository;
    this.scoreboardMapper = scoreboardMapper;
  }

  @Override
  @Transactional(TxType.REQUIRED)
  public Scoreboard save(Scoreboard scoreboard) {
    var jpa = this.scoreboardMapper.toPersistence(scoreboard);
    var saved = this.scoreboardJpaRepository.save(jpa);
    return this.scoreboardMapper.toDomain(saved);
  }

  @Override
  public Optional<Scoreboard> getScoreboardByGameId(GameId gameId) {
    return scoreboardJpaRepository.findByGameId(gameId.gameId())
        .map(this.scoreboardMapper::toDomain);
  }
}
