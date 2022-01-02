package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
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
public class CommandHistoryJpa {
  @Id
  @NonNull
  @Type(type="uuid-char")
  @Column(name = "transcation_id", updatable = false, nullable = false, unique = true)
  private UUID transactionId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "game_id", updatable = false, nullable = false)
  private UUID gameId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "round_id", updatable = false, nullable = false)
  private UUID roundId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID playerId;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
