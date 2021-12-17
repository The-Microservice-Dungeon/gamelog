package com.github.tmd.gamelog.adapter.event.kafka;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an Event coming from Kafka
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent{
    private String type;
    private String payload;
}
