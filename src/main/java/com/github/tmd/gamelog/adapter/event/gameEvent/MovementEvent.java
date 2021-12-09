package com.github.tmd.gamelog.adapter.event.eventTypes;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.domain.GameContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementEvent implements IEvent {

    private int robotId;

    private int start;

    private int end;

    private int round;

    @Override
    public String toString() {
        return "MovementEvent{" +
                "robotId=" + robotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public void execute(GameContext gameContext) {

    }
}
