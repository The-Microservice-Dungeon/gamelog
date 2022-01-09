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
public class FightingSilverTrophy extends Trophy implements RoundCheckedTrophy {

    private long id;
    private String name = "Blood Thirst";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Silver%20-%20Blood%20Thirst.png";

    @Override
    public Boolean awardingConditionFulfilled(RoundScore roundScore) {
        return false;
    }
}
