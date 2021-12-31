package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"game_id", "round_number"}),
    @UniqueConstraint(columnNames = {"game_id", "round_id"})
})
class RoundJpa {
  @Id
  @Column(name = "round_id", nullable = false, unique = true, updatable = false)
  private UUID roundId;

  @Column(name = "round_number")
  private Integer roundNumber;
}
