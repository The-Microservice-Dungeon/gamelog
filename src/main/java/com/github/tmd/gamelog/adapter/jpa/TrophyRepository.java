package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.adapter.jpa.mapper.TrophyDtoMapper;
import com.github.tmd.gamelog.domain.trophies.Trophy;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for objects of class Trophy.
 */
@Repository
public class TrophyRepository implements com.github.tmd.gamelog.domain.TrophyRepository {

    private final TrophyJpaRepository trophyJpaRepository;
    private final TrophyDtoMapper trophyDtoMapper;

    @Autowired
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

    /**
     * Initialize the repository with the list of default trophies.
     */
    public void initRepository() {
        for (Trophy trophy : getMissingDefaultTrophies()) {
            trophyJpaRepository.save(trophyDtoMapper.mapEntityToDto(trophy));
        }
    }

    /**
     * Get the list of default trophies that are still missing on the repo.
     *
     * @return List of default trophies that are missing on the repo.
     */
    private ArrayList<Trophy> getMissingDefaultTrophies() {
        ArrayList<Trophy> trophiesOnTheRepo = findAll();
        ArrayList<Trophy> requiredTrophies = Trophy.getDefaultTrophies();
        for (Trophy trophy : trophiesOnTheRepo) {
            trophy.setId(0);
            requiredTrophies.remove(trophy);
        }
        return requiredTrophies;
    }

}
