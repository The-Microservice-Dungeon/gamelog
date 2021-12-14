package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tmd.gamelog.adapter.event.gameEvent.EventInterface;
import com.github.tmd.gamelog.adapter.event.gameEvent.MovementEvent;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpsRepository;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.adapter.rest.PlayerRepository;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventHandler {
    private final PlayerRepository playerRepository;
    private final RoundScoreRepository roundScoreRepository;

    public KafkaEventHandler(PlayerRepository playerRepository, RoundScoreRepository roundScoreRepository) {
        this.playerRepository = playerRepository;
        this.roundScoreRepository = roundScoreRepository;
    }

    public void handleEvent(KafkaEvent kafkaEvent)
    {
        if(MovementEvent.getEventName().equals(kafkaEvent.getType())) {
            MovementEvent movementEvent;

            try {
                movementEvent = MovementEvent.fromKafkaEvent(kafkaEvent);
            } catch (JsonProcessingException ignored) {
                throw new RuntimeException();
            }

            Player player = playerRepository.findByRobotId(movementEvent.getRobotId());
            movementEvent.setPlayerId(player.getId());
            String gameId = "1";
            String roundId = "1";
            RoundScore roundScore = roundScoreRepository.findByGameAndRoundAndPlayer(gameId, roundId, player.getId());
            movementEvent.execute(roundScore);
            roundScoreRepository.save(roundScore);

            //TODO: Accnowledge ?
        }
    }

}
