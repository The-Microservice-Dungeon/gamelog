package com.github.tmd.gamelog.adapter.jpa.game;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameJpaRepository extends CrudRepository<GameJpa, UUID> {
}
