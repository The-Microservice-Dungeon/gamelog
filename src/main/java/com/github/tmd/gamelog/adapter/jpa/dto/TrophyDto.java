package com.github.tmd.gamelog.adapter.jpa.dto;

import com.github.tmd.gamelog.domain.trophies.TrophyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * DTO class for persistent storage of the trophies.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrophyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trophy_id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", updatable = true, nullable = false, unique = true)
    private String name;
    @Column(name = "badge_url", updatable = true, nullable = false)
    private String badgeUrl;
    @Column(name = "trophy_type", updatable = false, nullable = false, unique = true)
    private TrophyType trophyType;

}
