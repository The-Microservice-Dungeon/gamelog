package com.github.tmd.gamelog.adapter.view;

import com.github.tmd.gamelog.adapter.view.model.Placement;
import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * Simple Frontend for the Scoreboard using Thymeleaf
 */
@Controller
public class ScoreboardViewController {

  private final ScoreboardService scoreboardService;

  @Autowired
  public ScoreboardViewController(
      ScoreboardService scoreboardService) {
    this.scoreboardService = scoreboardService;
  }

  @GetMapping(value = "/scoreboard", produces = MediaType.TEXT_HTML_VALUE)
  public String getScoreboardView(Model model) {
    var scoreboard = this.scoreboardService.getScoreboardOfActiveGame()
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND));

    var gameScores = scoreboard.getGameScores();
    var placements = gameScores.entrySet().stream()
        .map(score -> {
          var s = score.getValue();

          var totalPlacement = scoreboard.getTotalPlacementOfPlayer(score.getKey());
          var fightingPlacement = scoreboard.getFightingPlacementOfPlayer(score.getKey());
          var miningPlacement = scoreboard.getMiningPlacementOfPlayer(score.getKey());
          var movementPlacement = scoreboard.getMovementPlacementOfPlayer(score.getKey());
          var tradingPlacement = scoreboard.getTradingPlacementOfPlayer(score.getKey());

          return new Placement(s.score(), s.getFightingScore(), s.getMiningScore(),
              s.getMovementScore(), s.getRobotScore(), s.getTradingScore(),

              totalPlacement,fightingPlacement,miningPlacement,movementPlacement, tradingPlacement,

              score.getKey().getName(), score.getKey().getId().toString());
        })
        .sorted((o1, o2) -> o2.totalPlacement().compareTo(o1.totalPlacement()))
        .collect(Collectors.toList());

    model.addAttribute("placements", placements);

    return "scoreboard";
  }
}
