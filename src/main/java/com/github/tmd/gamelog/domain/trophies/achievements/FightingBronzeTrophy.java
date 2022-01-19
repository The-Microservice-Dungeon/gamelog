package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import com.github.tmd.gamelog.domain.trophies.RoundCheckedTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightingBronzeTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "First Blood";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";

    @Override
    public Boolean awardingConditionFulfilled(AggregatedRoundScore roundScore) {
        return false;
    }
}
