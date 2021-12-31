package com.github.tmd.gamelog.adapter.jpa.game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RoundJpa {
  @Id
  @Column(name = "round_id", unique = true, updatable = false)
  private UUID roundId;

  @Column(name = "round_number")
  private Integer roundNumber;

  @ElementCollection(targetClass = RoundScoreJpa.class)
  @CollectionTable(
      name = "round_scores",
      joinColumns = @JoinColumn(name = "round_id", referencedColumnName = "round_id")
  )
  private Set<RoundScoreJpa> scores = new HashSet<>();
}
