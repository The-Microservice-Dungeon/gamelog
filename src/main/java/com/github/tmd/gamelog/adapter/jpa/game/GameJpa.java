package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.GameStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GameJpa {
  @Id
  @GeneratedValue
  private Long id;

  @NaturalId
  @Column(name = "game_id", unique = true, updatable = false)
  private String gameId;

  @Enumerated(EnumType.STRING)
  @Column(name = "game_status")
  private GameStatus status;
}
