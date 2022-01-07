package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightingThirdPlaceTrophy extends Trophy {

    private long id;
    private String name = "Fighting - Third Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Fighting%20Score%20-%20Third%20Place.png";

}
