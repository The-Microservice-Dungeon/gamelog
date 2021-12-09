package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameContextRepository implements com.github.tmd.gamelog.domain.GameContextRepository {
    @Autowired
    private GameContextJpaRepository gameContextJpaRepository;

    @Override
    public Optional<GameContext> findByEventTransactionId(String eventTransactionId) {
        return Optional.of(new GameContext());
    }
}
