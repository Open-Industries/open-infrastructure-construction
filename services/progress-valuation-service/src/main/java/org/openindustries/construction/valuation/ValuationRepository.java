package org.openindustries.construction.valuation;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ValuationRepository {
    private final ConcurrentHashMap<UUID, CertifiedProgress> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<ProgressCertificate> records = new CopyOnWriteArrayList<>();

    public CertifiedProgress saveAccount(CertifiedProgress account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<CertifiedProgress> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public ProgressCertificate saveRecord(ProgressCertificate record) {
        records.add(record);
        return record;
    }

    public List<ProgressCertificate> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
