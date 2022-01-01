package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

/**
 * History of planet block events
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
public class PlanetBlockHistoryJpa {
  @Id
  @NonNull
  @Column(name = "transactionId", unique = true, updatable = false, nullable = false)
  private UUID transactionId;

  @NonNull
  @Column(name = "planetId", updatable = false, nullable = false)
  private UUID planetId;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
