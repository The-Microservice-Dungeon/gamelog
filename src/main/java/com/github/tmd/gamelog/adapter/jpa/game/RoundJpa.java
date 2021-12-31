package com.github.tmd.gamelog.adapter.jpa.game;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
}
