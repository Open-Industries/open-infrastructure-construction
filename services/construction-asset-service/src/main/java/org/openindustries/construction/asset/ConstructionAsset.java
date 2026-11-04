package org.openindustries.construction.asset;

import java.util.UUID;

/** ConstructionAsset aggregate — Fleet registry for site equipment. */
public class ConstructionAsset {
    public UUID id;
        public String name;
        public String assetType;
        public int capacityTonnes;
    public String status;

    public static ConstructionAsset create(String name, String assetType, int capacityTonnes) {
        ConstructionAsset e = new ConstructionAsset();
        e.id = UUID.randomUUID();
        e.name = name;
        e.assetType = assetType;
        e.capacityTonnes = capacityTonnes;
        e.status = "AVAILABLE";
        return e;
    }
}
