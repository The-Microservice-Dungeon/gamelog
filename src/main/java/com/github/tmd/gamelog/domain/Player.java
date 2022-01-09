package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Player {
    private UUID id;
    private Set<PlayerTrophy> earnedTrophies;

    public Player() {
        this.id = null;
        this.earnedTrophies = new HashSet<PlayerTrophy>();
    }

    public Player(UUID id) {
        this.id = id;
        this.earnedTrophies = new HashSet<PlayerTrophy>();
    }
}
