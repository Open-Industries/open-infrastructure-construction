package org.openindustries.construction.safety;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SafetyStandardService {

    @Inject
    SafetyStandardRepository repository;

    public SafetyStandard create(CreateSafetyStandardRequest request) {
        return repository.save(SafetyStandard.create(request.name(), request.activityType(),
                request.maxCrewSize(), request.maxIncidentRate(), request.priority()));
    }

    public SafetyStandard get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "SafetyStandard not found: " + id));
    }

    public List<SafetyStandard> list() {
        return repository.findAll();
    }

    public SafetyStandard activate(UUID id) {
        SafetyStandard rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.activityType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.activityType());
        }
        for (SafetyStandard rule : candidates) {
            if (rule.maxIncidentRate <= request.requiredMaxIncidentRate()
                    && rule.maxCrewSize >= request.requiredMaxCrewSize()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.activityType() + " but none meets the requirement");
    }
}
