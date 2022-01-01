package com.github.tmd.gamelog.application;

import java.util.UUID;

public interface GameEventHandler {
  void onCreateGame(UUID gameId);
  void onEndGame(UUID gameId);
  void onStartRound(UUID gameId, UUID roundId, Integer roundNumber);
  void onEndRound(UUID gameId, UUID roundId, Integer roundNumber);
}
