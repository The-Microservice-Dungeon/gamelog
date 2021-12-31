package com.github.tmd.gamelog.domain.game;

import java.time.ZonedDateTime;

public interface GameEventHandler {
  void onGameCreate(String gameId, ZonedDateTime time);
  void onGameStart(String gameId, ZonedDateTime time);
  void onGameEnd(String gameId, ZonedDateTime time);
}
