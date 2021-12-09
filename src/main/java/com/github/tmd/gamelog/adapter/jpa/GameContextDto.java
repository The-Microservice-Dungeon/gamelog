package com.github.tmd.gamelog.adapter.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameContextDto {
    @Id
    private String id;
}
