package com.github.tmd.gamelog.adapter.view;

import com.github.tmd.gamelog.adapter.jpa.TrophyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple Frontend for Trophies using Thymeleaf
 */
@Controller
public class TrophyViewController {
  private final TrophyRepository trophyRepository;

  @Autowired
  public TrophyViewController(TrophyRepository trophyRepository) {
    this.trophyRepository = trophyRepository;
  }

  @GetMapping(value = "trophies", produces = MediaType.TEXT_HTML_VALUE)
  public String showTrophies(Model model) {
    model.addAttribute("trophies", trophyRepository.findAll());
    return "trophies";
  }
}
