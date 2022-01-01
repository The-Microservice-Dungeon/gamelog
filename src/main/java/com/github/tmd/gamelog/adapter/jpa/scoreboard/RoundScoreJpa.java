package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"player_id", "round_id"})
})
class RoundScoreJpa {
  @ManyToOne(optional = false)
  @JoinColumn(name = "player_id")
  private PlayerJpa player;

  @ManyToOne(optional = false)
  @JoinColumn(name = "round_id")
  private RoundJpa round;

  // e.g. Integer balance, Integer numOfKills, ...

}
