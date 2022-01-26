package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.trophies.ScoreboardTrophy;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelingSecondPlaceTrophy extends Trophy implements ScoreboardTrophy {

    private long id;
    private String name = "Traveling - Second Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Traveling%20Score%20-%20Second%20Place.png";

    @Override
    public void awardToQualifiedPlayer(Scoreboard scoreboard) {
        Set<Player> players = scoreboard.getGameScores().keySet();
        for (Player player : players) {
            if (scoreboard.getMovementPlacementOfPlayer(player) == 2) {
                player.awardTrophy(this, scoreboard.getGame().getId().id());
            }
        }
    }
}
