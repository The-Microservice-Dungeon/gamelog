package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.Game;
import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.GameRepository;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {
  private final GameJpaRepository gameJpaRepository;
  private final GameMapper gameMapper;

  public GameRepositoryImpl(
      GameJpaRepository gameJpaRepository,
      GameMapper gameMapper) {
    this.gameJpaRepository = gameJpaRepository;
    this.gameMapper = gameMapper;
  }

  @Override
  public Game save(Game game) {
    var jpa = gameMapper.toPersistence(game);
    var saved = this.gameJpaRepository.save(jpa);
    return gameMapper.toDomain(saved);
  }

  @Override
  public Optional<Game> findGameById(GameId gameId) {
    var found = gameJpaRepository.findById(gameId.gameId());
    return found.map(this.gameMapper::toDomain);
  }

  @Override
  public Optional<Game> findGameByRoundId(RoundId roundId) {
    var found = gameJpaRepository.findGameJpaByRounds_RoundId(roundId.roundId());
    return found.map(this.gameMapper::toDomain);
  }
}
