package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.trophies.ScoreboardTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiningFirstPlaceTrophy extends ScoreboardChangingTrophy implements ScoreboardTrophy {

    private long id;
    private String name = "Mining - First Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Mining%20Score%20-%20First%20Place.png";

    @Override
    public void awardToQualifiedPlayer(Scoreboard scoreboard) {
        List<Player> players = scoreboard.getGameScores().keySet().stream().toList();
        for (Player player : players) {
            if (scoreboard.getMiningPlacementOfPlayer(player) == 1) {
                awardTrophyAndUpdateScoreboard(scoreboard, player);
            }
        }
    }
}
