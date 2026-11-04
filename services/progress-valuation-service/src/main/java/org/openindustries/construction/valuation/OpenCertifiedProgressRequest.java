package org.openindustries.construction.valuation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenCertifiedProgressRequest(
        @NotNull java.util.UUID projectId,
        @PositiveOrZero long contractValueMinor) {
}
