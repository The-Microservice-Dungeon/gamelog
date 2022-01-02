package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * History of movement events
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MovementHistoryJpa {
  @Id
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  private UUID id = UUID.randomUUID();

  @NaturalId
  @NonNull
  @Type(type="uuid-char")
  @Column(name = "transaction_id", unique = true, updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Type(type="uuid-char")
  @ElementCollection
  private Set<UUID> robots;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "planet_id", updatable = false, nullable = false)
  private UUID planetId;

  @NonNull
  @Column(name = "movement_difficulty", updatable = false, nullable = false)
  private Integer movementDifficulty;

  @NonNull
  @Column(name = "timestamp", nullable = false, updatable = false)
  private Instant timestamp;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
