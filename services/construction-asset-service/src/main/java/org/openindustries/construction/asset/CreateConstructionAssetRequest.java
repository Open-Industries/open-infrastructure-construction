package org.openindustries.construction.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateConstructionAssetRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "CRANE|EXCAVATOR|MIXER|SCAFFOLD") String assetType,
        @Positive int capacityTonnes) {
}
