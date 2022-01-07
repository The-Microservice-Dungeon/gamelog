package com.github.tmd.gamelog.domain.trophies;

/**
 * Enum for the Trophy types.
 * The enum is used to store the Trophy (sub)class on the database
 * to make mapping of TrophyDto to Trophy subclasses easier.
 */
public enum TrophyType {

    Trophy,
    FightingBronzeTrophy,
    FightingSilverTrophy,
    FightingGoldTrophy,
    MiningBronzeTrophy,
    MiningSilverTrophy,
    MiningGoldTrophy,
    TradingBronzeTrophy,
    TradingSilverTrophy,
    TradingGoldTrophy,
    TravelingBronzeTrophy,
    TravelingSilverTrophy,
    TravelingGoldTrophy

}
