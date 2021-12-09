package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.github.tmd.gamelog.domain.GameContext;

public interface EventInterface {
    public void execute(GameContext gameContext);
}
