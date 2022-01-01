package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerBalanceHistoryJpaRepository extends CrudRepository<PlayerBalanceHistoryJpa, UUID> {

}
