package com.github.tmd.gamelog;

import com.github.tmd.gamelog.gameEvent.MovementEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    final KafkaSenderExample sender;

    public ExampleController(KafkaSenderExample sender) {
        this.sender = sender;
    }

    @GetMapping("/test")
    public void test() {
        sender.sendMessage("TEST", "reflectoring-1");
    }

    @GetMapping("/user")
    public void user() {
        User u = new User();
        u.setName("Nico");
        sender.sendCustomMessage(u, "reflectoring-user");
    }

    @GetMapping("/move")
    public void move() {
        MovementEvent move = new MovementEvent();
        move.setRobotId(1);
        move.setStart(1);
        move.setEnd(2);
        sender.sendMovementEvent(move);
    }

    @GetMapping("/event")
    public void event() {
        Event event = new Event();
        event.setEventType("movement_event");
        event.setCreatedAt("now");
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }

}
