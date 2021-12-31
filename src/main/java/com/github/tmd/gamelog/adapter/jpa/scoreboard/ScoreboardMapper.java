package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.adapter.jpa.game.GameMapper;
import com.github.tmd.gamelog.domain.scoreboard.Scoreboard;
import org.springframework.stereotype.Component;

@Component
public class ScoreboardMapper {
  private final GameMapper gameMapper;

  public ScoreboardMapper(GameMapper gameMapper) {
    this.gameMapper = gameMapper;
  }

  public ScoreboardJpa toPersistence(Scoreboard scoreboard) {
    var jpa = new ScoreboardJpa();

    jpa.setScoreboardId(scoreboard.getScoreboardId().id());
    jpa.setGameJpa(gameMapper.toPersistence(scoreboard.getGame()));

    return jpa;
  }

  public Scoreboard toDomain(ScoreboardJpa scoreboardJpa) {
    var game = gameMapper.toDomain(scoreboardJpa.getGameJpa());

    return new Scoreboard(new Scoreboard.ScoreboardId(scoreboardJpa.getScoreboardId()), game);
  }
}
