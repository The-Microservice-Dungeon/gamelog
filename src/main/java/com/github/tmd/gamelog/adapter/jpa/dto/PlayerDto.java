package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * DTO class for persistent storage of the Player.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    @Id
    private String id;

}