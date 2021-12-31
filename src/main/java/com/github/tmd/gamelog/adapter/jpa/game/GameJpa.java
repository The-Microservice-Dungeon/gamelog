package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.GameStatus;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GameJpa {

  @Id
  @Column(name = "game_id", unique = true, updatable = false)
  private UUID gameId;

  @Enumerated(EnumType.STRING)
  @Column(name = "game_status")
  private GameStatus status;
}
