package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.adapter.rest.PlayerRepository;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.RoundScore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementEvent implements EventInterface {
    private String robotId;
    private int start;
    private int end;
    private int round;
    private String playerId;

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
    public String toString() {
        return "MovementEvent{" +
                "robotId=" + robotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public void execute(RoundScore roundScore) {
        roundScore.increaseMovementScoreBy(1);
    }
}
