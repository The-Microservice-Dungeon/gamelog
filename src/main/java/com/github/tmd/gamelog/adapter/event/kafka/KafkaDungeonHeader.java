package com.github.tmd.gamelog.adapter.event.kafka;

/**
 * Header Constants as defined in the
 * <a href="https://the-microservice-dungeon.github.io/decisionlog/decisions/event-structure.html">Event Structure decision</a>
 */
public class KafkaDungeonHeader {
  public static String KEY_TRANSACTION_ID = "transactionId";
  public static String KEY_VERSION = "version";
  public static String KEY_TIMESTAMP = "timestamp";
  public static String KEY_EVENT_ID = "eventId";
  public static String KEY_TYPE = "type";
}
