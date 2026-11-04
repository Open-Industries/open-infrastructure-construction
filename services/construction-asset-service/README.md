# Construction Asset Service

Bounded context: **Construction Assets** — Fleet registry for site equipment.

Alignment: Plant & equipment master data (IFC asset posture). Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/construction-assets` |
| Owns data | ConstructionAsset records |

Run locally: `mvn -pl services/construction-asset-service quarkus:dev`.
