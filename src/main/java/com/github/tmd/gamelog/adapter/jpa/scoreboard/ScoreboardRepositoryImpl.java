package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.ScoreboardRepository;
import java.util.Optional;
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
  public Optional<Scoreboard> getScoreboardByGameId(GameId gameId) {
    return scoreboardJpaRepository.findByGameId(gameId.gameId())
        .map(this.scoreboardMapper::toDomain);
  }
}
