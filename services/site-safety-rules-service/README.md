# Site Safety Rules Service

Bounded context: **Site Safety Rules** — Decision rules binding activity types to crew-size and incident-rate envelopes.

Alignment: ISO 45001 operational controls as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/site-safety-rules` |
| Owns data | SafetyStandard rules |
| Publishes | `safety.activated` |

Run locally: `mvn -pl services/site-safety-rules-service quarkus:dev`.
