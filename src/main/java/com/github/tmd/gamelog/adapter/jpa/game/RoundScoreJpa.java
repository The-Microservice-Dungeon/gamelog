package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"player_id", "round_id"})
})
public class RoundScoreJpa {
  @ManyToOne(optional = false)
  @JoinColumn(name = "player_id", nullable = false, updatable = false)
  private PlayerJpa player;

  // TODO: Delete when necessary
  @Column(name = "tmp_tst_score")
  private Integer testScore;
}
