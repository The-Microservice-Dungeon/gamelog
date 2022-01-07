package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.domain.RoundScore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a Movement event coming from the Robot service
 */
@Data
public class MovementEvent implements EventInterface {
    private String robotId;
    private int start;
    private int end;
    private int round;
    private UUID playerId;

    public static String getEventName()
    {
        return "movement";
    }

    public static MovementEvent fromKafkaEvent(KafkaEvent kafkaEvent) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(kafkaEvent.getPayload(), MovementEvent.class);
    }

    @Override
    public void execute(RoundScore roundScore) {
        roundScore.getMovementScore().increase(1);
    }
}
