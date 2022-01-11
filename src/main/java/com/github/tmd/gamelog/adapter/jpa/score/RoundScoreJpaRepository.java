package com.github.tmd.gamelog.adapter.jpa.score;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundScoreJpaRepository extends JpaRepository<RoundScoreJpa, UUID> {

}
