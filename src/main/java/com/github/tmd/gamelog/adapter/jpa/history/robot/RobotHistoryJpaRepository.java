package com.github.tmd.gamelog.adapter.jpa.history.robot;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface RobotHistoryJpaRepository extends CrudRepository<RobotHistoryJpa, UUID> {

}
