package com.github.tmd.gamelog.eventManagement.application.eventRepositorys;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.MovementEvent;
import org.springframework.data.repository.CrudRepository;

public interface MovementEventRepository extends CrudRepository {
    List<MovementEvent> findByRound(int round);
}
