package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * History of trading events
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PlayerBalanceHistoryJpa {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Column(name = "balance", updatable = false, nullable = false)
  private Integer moneyChange;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}