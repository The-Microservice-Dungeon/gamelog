package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.TrophyType;
import com.github.tmd.gamelog.domain.trophies.achievements.*;
import com.github.tmd.gamelog.domain.trophies.scoreboard.*;
import org.springframework.stereotype.Component;

/**
 * Class for mapping TrophyDto to Trophy and vice-versa.
 */
@Component
public class TrophyDtoMapper {

    public TrophyDto mapEntityToDto(Trophy trophyEntity) {
        TrophyType trophyType = switch (trophyEntity.getClass().getSimpleName()) {
            case "FightingBronzeTrophy" -> TrophyType.FightingBronzeTrophy;
            case "FightingSilverTrophy" -> TrophyType.FightingSilverTrophy;
            case "FightingGoldTrophy" -> TrophyType.FightingGoldTrophy;
            case "MiningBronzeTrophy" -> TrophyType.MiningBronzeTrophy;
            case "MiningSilverTrophy" -> TrophyType.MiningSilverTrophy;
            case "MiningGoldTrophy" -> TrophyType.MiningGoldTrophy;
            case "TradingBronzeTrophy" -> TrophyType.TradingBronzeTrophy;
            case "TradingSilverTrophy" -> TrophyType.TradingSilverTrophy;
            case "TradingGoldTrophy" -> TrophyType.TradingGoldTrophy;
            case "TravelingBronzeTrophy" -> TrophyType.TravelingBronzeTrophy;
            case "TravelingSilverTrophy" -> TrophyType.TravelingSilverTrophy;
            case "TravelingGoldTrophy" -> TrophyType.TravelingGoldTrophy;
            case "GameThirdPlaceTrophy" -> TrophyType.GameThirdPlaceTrophy;
            case "GameSecondPlaceTrophy" -> TrophyType.GameSecondPlaceTrophy;
            case "GameFirstPlaceTrophy" -> TrophyType.GameFirstPlaceTrophy;
            case "FightingThirdPlaceTrophy" -> TrophyType.FightingThirdPlaceTrophy;
            case "FightingSecondPlaceTrophy" -> TrophyType.FightingSecondPlaceTrophy;
            case "FightingFirstPlaceTrophy" -> TrophyType.FightingFirstPlaceTrophy;
            case "MiningThirdPlaceTrophy" -> TrophyType.MiningThirdPlaceTrophy;
            case "MiningSecondPlaceTrophy" -> TrophyType.MiningSecondPlaceTrophy;
            case "MiningFirstPlaceTrophy" -> TrophyType.MiningFirstPlaceTrophy;
            case "TradingThirdPlaceTrophy" -> TrophyType.TradingThirdPlaceTrophy;
            case "TradingSecondPlaceTrophy" -> TrophyType.TradingSecondPlaceTrophy;
            case "TradingFirstPlaceTrophy" -> TrophyType.TradingFirstPlaceTrophy;
            case "TravelingThirdPlaceTrophy" -> TrophyType.TravelingThirdPlaceTrophy;
            case "TravelingSecondPlaceTrophy" -> TrophyType.TravelingSecondPlaceTrophy;
            case "TravelingFirstPlaceTrophy" -> TrophyType.TravelingFirstPlaceTrophy;
            default -> TrophyType.Trophy;
        };
        return new TrophyDto(trophyEntity.getId(), trophyEntity.getName(), trophyEntity.getBadgeUrl(), trophyType);
    }

    public Trophy mapDtoToEntity(TrophyDto trophyDto) {
        Trophy trophy = switch (trophyDto.getTrophyType()) {
            case Trophy -> new Trophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingBronzeTrophy -> new FightingBronzeTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingSilverTrophy -> new FightingSilverTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingGoldTrophy -> new FightingGoldTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningBronzeTrophy -> new MiningBronzeTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningSilverTrophy -> new MiningSilverTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningGoldTrophy -> new MiningGoldTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingBronzeTrophy -> new TradingBronzeTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingSilverTrophy -> new TradingSilverTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingGoldTrophy -> new TradingGoldTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingBronzeTrophy -> new TravelingBronzeTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingSilverTrophy -> new TravelingSilverTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingGoldTrophy -> new TravelingGoldTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case GameThirdPlaceTrophy -> new GameThirdPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case GameSecondPlaceTrophy -> new GameSecondPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case GameFirstPlaceTrophy -> new GameFirstPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingThirdPlaceTrophy -> new FightingThirdPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingSecondPlaceTrophy -> new FightingSecondPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case FightingFirstPlaceTrophy -> new FightingFirstPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningThirdPlaceTrophy -> new MiningThirdPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningSecondPlaceTrophy -> new MiningSecondPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case MiningFirstPlaceTrophy -> new MiningFirstPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingThirdPlaceTrophy -> new TradingThirdPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingSecondPlaceTrophy -> new TradingSecondPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TradingFirstPlaceTrophy -> new TradingFirstPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingThirdPlaceTrophy -> new TravelingThirdPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingSecondPlaceTrophy -> new TravelingSecondPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
            case TravelingFirstPlaceTrophy -> new TravelingFirstPlaceTrophy(trophyDto.getId(), trophyDto.getName(), trophyDto.getBadgeUrl());
        };
        return trophy;
    }
}
