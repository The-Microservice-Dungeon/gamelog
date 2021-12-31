package com.github.tmd.gamelog.kafka_sender;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import java.util.UUID;
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
    sender.sendEvent("event", event);
  }

  @GetMapping("/event/round/end")
  public void eventRoundEnd() {
    KafkaEvent event = new KafkaEvent();
    event.setPayload("{hihhiihiihihihihihih}");
    sender.sendEvent("event", event);
  }

  @GetMapping("/event/game/end")
  public void endGame() {
    sender.sendGameStatusEvent("status", "game-status-change",
        new GameStatusEvent(UUID.randomUUID(), GameStatus.ENDED));
  }


}
