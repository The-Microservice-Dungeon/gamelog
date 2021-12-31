package com.github.tmd.gamelog.domain.game;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface GameEventHandler {
  void onGameCreate(UUID gameId, ZonedDateTime time);
  void onGameStart(UUID gameId, ZonedDateTime time);
  void onGameEnd(UUID gameId, ZonedDateTime time);
  void onRoundStart(UUID gameId, UUID roundId, Integer roundNumber, ZonedDateTime time);
}
