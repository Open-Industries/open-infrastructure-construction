# Open Infrastructure & Construction — Reference Construction Core Platform

A **standard, generic infrastructure and construction core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> certified progress value on a project never exceeds its contract value — a progress certificate that would overrun it is rejected (422) and records nothing.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| ISO 19650 | BIM information management | `docs/domain/domain-standards.md` mapping |
| IFC / ISO 16739 | Industry Foundation Classes data schema | Asset/element data posture |
| ISO 45001 | Occupational health & safety | Safety rules context and risk scoring |
| ISO 31000 | Risk management | Risk process posture in governance docs |
| ISO 9001 | Quality management | Quality gates in the life cycle |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `project-service` | 8081 | ISO 19650 project information posture | Infrastructure project registry. |
| `progress-valuation-service` | 8082 | Contract administration: certified progress discipline | Contract-value accounts per project and the certified progress certificates against them. |
| `site-safety-rules-service` | 8083 | ISO 45001 operational controls as auditable rules | Decision rules binding activity types to crew-size and incident-rate envelopes. |
| `construction-asset-service` | 8084 | Plant & equipment master data (IFC asset posture) | Fleet registry for site equipment. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `site-risk-scorer` | Event / HTTP | Scores project risk signals (incidents, delay, weather exposure) with an auditable rationale. |
| `progress-compliance-evaluator` | Event / HTTP | Compares defect counts and schedule adherence against project targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — certified progress value on a project never exceeds its contract value — a progress certificate that would overrun it is rejected (422) and records nothing.
3. **Privacy by design** — Worker personal data stays in adopter HR/safety systems; this core tracks projects, valuations, standards and assets. Site imagery and personal identifiers are out of scope by design; all data is synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/project-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `site-safety-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `progress-valuation-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
