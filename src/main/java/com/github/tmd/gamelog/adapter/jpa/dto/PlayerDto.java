package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Type;

/**
 * DTO class for persistent storage of the Player.
 */
@Entity
@Data
@AllArgsConstructor
public class PlayerDto {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "player_id")
    private UUID id;

    @Column(name = "player_name", nullable = false)
    private String name;

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