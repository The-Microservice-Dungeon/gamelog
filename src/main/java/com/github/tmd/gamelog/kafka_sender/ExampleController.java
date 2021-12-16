package com.github.tmd.gamelog.kafka_sender;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
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
        KafkaEvent event = new KafkaEvent();
        event.setPayload("{\"start\": 3}");
        event.setType("movement");
        sender.sendEvent(event);
    }

    @GetMapping("/event/round/end")
    public void eventRoundEnd() {
        KafkaEvent event = new KafkaEvent();
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }


}
