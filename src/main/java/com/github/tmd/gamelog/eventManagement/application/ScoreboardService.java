package com.github.tmd.gamelog.eventManagement.application;

import com.github.tmd.gamelog.eventManagement.application.User.User;
import com.github.tmd.gamelog.eventManagement.application.User.UserRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreboardService {

    UserRepository userRepository;

    public ScoreboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    List<ScoreboardEntry> getScoreboardByCategory(String category) {
        List<ScoreboardEntry> scoreboard = new ArrayList<>();
        for (User user : this.userRepository.findAll(Sort.by(Sort.Direction.DESC, category))) {
            Class<?> c = user.getClass();
            Field field = c.getDeclaredField(category);
            field.setAccessible(true);
            ScoreboardEntry entry = new ScoreboardEntry(user, (int) field.get(user));
            scoreboard.add(entry);
        }
        return scoreboard;
    }
}
