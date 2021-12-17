package com.github.tmd.gamelog.adapter.event.gameEvent.game;

/**
 * Enum Constants regarding the possible Game Status values.
 * Since the status can be in lowercase or mixed-case (for whatever reason)
 * it is necessary to set {@code spring.jackson.mapper.accept-case-insensitive-enums=true} in the
 * spring config.
 */
public enum GameStatus {
  CREATED,
  STARTED,
  ENDED;
}
