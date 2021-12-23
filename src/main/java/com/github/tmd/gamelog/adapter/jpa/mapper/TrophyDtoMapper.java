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
        TrophyDto trophyDto = new TrophyDto();
        trophyDto.setName(trophyEntity.getName());
        return trophyDto;
    }

    public Trophy mapDtoToEntity(TrophyDto trophyDto) {
        Trophy trophy = new Trophy(trophyDto.getName());
        return trophy;
    }
}
