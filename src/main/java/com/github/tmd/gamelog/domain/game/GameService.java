package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import java.util.HashSet;
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
    var game = new Game(new GameId(gameId), new HashSet<>());
    return this.gameRepository.save(game);
  }

  public void startGame(UUID gameId) {
    var game = getOrThrow(gameId);

    game.setGameStatus(GameStatus.STARTED);
    this.gameRepository.save(game);
  }

  public void endGame(UUID gameId) {
    var game = getOrThrow(gameId);

    game.setGameStatus(GameStatus.ENDED);
    this.gameRepository.save(game);
  }

  public void addRound(UUID gameId, UUID roundId, Integer roundNumber) {
    var game = getOrThrow(gameId);
    var round = new Round(new RoundId(roundId), roundNumber);
    game.addRound(round);

    this.gameRepository.save(game);
  }

  private Game getOrThrow(UUID gameId) {
    var optGame = gameRepository.findGameById(new GameId(gameId));
    if(optGame.isEmpty()) throw new RuntimeException("Game with id %s not found".formatted(gameId));
    return optGame.get();
  }
}
