package com.github.tmd.gamelog.adapter.rest_client.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public record RobotDto(
    @JsonProperty("id") UUID id,
    @JsonProperty("player") UUID player,
    @JsonProperty("planet") UUID planet,
    @JsonProperty("alive") Boolean alive,
    @JsonProperty("maxHealth") Integer maxHealth,
    @JsonProperty("maxEnergy") Integer maxEnergy,
    @JsonProperty("energyRegen") Integer energyRegen,
    @JsonProperty("attackDamage") Integer attackDamage,
    @JsonProperty("miningSpeed") Integer miningSpeed,
    @JsonProperty("health") Integer health,
    @JsonProperty("energy") Integer energy,
    @JsonProperty("healthLevel") Integer healthLevel,
    @JsonProperty("damageLevel") Integer damageLevel,
    @JsonProperty("miningSpeedLevel") Integer miningSpeedLevel,
    @JsonProperty("miningLevel") Integer miningLevel,
    @JsonProperty("energyLevel") Integer energyLevel,
    @JsonProperty("energyRegenLevel") Integer energyRegenLevel,
    @JsonProperty("storageLevel") Integer storageLevel
) { }
