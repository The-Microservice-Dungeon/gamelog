package com.github.tmd.gamelog.eventManagement.application;

import com.github.tmd.gamelog.eventManagement.application.User.User;
import com.github.tmd.gamelog.eventManagement.application.User.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScoreboardController {

    final UserRepository userRepository;

    public ScoreboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/scoreboard")
    public List<User> getScoreboard() {
        return this.userRepository.findAllByOrderByScoreDesc();
    }

}
