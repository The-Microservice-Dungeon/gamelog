package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * History of fight events
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FightHistoryJpa {
  @Id
  @NonNull
  @Column(name = "transactionId", unique = true, updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Column(name = "attacker", updatable = false, nullable = false)
  private UUID attacker;

  @NonNull
  @Column(name = "defender", updatable = false, nullable = false)
  private UUID defender;

  @NonNull
  @Column(name = "defender_health", updatable = false, nullable = false)
  private Integer defenderHealth;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
