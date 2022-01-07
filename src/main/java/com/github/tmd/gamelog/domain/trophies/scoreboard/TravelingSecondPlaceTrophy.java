package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelingSecondPlaceTrophy extends Trophy {

    private long id;
    private String name = "Traveling - Second Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Traveling%20Score%20-%20Second%20Place.png";

}
