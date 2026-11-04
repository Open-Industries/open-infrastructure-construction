package org.openindustries.construction.asset;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConstructionAssetService {

    @Inject
    ConstructionAssetRepository repository;

    public ConstructionAsset create(CreateConstructionAssetRequest request) {
        return repository.save(ConstructionAsset.create(request.name(), request.assetType(), request.capacityTonnes()));
    }

    public ConstructionAsset get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "ConstructionAsset not found: " + id));
    }

    public List<ConstructionAsset> list() {
        return repository.findAll();
    }

    public ConstructionAsset deploy(UUID id) {
        ConstructionAsset entity = get(id);
        if (!"AVAILABLE".equals(entity.status)) {
            throw new DomainException(409, "Only a AVAILABLE constructionasset can transition via deploy");
        }
        entity.status = "DEPLOYED";
        return repository.save(entity);
    }
}
