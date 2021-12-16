package com.github.tmd.gamelog.application;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class RoundScoreService {

    private final RoundScoreRepository roundScoreRepository;

    public RoundScoreService(RoundScoreRepository roundScoreRepository) {
        this.roundScoreRepository = roundScoreRepository;
    }
}
