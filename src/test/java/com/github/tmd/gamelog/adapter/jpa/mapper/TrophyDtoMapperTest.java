package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.TrophyType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the TrophyDtoMapper class.
 */
public class TrophyDtoMapperTest {

    private static TrophyDtoMapper trophyDtoMapper;

    @BeforeAll
    static void beforeAll() {
        trophyDtoMapper = new TrophyDtoMapper();
    }

    @Test
    void testMapEntityToDto() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testFromEntityTypeToDtoType(Trophy.class, TrophyType.Trophy);
    }

    @Test
    void testMapDtoToEntity() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testFromDtoTypeToEntityType(Trophy.class, TrophyType.Trophy);
    }

    /**
     * Generalized test for testing mapping from Trophy domain/entity types to TrophyDto.
     * @param trophyClass Trophy (sub)class.
     * @param trophyType TrophyType matching the Trophy (sub)class.
     * @param <T> Has to be Trophy or a class that extends Trophy.
     * @throws NoSuchMethodException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws InvocationTargetException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws InstantiationException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws IllegalAccessException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     */
    private <T extends Trophy> void testFromEntityTypeToDtoType(Class<T> trophyClass, TrophyType trophyType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Instantiate an instance of the trophy entity class.
        Constructor<T> constructor = trophyClass.getConstructor();
        Trophy trophyEntity = constructor.newInstance();

        // Map the trophy entity to its DTO counterpart.
        TrophyDto trophyDto = trophyDtoMapper.mapEntityToDto(trophyEntity);

        // Run tests on the mapped DTO.
        assertThat(trophyDto.getId()).isEqualTo(trophyEntity.getId());
        assertThat(trophyDto.getName()).isEqualTo(trophyEntity.getName());
        assertThat(trophyDto.getTrophyType()).isEqualTo(trophyType);
    }

    /**
     * Generalized test for testing mapping from TrophyDto to Trophy domain/entity types.
     * @param trophyClass Trophy (sub)class.
     * @param trophyType TrophyType matching the Trophy (sub)class.
     * @param <T> Has to be Trophy or a class that extends Trophy.
     * @throws NoSuchMethodException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws InvocationTargetException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws InstantiationException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     * @throws IllegalAccessException Test fails if his is thrown. Shouldn't happen, as all Trophy classes have a no-args constructor.
     */
    private <T extends Trophy> void testFromDtoTypeToEntityType(Class<T> trophyClass, TrophyType trophyType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Instantiate an instance of the trophy class to get the values for the DTO constructor.
        Constructor<T> constructor = trophyClass.getConstructor();
        Trophy trophyEntityTemplate = constructor.newInstance();

        // Instantiate the DTO and map it to its entity counterpart.
        TrophyDto trophyDto = new TrophyDto(trophyEntityTemplate.getId(), trophyEntityTemplate.getName(), trophyEntityTemplate.getBadgeUrl(), trophyType);
        Trophy trophyEntity = trophyDtoMapper.mapDtoToEntity(trophyDto);

        // Run tests on the mapped entity.
        assertThat(trophyEntity.getId()).isEqualTo(trophyEntityTemplate.getId());
        assertThat(trophyEntity.getName()).isEqualTo(trophyEntityTemplate.getName());
        assertThat(trophyEntity.getClass()).isEqualTo(trophyEntityTemplate.getClass());
    }

}
