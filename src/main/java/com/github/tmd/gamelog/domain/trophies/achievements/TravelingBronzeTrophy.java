package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.score.entity.AggregatedRoundScore;
import com.github.tmd.gamelog.domain.trophies.RoundCheckedTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelingBronzeTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Sputnik";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Traveling%20Bronze%20-%20Sputnik.png";

    @Override
    public Boolean awardingConditionFulfilled(AggregatedRoundScore roundScore) {
        return false;
    }
}
