package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.vo.RoundScore;
import com.github.tmd.gamelog.domain.player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Round {
  private final RoundId roundId;
  private final Integer roundNumber;

  @Setter(AccessLevel.NONE)
  private final Map<Player, RoundScore> scores;

  public void addScore(Player player, RoundScore roundScore) {
    if(scores.containsKey(player)) throw new IllegalArgumentException("Cant update score");
    scores.put(player, roundScore);
  }

  public record RoundId(UUID roundId) { }
}
