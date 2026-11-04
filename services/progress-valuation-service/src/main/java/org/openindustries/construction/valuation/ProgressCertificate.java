package org.openindustries.construction.valuation;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a CertifiedProgress. */
public class ProgressCertificate {
    public UUID id;
    public UUID accountId;
    public String workType;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static ProgressCertificate of(UUID accountId, String workType, long amountMinor, long remainingAfter) {
        ProgressCertificate r = new ProgressCertificate();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.workType = workType;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
