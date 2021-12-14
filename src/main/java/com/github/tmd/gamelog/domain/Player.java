package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player
{
    private String id;

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                '}';
    }
}
