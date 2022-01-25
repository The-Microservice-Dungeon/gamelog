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
public class TravelingGoldTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Enterprise";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Traveling%20Gold%20-%20Enterprise.png";

    @Override
    public Boolean awardingConditionFulfilled(PlayerStatistics playerStatistics) {
        return playerStatistics.getTraveledDistance() >= 40;
    }
}
