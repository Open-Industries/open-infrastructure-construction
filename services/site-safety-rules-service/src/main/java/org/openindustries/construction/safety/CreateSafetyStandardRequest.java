package org.openindustries.construction.safety;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateSafetyStandardRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "EXCAVATION|HEIGHT_WORK|CRANE_LIFT|ELECTRICAL") String activityType,
        @Positive long maxCrewSize,
        @Positive int maxIncidentRate,
        @Min(1) @Max(100) int priority) {
}
