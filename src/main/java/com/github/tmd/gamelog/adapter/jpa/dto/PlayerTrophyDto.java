package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
import org.hibernate.annotations.Type;

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
    @Column(name = "player_trophy_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trophy_id", referencedColumnName = "trophy_id")
    private TrophyDto trophyDto;

    @Type(type = "uuid-char")
    @Column(name = "game_id", nullable = false, updatable = false)
    private UUID gameId;

    @Column(name = "date_awarded", nullable = false, updatable = false)
    private Date dateAwarded;
}
