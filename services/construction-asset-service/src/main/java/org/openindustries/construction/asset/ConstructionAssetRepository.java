package org.openindustries.construction.asset;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ConstructionAssetRepository {
    private final ConcurrentHashMap<UUID, ConstructionAsset> store = new ConcurrentHashMap<>();

    public ConstructionAsset save(ConstructionAsset entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<ConstructionAsset> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ConstructionAsset> findAll() {
        return List.copyOf(store.values());
    }
}
