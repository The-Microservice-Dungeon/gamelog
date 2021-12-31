package com.github.tmd.gamelog.adapter.jpa.player;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerJpaRepository extends CrudRepository<PlayerJpa, UUID> {

}
