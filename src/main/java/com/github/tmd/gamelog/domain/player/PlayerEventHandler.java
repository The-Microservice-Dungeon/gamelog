package com.github.tmd.gamelog.domain.player;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface PlayerEventHandler {
  void onPlayerRegister(UUID playerId, String userName, ZonedDateTime time);
}
