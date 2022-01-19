package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatusChangedEvent;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.application.score.service.RoundScoreService;
import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class RoundScoreLifecycleHook implements GameLifecycleHook {
  private final RoundScoreService roundScoreService;

  public RoundScoreLifecycleHook(
      RoundScoreService roundScoreService) {
    this.roundScoreService = roundScoreService;
  }

  @Override
  @Transactional
  public void onRoundStatus(RoundStatusChangedEvent event, UUID gameId, Instant timestamp) {
    if(event.roundStatus() == RoundStatus.ENDED) {
      this.roundScoreService.accumulateAndSaveRoundScoresForRound(event.roundId());
    }
  }
}
