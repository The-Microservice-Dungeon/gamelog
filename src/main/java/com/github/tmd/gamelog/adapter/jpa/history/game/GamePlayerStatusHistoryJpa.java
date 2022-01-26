package com.github.tmd.gamelog.adapter.jpa.history.game;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
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
public class GamePlayerStatusHistoryJpa {
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
  @Column(name = "user_id", updatable = false, nullable = false)
  private UUID userId;

  @NonNull
  @Column(name = "user_name", updatable = false, nullable = false)
  private String userName;

  @NonNull
  @Column(name = "timestamp", nullable = false, updatable = false)
  private Instant timestamp;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;

  @PrePersist
  void preInsert() {
    // If no username is available, just use the userId as a username
    if(this.userName == null || this.userName.isBlank()) {
      this.userName = this.userId.toString();
    }
  }
}
