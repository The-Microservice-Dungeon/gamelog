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
public class TradingSilverTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Manager";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Trading%20Silver%20-%20Manager.png";

    @Override
    public Boolean awardingConditionFulfilled(RoundScore roundScore) {
        return false;
    }
}
