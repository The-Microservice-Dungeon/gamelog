package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.gameEvent.EventInterface;
import com.github.tmd.gamelog.adapter.event.gameEvent.MovementEvent;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventHandler {
    private ObjectMapper objectMapper;

    public KafkaEventHandler() {
        this.objectMapper = new ObjectMapper();
    }

    public void handleEvent(KafkaEvent kafkaEvent)
    {
        if(MovementEvent.getEventName().equals(kafkaEvent.getType())) {
            MovementEvent movementEvent = objectMapper.readValue(kafkaEvent.getPayload(), MovementEvent.class);

            return;
        }

        if(MovementEvent.getEventName().equals(kafkaEvent.getType())) {
            MovementEvent movementEvent = objectMapper.readValue(kafkaEvent.getPayload(), MovementEvent.class);

            return;
        }
    }

}
