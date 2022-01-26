package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.PlayerStatistics;
import com.github.tmd.gamelog.domain.trophies.RoundCheckedTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiningSilverTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Gold Digger";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Mining%20Silver%20-%20Gold%20Digger.png";

    @Override
    public Boolean awardingConditionFulfilled(PlayerStatistics playerStatistics) {
        return playerStatistics.getMinedResources() >= 25;
    }
}
