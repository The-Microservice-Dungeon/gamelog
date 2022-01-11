package com.github.tmd.gamelog.adapter.jpa.history.trading.projections;

import java.util.UUID;

public interface OnlyPlayerAndNumberOfTradesProjection {
  UUID getPlayerId();
  Integer getNumberOfTrades();
}
