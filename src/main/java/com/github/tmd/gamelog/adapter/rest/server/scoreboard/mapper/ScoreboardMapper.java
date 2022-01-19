package com.github.tmd.gamelog.adapter.rest.server.scoreboard.mapper;

import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardEntryJsonDto;
import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardJsonDto;
import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardPlayerEntryJsonDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedGameScore;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardMapper {
  public ScoreboardJsonDto toJson(Scoreboard scoreboard) {
    return new ScoreboardJsonDto(toJson(scoreboard.getGameScores()));
  }

  private List<ScoreboardEntryJsonDto> toJson(Map<Player, AggregatedGameScore> gameScoreMap) {
    return gameScoreMap.entrySet().stream()
        .map(entry -> new ScoreboardEntryJsonDto(toJson(entry.getKey()), entry.getValue().score()))
        .sorted(((o1, o2) -> o2.score().compareTo(o1.score())))
        .collect(Collectors.toList());
  }

  private ScoreboardPlayerEntryJsonDto toJson(Player player) {
    return new ScoreboardPlayerEntryJsonDto(player.getId(), "__PLACEHOLDER__");
  }
}
