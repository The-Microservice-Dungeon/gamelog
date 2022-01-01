package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
public class ScoreboardJpa {
  @Id
  @Column(name = "scoreboard_id", nullable = false, unique = true, updatable = false)
  private UUID scoreboardId;

  @NaturalId
  @OneToOne(optional = false, orphanRemoval = true, cascade = {
      CascadeType.PERSIST,
      CascadeType.REFRESH,
      CascadeType.MERGE,
      CascadeType.DETACH
  })
  @JoinColumn(name = "game_id", unique = true, updatable = false)
  private GameJpa gameJpa;

  @ElementCollection
  private Set<RoundScoreJpa> scores = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "scoreboard_status")
  private ScoreboardStatusJpa status;
}
