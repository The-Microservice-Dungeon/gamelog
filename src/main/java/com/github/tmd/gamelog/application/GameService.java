package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.GameRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GameService {
  private final GameRepository gameRepository;

  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public Optional<Game> findGameById(GameId gameId) {
    return this.gameRepository.findGameById(gameId);
  }

  public Optional<Game> findActiveGame() {
    return this.gameRepository.findActiveGame();
  }

  public Optional<Game> findLatestGame() {
    return gameRepository.findLatestGame();
  }
}
