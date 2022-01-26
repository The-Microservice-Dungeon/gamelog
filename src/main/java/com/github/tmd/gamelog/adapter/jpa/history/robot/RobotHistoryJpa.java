package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * History of robot states.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"round_id", "robot_id"})
})
public class RobotHistoryJpa {
  @Id
  @Type(type = "uuid-char")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "round_id", updatable = false, nullable = false)
  private UUID roundId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "robot_id", updatable = false, nullable = false)
  private UUID robotId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "player_id", updatable = false, nullable = false)
  private UUID playerId;

  @NonNull
  @Type(type="uuid-char")
  @Column(name = "planet_id", updatable = false, nullable = false)
  private UUID planetId;

  @NonNull
  @Column(name = "alive", updatable = false, nullable = false)
  private Boolean alive;

  @NonNull
  @Column(name = "max_health", updatable = false, nullable = false)
  private Integer maxHealth;

  @NonNull
  @Column(name = "max_energy", updatable = false, nullable = false)
  private Integer maxEnergy;

  @NonNull
  @Column(name = "energy_regen", updatable = false, nullable = false)
  private Integer energyRegen;

  @NonNull
  @Column(name = "attack_damage", updatable = false, nullable = false)
  private Integer attackDamage;

  @NonNull
  @Column(name = "mining_speed", updatable = false, nullable = false)
  private Integer miningSpeed;

  @NonNull
  @Column(name = "health", updatable = false, nullable = false)
  private Integer health;

  @NonNull
  @Column(name = "energy", updatable = false, nullable = false)
  private Integer energy;

  @NonNull
  @Column(name = "health_level", updatable = false, nullable = false)
  private Integer healthLevel;

  @NonNull
  @Column(name = "damage_level", updatable = false, nullable = false)
  private Integer damageLevel;

  @NonNull
  @Column(name = "mining_speed_level", updatable = false, nullable = false)
  private Integer miningSpeedLevel;

  @NonNull
  @Column(name = "mining_level", updatable = false, nullable = false)
  private Integer miningLevel;

  @NonNull
  @Column(name = "energy_level", updatable = false, nullable = false)
  private Integer energyLevel;

  @NonNull
  @Column(name = "energy_regen_level", updatable = false, nullable = false)
  private Integer energyRegenLevel;

  @NonNull
  @Column(name = "storage_level", updatable = false, nullable = false)
  private Integer storageLevel;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdDate;
}
