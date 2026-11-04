# Progress Valuation Service

Bounded context: **Progress Valuations** — Contract-value accounts per project and the certified progress certificates against them.

Alignment: Contract administration: certified progress discipline. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/valuations` |
| Owns data | CertifiedProgress accounts, ProgressCertificate records |
| Publishes | `valuation.consumed`, `valuation.insufficient` |

Run locally: `mvn -pl services/progress-valuation-service quarkus:dev`.
