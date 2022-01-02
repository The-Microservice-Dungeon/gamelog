package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.Trophy;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for objects of class Trophy.
 */
public class TrophyRepository implements com.github.tmd.gamelog.domain.TrophyRepository {

    private final TrophyJpaRepository trophyJpaRepository;
    private final TrophyDtoMapper trophyDtoMapper;

    public TrophyRepository(TrophyJpaRepository trophyJpaRepository, TrophyDtoMapper trophyDtoMapper) {
        this.trophyJpaRepository = trophyJpaRepository;
        this.trophyDtoMapper = trophyDtoMapper;
    }

    @Override
    public ArrayList<Trophy> findAll() {
        List<TrophyDto> trophyDtos = trophyJpaRepository.findAll();
        ArrayList<Trophy> trophies = new ArrayList<>();
        for (TrophyDto trophyDto : trophyDtos) {
            trophies.add(trophyDtoMapper.mapDtoToEntity(trophyDto));
        }
        return trophies;
    }

    @Override
    public void upsert(Trophy trophy) {
        TrophyDto trophyDtoFromTrophy = trophyDtoMapper.mapEntityToDto(trophy);
        trophyJpaRepository.save(trophyDtoFromTrophy);
    }

}
