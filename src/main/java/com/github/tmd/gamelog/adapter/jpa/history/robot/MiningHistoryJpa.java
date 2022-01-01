package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * History of mining events
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MiningHistoryJpa {
  @Id
  @NonNull
  @Column(name = "transactionId", unique = true, updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Column(name = "mined_amount", updatable = false, nullable = false)
  private Integer minedAmount;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "mined_resource", updatable = false, nullable = false)
  private MiningHistoryResourceJpa resourceJpa;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}