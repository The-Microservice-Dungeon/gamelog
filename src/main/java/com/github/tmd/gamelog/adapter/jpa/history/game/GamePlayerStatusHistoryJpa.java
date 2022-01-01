package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GamePlayerStatusHistoryJpa {
  @Id
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Column(name = "game_id", updatable = false, nullable = false, unique = true)
  private UUID gameId;

  @NonNull
  @Column(name = "user_id", updatable = false, nullable = false, unique = true)
  private UUID userId;

  @NonNull
  @Column(name = "user_name", updatable = false, nullable = false, unique = true)
  private String userName;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", updatable = false, nullable = false, unique = true)
  private GamePlayerStatusJpa status;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}