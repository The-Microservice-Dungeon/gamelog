package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.github.tmd.gamelog.domain.GameContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementEvent implements EventInterface {

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
    public void execute(GameContext roundScore) {
    }
}
