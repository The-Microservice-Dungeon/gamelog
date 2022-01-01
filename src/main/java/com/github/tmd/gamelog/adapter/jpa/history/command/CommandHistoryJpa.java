package com.github.tmd.gamelog.adapter.jpa.history.command;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CommandHistoryJpa {
  @Id
  @Column(name = "transcation_id", updatable = false, nullable = false, unique = true)
  private UUID transactionId;

  @Column(name = "gameId_id", updatable = false, nullable = false)
  private UUID gameId;

  @Column(name = "round_id", updatable = false, nullable = false)
  private UUID roundId;

  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID playerId;
}
