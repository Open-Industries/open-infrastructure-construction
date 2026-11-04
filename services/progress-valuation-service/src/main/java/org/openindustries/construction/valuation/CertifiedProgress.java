package org.openindustries.construction.valuation;

import java.util.UUID;

/**
 * CertifiedProgress aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class CertifiedProgress {
    public UUID id;
        public java.util.UUID projectId;
    public long contractValueMinor;
    public String status;

    public static CertifiedProgress open(java.util.UUID projectId, long contractValueMinor) {
        CertifiedProgress a = new CertifiedProgress();
        a.id = UUID.randomUUID();
        a.projectId = projectId;
        a.contractValueMinor = contractValueMinor;
        a.status = "ACTIVE";
        return a;
    }
}
