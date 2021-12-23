package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Long id;
    private String name;

}
