package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class CommandHistoryJpa {
  @Id
  @NonNull
  @Column(name = "transcation_id", updatable = false, nullable = false, unique = true)
  private UUID transactionId;

  @NonNull
  @Column(name = "gameId_id", updatable = false, nullable = false)
  private UUID gameId;

  @NonNull
  @Column(name = "round_id", updatable = false, nullable = false)
  private UUID roundId;

  @NonNull
  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID playerId;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
