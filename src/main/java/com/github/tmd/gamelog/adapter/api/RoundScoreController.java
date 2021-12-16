package com.github.tmd.gamelog.adapter.api;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.RoundScoreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("roundScore")
public class RoundScoreController {

    private final RoundScoreRepository roundScoreRepository;

    public RoundScoreController(RoundScoreRepository roundScoreRepository) {
        this.roundScoreRepository = roundScoreRepository;
    }

    @GetMapping("/")
    public ArrayList<RoundScore> getAll(@RequestParam("roundId") String round) {
        return this.roundScoreRepository.findAllByRoundId(round);
    }

}
