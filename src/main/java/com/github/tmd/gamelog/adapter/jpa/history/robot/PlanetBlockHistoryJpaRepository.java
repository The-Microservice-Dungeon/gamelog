package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetBlockHistoryJpaRepository extends
    JpaRepository<PlanetBlockHistoryJpa, UUID> {

}
