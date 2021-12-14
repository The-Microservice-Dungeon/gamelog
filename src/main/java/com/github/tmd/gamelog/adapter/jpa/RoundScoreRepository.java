package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.stereotype.Component;

@Component
public class RoundScoreRepository implements com.github.tmd.gamelog.domain.RoundScoreRepository {

    private final RoundScoreDtoMapper roundScoreDtoMapper;
    private final RoundScoreJpaRepository roundScoreJpaRepository;

    public RoundScoreRepository(
        RoundScoreJpaRepository roundScoreJpaRepository,
        RoundScoreDtoMapper roundScoreDtoMapper
    ) {
        this.roundScoreJpaRepository = roundScoreJpaRepository;
        this.roundScoreDtoMapper = roundScoreDtoMapper;
    }

    public RoundScore findByCommandContext(CommandContext commandContext) {
        RoundScoreDto roundScoreDto = this.fetchDtoByCommandContext(commandContext);

        if(null == roundScoreDto) {
            return null;
        }

        return this.roundScoreDtoMapper.mapDtoToEntity(roundScoreDto);
    }

    private RoundScoreDto fetchDtoByCommandContext(CommandContext commandContext) {
        return roundScoreJpaRepository.findByGameAndRoundAndPlayer(
                commandContext.getRound().getGameId(),
                commandContext.getRound().getRoundId(),
                commandContext.getPlayer().getId()
        );
    }

    public void upsert(RoundScore roundScore) {
        RoundScoreDto databaseRoundScoreDto = this.fetchDtoByRoundScore(roundScore);
        RoundScoreDto roundScoreDto = this.roundScoreDtoMapper.mapEntityToDto(roundScore);

        if (databaseRoundScoreDto != null) {
            roundScoreDto.setId(databaseRoundScoreDto.getId());
        }

        this.roundScoreJpaRepository.save(roundScoreDto);
    }


    private RoundScoreDto fetchDtoByRoundScore(RoundScore roundScore) {
        return this.roundScoreJpaRepository.findByGameAndRoundAndPlayer(
                roundScore.getRound().getGameId(),
                roundScore.getRound().getRoundId(),
                roundScore.getPlayer().getId()
        );
    }
}
