package com.github.tmd.gamelog.adapter.jpa.player;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PlayerJpa {
  @Id
  @Column(name = "player_id", unique = true, updatable = false)
  private UUID playerId;

  @Column(name = "player_name", nullable = false)
  private String userName;
}
