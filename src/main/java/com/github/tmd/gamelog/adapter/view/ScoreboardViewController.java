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
    var scoreboard = this.scoreboardService.getScoreboardOfActiveGame().orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND));

    var placements = scoreboard.getGameScores().entrySet().stream()
        .sorted((o1, o2) -> o2.getValue().score().compareTo(o1.getValue().score()))
        .map(score -> new Placement(score.getValue().score(), score.getKey().getName()))
        .collect(Collectors.toList());

    model.addAttribute("placements", placements);

    return "scoreboard";
  }
}
