package com.github.tmd.gamelog.kafkaSender;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    final KafkaSenderExample sender;

    public ExampleController(KafkaSenderExample sender) {
        this.sender = sender;
    }

    @GetMapping("/event/move")
    public void eventMove() {
        Event event = new Event();
        event.setEventType("movement_event");
        event.setCreatedAt("now");
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }

    @GetMapping("/event/round/end")
    public void eventRoundEnd() {
        Event event = new Event();
        event.setEventType("round_end_event");
        event.setCreatedAt("now");
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }


}
