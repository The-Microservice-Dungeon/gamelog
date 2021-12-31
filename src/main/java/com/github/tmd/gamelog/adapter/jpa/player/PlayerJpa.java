package com.github.tmd.gamelog.adapter.jpa.player;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlayerJpa {
  @Id
  @Column(name = "player_id", unique = true, updatable = false)
  private UUID playerId;

  @Column(name = "player_username")
  private String userName;
}
