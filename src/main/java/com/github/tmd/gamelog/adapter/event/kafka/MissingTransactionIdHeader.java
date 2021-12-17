package com.github.tmd.gamelog.adapter.event.kafka;

public class MissingTransactionIdHeader extends RuntimeException {

    MissingTransactionIdHeader() {
        super("No TransactionId found in KafkaEvent headers.");
    }

}
