package com.github.tmd.gamelog;

import com.github.tmd.gamelog.gameEvent.MovementEvent;
import org.springframework.stereotype.Service;

@Service
public class NormalizerService {

    public Event normalize(Event event) {
        MovementEvent movementEvent = new MovementEvent();
        movementEvent.setStart(1);
        movementEvent.setEnd(1);
        movementEvent.setRobotId(2);
        return movementEvent;
    }

}
