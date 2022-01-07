package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiningBronzeTrophy extends Trophy {

    private long id;
    private String name = "Miner";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Bronze%20-%20Miner.png";

}
