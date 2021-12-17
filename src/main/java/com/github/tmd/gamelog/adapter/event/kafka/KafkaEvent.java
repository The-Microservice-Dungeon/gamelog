package com.github.tmd.gamelog.adapter.event.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an Event coming from Kafka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent{
    private String type;
    private String payload;
}
