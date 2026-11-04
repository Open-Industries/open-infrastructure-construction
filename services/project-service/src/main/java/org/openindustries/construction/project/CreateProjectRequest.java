package org.openindustries.construction.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProjectRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "ROAD|BRIDGE|BUILDING|DAM") String projectType,
        @NotBlank String regionCode) {
}
