package com.github.tmd.gamelog.eventManagement.application;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.Event;
import com.github.tmd.gamelog.eventManagement.application.eventTypes.MovementEvent;
import com.github.tmd.gamelog.eventManagement.application.eventTypes.RoundEndEvent;
import org.springframework.stereotype.Service;

@Service
public class EventNormalizerService {

    public Event normalize(Event event) {

        String eventType = event.getEventType();

        if (eventType.equals("movement_event")) {
            MovementEvent result = new MovementEvent();
            result.setStart(1);
            result.setEnd(1);
            result.setRobotId(2);
            return result;
        } else if (eventType.equals("round_end_event")) {
            RoundEndEvent result = new RoundEndEvent();
            result.setRound(1);
            return result;
        } else {
            return new Event();
        }

    }

}
