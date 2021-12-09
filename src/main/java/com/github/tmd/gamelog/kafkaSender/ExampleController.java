package com.github.tmd.gamelog.kafkaSender;

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
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }

    @GetMapping("/event/round/end")
    public void eventRoundEnd() {
        KafkaEvent event = new KafkaEvent();
        event.setPayload("{hihhiihiihihihihihih}");
        sender.sendEvent(event);
    }


}
