package org.openindustries.construction.valuation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ValuationService {

    @Inject
    ValuationRepository repository;

    public CertifiedProgress open(OpenCertifiedProgressRequest request) {
        return repository.saveAccount(CertifiedProgress.open(request.projectId(), request.contractValueMinor()));
    }

    public CertifiedProgress get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "CertifiedProgress not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public ProgressCertificate consume(UUID accountId, CreateProgressCertificateRequest request) {
        CertifiedProgress account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "CertifiedProgress is not ACTIVE");
        }
        if (request.amountMinor() > account.contractValueMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.contractValueMinor);
        }
        account.contractValueMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(ProgressCertificate.of(accountId, request.workType(), request.amountMinor(), account.contractValueMinor));
    }

    public List<ProgressCertificate> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
