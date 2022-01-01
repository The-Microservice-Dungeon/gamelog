package com.github.tmd.gamelog.adapter.jpa.history.trading;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TradingHistoryJpaRepository extends CrudRepository<TradingHistoryJpa, UUID> {

}
