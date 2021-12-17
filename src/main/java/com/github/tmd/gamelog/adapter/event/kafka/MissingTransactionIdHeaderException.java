package com.github.tmd.gamelog.adapter.event.kafka;

/**
 * Exception is thrown when there is no Transaction ID present in the header
 */
public class MissingTransactionIdHeaderException extends RuntimeException {
    public MissingTransactionIdHeaderException() {
        super("No TransactionId found in KafkaEvent headers.");
    }

    public MissingTransactionIdHeaderException(String message) {
        super(message);
    }
}
