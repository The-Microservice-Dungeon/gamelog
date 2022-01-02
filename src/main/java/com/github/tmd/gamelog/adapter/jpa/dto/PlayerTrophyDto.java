package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private Long id;

    @ManyToOne
    private TrophyDto trophyDto;

    @ManyToOne
    private PlayerDto playerDtoAwardedTo;

    private Date dateAwarded;
}
