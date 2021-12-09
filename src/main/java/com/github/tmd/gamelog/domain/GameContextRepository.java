package com.github.tmd.gamelog.domain;

import java.util.Optional;

public interface GameContextRepository {
    Optional<GameContext> findByEventTransactionId(String eventTransactionId);
}
