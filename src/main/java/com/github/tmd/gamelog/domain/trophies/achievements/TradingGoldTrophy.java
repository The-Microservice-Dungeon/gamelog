package com.github.tmd.gamelog.domain.trophies.achievements;

import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.trophies.RoundCheckedTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradingGoldTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Grand Nagus";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Trading%20Gold%20-%20Grand%20Nagus.png";

    @Override
    public Boolean awardingConditionFulfilled(RoundScore roundScore) {
        return false;
    }
}
