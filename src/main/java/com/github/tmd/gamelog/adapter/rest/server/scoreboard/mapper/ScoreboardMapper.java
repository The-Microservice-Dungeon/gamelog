package com.github.tmd.gamelog.adapter.rest.server.scoreboard.mapper;

import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardEntryJsonDto;
import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardJsonDto;
import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardPlayerEntryJsonDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardMapper {

  public ScoreboardJsonDto toJson(Scoreboard scoreboard) {
    return new ScoreboardJsonDto(toJson(scoreboard.getGameScores()));
  }

  private List<ScoreboardEntryJsonDto> toJson(Map<Player, AggregatedScore> gameScoreMap) {
    return gameScoreMap.entrySet().stream()
        .map(entry -> new ScoreboardEntryJsonDto(toJson(entry.getKey()), entry.getValue().score(),
            entry.getValue().getFightingScore(), entry.getValue().getMiningScore(), entry.getValue()
            .getMovementScore(), entry.getValue().getRobotScore(), entry.getValue().getTradingScore()))
        .sorted(((o1, o2) -> o2.totalScore().compareTo(o1.totalScore())))
        .collect(Collectors.toList());
  }

  private ScoreboardPlayerEntryJsonDto toJson(Player player) {
    return new ScoreboardPlayerEntryJsonDto(player.getId(), player.getName());
  }
}
