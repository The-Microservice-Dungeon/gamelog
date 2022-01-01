package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerMapper;
import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard.ScoreboardId;
import com.github.tmd.gamelog.domain.scoreboard.model.Game;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.GameStatus;
import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import com.github.tmd.gamelog.domain.scoreboard.model.Round.RoundId;
import com.github.tmd.gamelog.domain.scoreboard.model.RoundScore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScoreboardMapper {

  private final PlayerMapper playerMapper;

  @Autowired
  public ScoreboardMapper(PlayerMapper playerMapper) {
    this.playerMapper = playerMapper;
  }

  public ScoreboardJpa toPersistence(Scoreboard scoreboard) {
    return new ScoreboardJpa(
        scoreboard.getScoreboardId().id(),
        this.toPersistence(scoreboard.getGame()),
        this.toPersistence(scoreboard.getRoundScores())
    );
  }

  public Scoreboard toDomain(ScoreboardJpa scoreboardJpa) {
    var scoreboard = new Scoreboard(new ScoreboardId(scoreboardJpa.getScoreboardId()),
        this.toDomain(scoreboardJpa.getGameJpa()),
        this.toDomain(scoreboardJpa.getScores())
    );

    return scoreboard;
  }

  private Round toDomain(RoundJpa round) {
    return new Round(new RoundId(round.getRoundId()), round.getRoundNumber());
  }

  private RoundJpa toPersistence(Round round) {
    return new RoundJpa(round.getRoundId().roundId(), round.getRoundNumber());
  }

  private Game toDomain(GameJpa jpa) {
    var rounds = jpa.getRounds().stream().map(this::toDomain).collect(Collectors.toSet());
    var game = new Game(new GameId(jpa.getGameId()), rounds);
    game.setGameStatus(this.toDomain(jpa.getStatus()));
    return game;
  }

  private GameJpa toPersistence(Game game) {
    return new GameJpa(
        game.getId().gameId(),
        this.toPersistence(game.getGameStatus()),
        game.getRounds().stream().map(this::toPersistence).collect(Collectors.toSet())
    );
  }

  private GameStatus toDomain(GameStatusJpa jpa) {
    return switch (jpa) {
      case ENDED -> GameStatus.ENDED;
      case STARTED -> GameStatus.STARTED;
      case CREATED -> GameStatus.CREATED;
    };
  }

  private GameStatusJpa toPersistence(GameStatus status) {
    return switch (status) {
      case ENDED -> GameStatusJpa.ENDED;
      case STARTED -> GameStatusJpa.STARTED;
      case CREATED -> GameStatusJpa.CREATED;
    };
  }

  private Map<Player, Set<RoundScore>> toDomain(Set<RoundScoreJpa> roundScoreJpas) {
    Map<Player, Set<RoundScore>> scores = new HashMap<>();

    for(RoundScoreJpa jpa : roundScoreJpas) {
      var player = playerMapper.toDomain(jpa.getPlayer());
      var scoreSet = scores.getOrDefault(player, new HashSet<>());
      scoreSet.add(new RoundScore(this.toDomain(jpa.getRound())));
      scores.put(player, scoreSet);
    }

    return scores;
  }

  private Set<RoundScoreJpa> toPersistence(Map<Player, Set<RoundScore>> playerSetMap) {
    return playerSetMap.entrySet().stream()
        .flatMap(entry -> entry.getValue()
            .stream()
            .map(r -> new RoundScoreJpa(playerMapper.toPersistence(entry.getKey()), toPersistence(r.round())))
        )
        .collect(Collectors.toSet());
  }
}
