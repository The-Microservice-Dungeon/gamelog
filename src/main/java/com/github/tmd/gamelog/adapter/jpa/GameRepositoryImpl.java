package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusJpa;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.GameRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {
  private final GameMapper gameMapper;
  private final GameStatusHistoryJpaRepository gameStatusHistoryJpaRepository;

  public GameRepositoryImpl(GameMapper gameMapper,
      GameStatusHistoryJpaRepository gameStatusHistoryJpaRepository) {
    this.gameMapper = gameMapper;
    this.gameStatusHistoryJpaRepository = gameStatusHistoryJpaRepository;
  }

  @Override
  public Optional<Game> findGameById(GameId gameId) {
    return gameStatusHistoryJpaRepository.findByGameId(gameId.id())
        .map(gameMapper::toDomain);
  }

  @Override
  public Optional<Game> findActiveGame() {
    return gameStatusHistoryJpaRepository.findFirstByStatus(GameStatusJpa.STARTED)
        .map(gameMapper::toDomain);
  }

  @Override
  public Set<Game> findAllGames() {
    return StreamSupport.stream(gameStatusHistoryJpaRepository.findAll().spliterator(), false)
        .map(gameMapper::toDomain)
        .collect(Collectors.toSet());
  }
}