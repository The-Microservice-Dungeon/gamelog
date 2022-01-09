package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO class for persistent storage of the Player.
 */
@Entity
@Data
@AllArgsConstructor
public class PlayerDto {

    @Id
    @Column(name = "player_id")
    private UUID id;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id")
    private Set<PlayerTrophyDto> earnedTrophies;

    public PlayerDto() {
        this.id = null;
        this.earnedTrophies = new HashSet<PlayerTrophyDto>();
    }

    public PlayerDto(UUID id) {
        this.id = id;
        this.earnedTrophies = new HashSet<PlayerTrophyDto>();
    }
}