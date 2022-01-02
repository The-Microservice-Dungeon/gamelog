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
import org.hibernate.annotations.Type;
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
public class TradingHistoryJpa {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "transaction_id", unique = true, updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Column(name = "money_change_amount", updatable = false, nullable = false)
  private Integer moneyChange;

  @NonNull
  @Column(name = "timestamp", nullable = false, updatable = false)
  private Instant timestamp;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
