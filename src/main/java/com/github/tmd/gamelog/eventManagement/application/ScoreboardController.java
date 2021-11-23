package com.github.tmd.gamelog.eventManagement.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScoreboardController {

    ScoreboardService scoreboardService;

    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @GetMapping("/scoreboards/{category}")
    public List<ScoreboardEntry> getScoreboard(@PathVariable String category) {
        return this.scoreboardService.getScoreboardByCategory(category);
    }

}
