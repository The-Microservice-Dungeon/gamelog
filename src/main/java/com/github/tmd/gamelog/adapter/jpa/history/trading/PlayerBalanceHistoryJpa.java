package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"player_id", "assumed_round_id"})
})
public class PlayerBalanceHistoryJpa {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID playerId;

  @NonNull
  @Column(name = "balance", updatable = false, nullable = false)
  private Integer balance;

  // Since we're performing a synchronous call at the end of a round to achieve this we cannot
  // be sure whether the retrieved state belongs to the correct round. We can only assume this.
  // See TradingHistoryService for more details
  @NonNull
  @Type(type="uuid-char")
  @Column(name = "assumed_round_id", updatable = false, nullable = false)
  private UUID assumedRoundId;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
