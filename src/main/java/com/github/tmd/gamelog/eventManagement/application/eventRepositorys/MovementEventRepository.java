package com.github.tmd.gamelog.eventManagement.application.eventRepositorys;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.MovementEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementEventRepository extends CrudRepository<MovementEvent, Long> {
    List<MovementEvent> findByRound(int round);
}
