package com.github.tmd.gamelog.adapter.jpa.score;

import java.util.Map;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.hibernate.annotations.Type;

/**
 * Holds all scores wihtin a single round
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "round_scores")
public class RoundScoreJpa {
  @Id
  @Type(type="uuid-char")
  @Column(name = "round_id", updatable = false, nullable = false, unique = true)
  private UUID roundId;

  /**
   * Player -> Aggregated Round Scores
   */
  @ElementCollection
  @CollectionTable(name = "player_round_scores", joinColumns = {@JoinColumn(name = "round_id")})
  @MapKeyColumn(name = "player_id")
  private Map<String, RoundScoreJpaEmbeddable> playerRoundScores;
}
