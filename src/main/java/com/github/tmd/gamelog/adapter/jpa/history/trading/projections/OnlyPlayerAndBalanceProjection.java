package com.github.tmd.gamelog.adapter.jpa.history.trading.projections;

import java.util.UUID;

public interface OnlyPlayerAndBalanceProjection {
  UUID getPlayerId();
  Integer getBalance();
}
