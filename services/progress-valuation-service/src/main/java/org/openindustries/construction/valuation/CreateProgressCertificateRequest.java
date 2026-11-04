package org.openindustries.construction.valuation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProgressCertificateRequest(
        @NotBlank @Pattern(regexp = "EARTHWORK|STRUCTURE|FINISHING|SYSTEMS") String workType,
        @Positive long amountMinor) {
}
