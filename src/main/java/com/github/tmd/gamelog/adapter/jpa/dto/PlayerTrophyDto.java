package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * DTO class for persistent storage of the PlayerTrophies.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTrophyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    private TrophyDto trophyDto;

    @Column(name = "game_id", nullable = false, updatable = false)
    private UUID gameId;

    @Column(name = "date_awarded", updatable = false, nullable = false)
    private Date dateAwarded;
}
