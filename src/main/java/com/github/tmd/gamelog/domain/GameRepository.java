package com.github.tmd.gamelog.domain;

import java.util.Optional;

public interface GameRepository {
    Optional<Game> findByEventTransactionId(String eventTransactionId);
}
