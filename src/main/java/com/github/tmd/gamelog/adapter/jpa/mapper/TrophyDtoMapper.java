package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Trophy;
import org.springframework.stereotype.Component;

/**
 * Class for mapping TrophyDto to Trophy and vice-versa.
 */
@Component
public class TrophyDtoMapper {

    public TrophyDto mapEntityToDto(Trophy trophyEntity) {
        TrophyDto trophyDto = new TrophyDto(trophyEntity.getId(), trophyEntity.getName(), trophyEntity.getBadgeUrl());
        return trophyDto;
    }

    public Trophy mapDtoToEntity(TrophyDto trophyDto) {
        Trophy trophy = new Trophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
        return trophy;
    }
}
