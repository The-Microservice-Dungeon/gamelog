package com.github.tmd.gamelog.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface CommandContextRepository {
    CommandContext findByTransactionId(String transactionId);
}
