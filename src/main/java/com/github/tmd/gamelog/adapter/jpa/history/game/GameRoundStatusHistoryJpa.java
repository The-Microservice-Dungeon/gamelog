package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GameRoundStatusHistoryJpa {
  @Id
  @Type(type = "uuid-char")
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "game_id", updatable = false, nullable = false)
  private UUID gameId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "round_id", updatable = false, nullable = false)
  private UUID roundId;

  @NonNull
  @Column(name = "round_number", updatable = false, nullable = false)
  private Integer roundNumber;

  @NonNull
  @Column(name = "status", updatable = false, nullable = false)
  private GameRoundStatusJpa status;

  @NonNull
  @Column(name = "timestamp", nullable = false, updatable = false)
  private Instant timestamp;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
