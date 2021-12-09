package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameContextRepository implements GameRepository {
    @Autowired
    private GameContextJpaRepository gameContextJpaRepository;

    @Override
    public Optional<Game> findByEventTransactionId(String eventTransactionId) {
        return Optional.of(new Game());
    }
}
