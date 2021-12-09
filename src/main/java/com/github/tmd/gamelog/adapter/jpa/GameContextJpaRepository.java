package com.github.tmd.gamelog.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameContextJpaRepository extends JpaRepository<GameContextDto, Long> {
}
