package com.github.tmd.gamelog.adapter.jpa;


import com.github.tmd.gamelog.domain.RoundScore;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
public class RoundScoreDto implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    private String game;
    private String round;
    private int roundNumber;
    private String player;

    private int movementScore;

    public static RoundScoreDto fromRoundScore(RoundScore roundScore) {
        //TODO: find better solution
        RoundScoreDto roundScoreDto = new RoundScoreDto();

        roundScoreDto.setMovementScore(roundScore.getMovementScore());
        roundScoreDto.setGame(roundScore.getRound().getGameId());
        roundScoreDto.setRound(roundScore.getRound().getRoundId());
        roundScoreDto.setRoundNumber(roundScore.getRound().getRoundNumber());
        roundScoreDto.setPlayer(roundScore.getPlayer().getId());

        return roundScoreDto;
    }

    @Override
    public String toString() {
        return "RoundScoreDto{" +
                "id=" + id +
                ", game='" + game + '\'' +
                ", round='" + round + '\'' +
                ", player='" + player + '\'' +
                ", movementScore=" + movementScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundScoreDto)) return false;

        RoundScoreDto that = (RoundScoreDto) o;

        if (movementScore != that.movementScore) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(game, that.game)) return false;
        if (!Objects.equals(round, that.round)) return false;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (round != null ? round.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + movementScore;
        return result;
    }

    @Override
    public RoundScoreDto clone() {
        try {
            RoundScoreDto clone = (RoundScoreDto) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
