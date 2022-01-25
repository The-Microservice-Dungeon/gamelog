package com.github.tmd.gamelog;

import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GamelogStartup implements ApplicationListener<ApplicationReadyEvent> {
  private final TrophyRepository trophyRepository;

  @Autowired
  public GamelogStartup(TrophyRepository trophyRepository) {
    this.trophyRepository = trophyRepository;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    this.trophyRepository.initRepository();
  }
}
