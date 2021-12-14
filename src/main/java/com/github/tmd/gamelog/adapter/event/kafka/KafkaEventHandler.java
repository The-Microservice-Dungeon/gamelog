package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tmd.gamelog.adapter.event.gameEvent.MovementEvent;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.adapter.rest.PlayerRepository;
import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventHandler {
    private final RoundScoreRepository roundScoreRepository;

    public KafkaEventHandler(RoundScoreRepository roundScoreRepository) {
        this.roundScoreRepository = roundScoreRepository;
    }

    public void handleEvent(KafkaEvent kafkaEvent, CommandContext commandContext)
    {
        if(MovementEvent.getEventName().equals(kafkaEvent.getType())) {
            MovementEvent movementEvent;

            try {
                movementEvent = MovementEvent.fromKafkaEvent(kafkaEvent);
            } catch (JsonProcessingException ignored) {
                throw new RuntimeException();
            }

            movementEvent.setPlayerId(commandContext.getPlayer().getId());
            RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
            movementEvent.execute(roundScore);
            roundScoreRepository.save(roundScore);

            //TODO: Accnowledge ?
        }
    }

}
