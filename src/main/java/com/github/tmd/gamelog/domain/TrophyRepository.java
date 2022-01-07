package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface for the repository of Trophy objects.
 */
@Repository
public interface TrophyRepository {

    ArrayList<Trophy> findAll();

    void upsert(Trophy trophy);

}
