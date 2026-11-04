# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Projects → Progress Valuations → Site Safety Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `project-service` | 8081 | ISO 19650 project information posture |
| `progress-valuation-service` | 8082 | Contract administration: certified progress discipline |
| `site-safety-rules-service` | 8083 | ISO 45001 operational controls as auditable rules |
| `construction-asset-service` | 8084 | Plant & equipment master data (IFC asset posture) |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/project-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
