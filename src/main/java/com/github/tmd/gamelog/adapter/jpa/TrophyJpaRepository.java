package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository for TrophyDto objects.
 */
@Repository
public interface TrophyJpaRepository extends JpaRepository<TrophyDto, Long> {

}
