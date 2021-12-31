package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.GameStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

  @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "game_id")
  private Set<RoundJpa> rounds = new HashSet<>();
}
