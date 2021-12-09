package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.domain.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementEvent implements EventInterface {
    private int robotId;
    private int start;
    private int end;
    private int round;

    public static String getEventName()
    {
        return "movement";
    }

    public static MovementEvent fromKafkaEvent(KafkaEvent kafkaEvent)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(kafkaEvent.getPayload(), MovementEvent.class);
        } catch (Exception ignored) {

        }
    }

    @Override
    public String toString() {
        return "MovementEvent{" +
                "robotId=" + robotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public void execute(Game roundScore) {
    }
}
