package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.adapter.jpa.game.GameJpa;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ScoreboardJpa {
  @Id
  @Column(name = "scoreboard_id", unique = true, updatable = false)
  private UUID scoreboardId;

  @OneToOne(optional = false)
  @JoinColumn(name = "game_id", unique = true, updatable = false)
  private GameJpa gameJpa;
}
