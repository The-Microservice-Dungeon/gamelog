package com.github.tmd.gamelog.adapter.jpa.history.command;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandHistoryJpaRepository extends CrudRepository<CommandHistoryJpa, UUID> {
  Set<CommandHistoryJpa> findAll();
}
