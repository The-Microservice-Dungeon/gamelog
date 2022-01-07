package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.TrophyType;
import com.github.tmd.gamelog.domain.trophies.achievements.*;
import com.github.tmd.gamelog.domain.trophies.scoreboard.*;
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

    /**
     * Test mapping from Trophy entity classes to TrophyDto.
     * Will fail with an exception if one of the Trophy subclasses has no no-args constructor.
     */
    @Test
    void testMapEntityToDto() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testFromTrophyEntityToDto();
        testFromAchievementTrophyEntityToDto();
        testFromScoreboardTrophyEntityToDto();
    }

    /**
     * Test mapping from TrophyDto to Trophy entity classes.
     * Will fail with an exception if one of the Trophy subclasses has no no-args constructor.
     */
    @Test
    void testMapDtoToEntity() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testFromDtoToTrophyEntity();
        testFromDtoToAchievementTrophyEntity();
        testFromDtoToScoreboardTrophyEntity();
    }

    /**
     * Test mapping Trophy to TrophyDto.
     * Will fail with an exception if the Trophy class has no no-args constructor.
     */
    private void testFromTrophyEntityToDto() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromEntityTypeToDtoType(Trophy.class, TrophyType.Trophy);
    }

    /**
     * Test mapping achievement Trophy entities to TrophyDto.
     * Will fail with an exception if the Trophy subclasses has no no-args constructor.
     */
    private void testFromAchievementTrophyEntityToDto() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromEntityTypeToDtoType(FightingBronzeTrophy.class, TrophyType.FightingBronzeTrophy);
        testFromEntityTypeToDtoType(FightingSilverTrophy.class, TrophyType.FightingSilverTrophy);
        testFromEntityTypeToDtoType(FightingGoldTrophy.class, TrophyType.FightingGoldTrophy);
        testFromEntityTypeToDtoType(MiningBronzeTrophy.class, TrophyType.MiningBronzeTrophy);
        testFromEntityTypeToDtoType(MiningSilverTrophy.class, TrophyType.MiningSilverTrophy);
        testFromEntityTypeToDtoType(MiningGoldTrophy.class, TrophyType.MiningGoldTrophy);
        testFromEntityTypeToDtoType(TradingBronzeTrophy.class, TrophyType.TradingBronzeTrophy);
        testFromEntityTypeToDtoType(TradingSilverTrophy.class, TrophyType.TradingSilverTrophy);
        testFromEntityTypeToDtoType(TradingGoldTrophy.class, TrophyType.TradingGoldTrophy);
        testFromEntityTypeToDtoType(TravelingBronzeTrophy.class, TrophyType.TravelingBronzeTrophy);
        testFromEntityTypeToDtoType(TravelingSilverTrophy.class, TrophyType.TravelingSilverTrophy);
        testFromEntityTypeToDtoType(TravelingGoldTrophy.class, TrophyType.TravelingGoldTrophy);
    }

    /**
     * Test mapping scoreboard Trophy entities to TrophyDto.
     * Will fail with an exception if the Trophy subclasses has no no-args constructor.
     */
    private void testFromScoreboardTrophyEntityToDto() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromEntityTypeToDtoType(GameThirdPlaceTrophy.class, TrophyType.GameThirdPlaceTrophy);
        testFromEntityTypeToDtoType(GameSecondPlaceTrophy.class, TrophyType.GameSecondPlaceTrophy);
        testFromEntityTypeToDtoType(GameFirstPlaceTrophy.class, TrophyType.GameFirstPlaceTrophy);
        testFromEntityTypeToDtoType(FightingThirdPlaceTrophy.class, TrophyType.FightingThirdPlaceTrophy);
        testFromEntityTypeToDtoType(FightingSecondPlaceTrophy.class, TrophyType.FightingSecondPlaceTrophy);
        testFromEntityTypeToDtoType(FightingFirstPlaceTrophy.class, TrophyType.FightingFirstPlaceTrophy);
        testFromEntityTypeToDtoType(MiningThirdPlaceTrophy.class, TrophyType.MiningThirdPlaceTrophy);
        testFromEntityTypeToDtoType(MiningSecondPlaceTrophy.class, TrophyType.MiningSecondPlaceTrophy);
        testFromEntityTypeToDtoType(MiningFirstPlaceTrophy.class, TrophyType.MiningFirstPlaceTrophy);
        testFromEntityTypeToDtoType(TradingThirdPlaceTrophy.class, TrophyType.TradingThirdPlaceTrophy);
        testFromEntityTypeToDtoType(TradingSecondPlaceTrophy.class, TrophyType.TradingSecondPlaceTrophy);
        testFromEntityTypeToDtoType(TradingFirstPlaceTrophy.class, TrophyType.TradingFirstPlaceTrophy);
        testFromEntityTypeToDtoType(TravelingThirdPlaceTrophy.class, TrophyType.TravelingThirdPlaceTrophy);
        testFromEntityTypeToDtoType(TravelingSecondPlaceTrophy.class, TrophyType.TravelingSecondPlaceTrophy);
        testFromEntityTypeToDtoType(TravelingFirstPlaceTrophy.class, TrophyType.TravelingFirstPlaceTrophy);
    }

    /**
     * Test mapping TrophyDto to Trophy.
     * Will fail with an exception if the Trophy class has no no-args constructor.
     */
    private void testFromDtoToTrophyEntity() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromDtoTypeToEntityType(Trophy.class, TrophyType.Trophy);
    }

    /**
     * Test mapping from TrophyDto to achievement Trophy entities.
     * Will fail with an exception if one of the Trophy subclasses a no no-args constructor.
     */
    private void testFromDtoToAchievementTrophyEntity() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromDtoTypeToEntityType(FightingBronzeTrophy.class, TrophyType.FightingBronzeTrophy);
        testFromDtoTypeToEntityType(FightingSilverTrophy.class, TrophyType.FightingSilverTrophy);
        testFromDtoTypeToEntityType(FightingGoldTrophy.class, TrophyType.FightingGoldTrophy);
        testFromDtoTypeToEntityType(MiningBronzeTrophy.class, TrophyType.MiningBronzeTrophy);
        testFromDtoTypeToEntityType(MiningSilverTrophy.class, TrophyType.MiningSilverTrophy);
        testFromDtoTypeToEntityType(MiningGoldTrophy.class, TrophyType.MiningGoldTrophy);
        testFromDtoTypeToEntityType(TradingBronzeTrophy.class, TrophyType.TradingBronzeTrophy);
        testFromDtoTypeToEntityType(TradingSilverTrophy.class, TrophyType.TradingSilverTrophy);
        testFromDtoTypeToEntityType(TradingGoldTrophy.class, TrophyType.TradingGoldTrophy);
        testFromDtoTypeToEntityType(TravelingBronzeTrophy.class, TrophyType.TravelingBronzeTrophy);
        testFromDtoTypeToEntityType(TravelingSilverTrophy.class, TrophyType.TravelingSilverTrophy);
        testFromDtoTypeToEntityType(TravelingGoldTrophy.class, TrophyType.TravelingGoldTrophy);
    }

    /**
     * Test mapping from TrophyDto to scoreboard Trophy entities.
     * Will fail with an exception if one of the Trophy subclasses a no no-args constructor.
     */
    private void testFromDtoToScoreboardTrophyEntity() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testFromDtoTypeToEntityType(GameThirdPlaceTrophy.class, TrophyType.GameThirdPlaceTrophy);
        testFromDtoTypeToEntityType(GameSecondPlaceTrophy.class, TrophyType.GameSecondPlaceTrophy);
        testFromDtoTypeToEntityType(GameFirstPlaceTrophy.class, TrophyType.GameFirstPlaceTrophy);
        testFromDtoTypeToEntityType(FightingThirdPlaceTrophy.class, TrophyType.FightingThirdPlaceTrophy);
        testFromDtoTypeToEntityType(FightingSecondPlaceTrophy.class, TrophyType.FightingSecondPlaceTrophy);
        testFromDtoTypeToEntityType(FightingFirstPlaceTrophy.class, TrophyType.FightingFirstPlaceTrophy);
        testFromDtoTypeToEntityType(MiningThirdPlaceTrophy.class, TrophyType.MiningThirdPlaceTrophy);
        testFromDtoTypeToEntityType(MiningSecondPlaceTrophy.class, TrophyType.MiningSecondPlaceTrophy);
        testFromDtoTypeToEntityType(MiningFirstPlaceTrophy.class, TrophyType.MiningFirstPlaceTrophy);
        testFromDtoTypeToEntityType(TradingThirdPlaceTrophy.class, TrophyType.TradingThirdPlaceTrophy);
        testFromDtoTypeToEntityType(TradingSecondPlaceTrophy.class, TrophyType.TradingSecondPlaceTrophy);
        testFromDtoTypeToEntityType(TradingFirstPlaceTrophy.class, TrophyType.TradingFirstPlaceTrophy);
        testFromDtoTypeToEntityType(TravelingThirdPlaceTrophy.class, TrophyType.TravelingThirdPlaceTrophy);
        testFromDtoTypeToEntityType(TravelingSecondPlaceTrophy.class, TrophyType.TravelingSecondPlaceTrophy);
        testFromDtoTypeToEntityType(TravelingFirstPlaceTrophy.class, TrophyType.TravelingFirstPlaceTrophy);
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
