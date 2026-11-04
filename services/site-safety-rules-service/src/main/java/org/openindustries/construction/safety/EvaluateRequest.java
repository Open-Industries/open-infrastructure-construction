package org.openindustries.construction.safety;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "EXCAVATION|HEIGHT_WORK|CRANE_LIFT|ELECTRICAL") String activityType,
        @Positive int requiredMaxIncidentRate,
        @Positive long requiredMaxCrewSize) {
}
