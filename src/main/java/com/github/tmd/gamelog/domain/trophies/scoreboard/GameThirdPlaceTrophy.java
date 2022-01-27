package com.github.tmd.gamelog.domain.trophies.scoreboard;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.trophies.ScoreboardTrophy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameThirdPlaceTrophy extends ScoreboardChangingTrophy implements ScoreboardTrophy {

    private long id;
    private String name = "Game - Third Place";
    private String badgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/scoreboard/Game%20Score%20-%20Third%20Place.png";

    @Override
    public void awardToQualifiedPlayer(Scoreboard scoreboard) {
        List<Player> players = scoreboard.getGameScores().keySet().stream().toList();
        for (Player player : players) {
            if (scoreboard.getTotalPlacementOfPlayer(player) == 3) {
                awardTrophyAndUpdateScoreboard(scoreboard, player);
            }
        }
    }
}
