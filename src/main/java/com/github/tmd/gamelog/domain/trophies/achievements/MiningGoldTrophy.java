package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiningGoldTrophy extends Trophy {

    private long id;
    private String name = "Mining Industrialist";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Gold%20-%20Mining%20Industrialist.png";

}
