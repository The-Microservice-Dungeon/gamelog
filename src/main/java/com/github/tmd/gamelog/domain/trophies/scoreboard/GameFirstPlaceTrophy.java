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
public class GameFirstPlaceTrophy extends Trophy implements ScoreboardTrophy {

    private long id;
    private String name = "Game - First Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Game%20Score%20-%20First%20Place.png";

    @Override
    public void awardToQualifiedPlayer(Scoreboard scoreboard) {
        Set<Player> players = scoreboard.getGameScores().keySet();
        for (Player player : players) {
            if (scoreboard.getTotalPlacementOfPlayer(player) == 1) {
                player.awardTrophy(this, scoreboard.getGame().getId().id());
            }
        }
    }
}
