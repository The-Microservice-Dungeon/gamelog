package com.github.tmd.gamelog.adapter.jpa.scoreboard;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
class GameJpa {
  @Id
  @Column(name = "game_id", nullable = false, unique = true, updatable = false)
  private UUID gameId;

  @Enumerated(EnumType.STRING)
  @Column(name = "game_status")
  private GameStatusJpa status;

  @OneToMany(orphanRemoval = true, cascade = {
      CascadeType.PERSIST,
      CascadeType.REFRESH,
      CascadeType.MERGE,
      CascadeType.DETACH
  })
  @JoinColumn(name="game_id")
  private Set<RoundJpa> rounds = new HashSet<>();
}
