package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
  private final GameRepository gameRepository;

  @Autowired
  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public Game createNewGame(UUID gameId) {
    var game = new Game(new GameId(gameId));
    return this.gameRepository.save(game);
  }

  public void startGame(UUID gameId) {
    var optGame = gameRepository.findGameById(new GameId(gameId));
    if(optGame.isEmpty()) throw new RuntimeException("Game with id %s not found".formatted(gameId));
    var game = optGame.get();

    game.setGameStatus(GameStatus.STARTED);
    this.gameRepository.save(game);
  }

  public void endGame(UUID gameId) {
    var optGame = gameRepository.findGameById(new GameId(gameId));
    if(optGame.isEmpty()) throw new RuntimeException("Game with id %s not found".formatted(gameId));
    var game = optGame.get();

    game.setGameStatus(GameStatus.ENDED);
    this.gameRepository.save(game);
  }
}
