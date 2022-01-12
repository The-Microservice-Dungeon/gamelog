package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository for PlayerDto objects.
 */
@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerDto, UUID> {

}
