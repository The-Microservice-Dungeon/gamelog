package com.github.tmd.gamelog.domain;

public interface CommandContextRepository {

    public CommandContext findByTransactionId(String transactionId);

}
